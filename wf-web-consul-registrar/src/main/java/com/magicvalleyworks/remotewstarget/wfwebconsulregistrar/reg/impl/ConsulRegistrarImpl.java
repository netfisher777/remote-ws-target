package com.magicvalleyworks.remotewstarget.wfwebconsulregistrar.reg.impl;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;
import com.magicvalleyworks.remotewstarget.wfwebconsulregistrar.context.api.ConsulRegistrationContext;
import com.magicvalleyworks.remotewstarget.wfwebconsulregistrar.reg.api.ConsulRegistrar;
import com.magicvalleyworks.remotewstarget.wfwebconsulregistrar.regconf.api.AppServicesRegConfig;
import com.magicvalleyworks.remotewstarget.wfwebconsulregistrar.regconf.api.WebServiceRegConfig;
import com.magicvalleyworks.remotewstarget.wfwebconsulregistrar.regconf.api.WebServicesRegConfig;
import com.magicvalleyworks.remotewstarget.wfwebconsulregistrar.serversettings.api.CurrentServerNodeSettings;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.*;

@ApplicationScoped
public class ConsulRegistrarImpl implements ConsulRegistrar {
    // CONSUL_AGENT_HOST_DEFAULT must be localhost
    // Assumes that the consul agent exists on the same machine as the app server,
    // on which this application is installed on
    private static final String CONSUL_AGENT_HOST_DEFAULT = "localhost";
    private static final Integer CONSUL_AGENT_PORT_DEFAULT = 8500;
    private static final String WEB_SERVICE_PATH_META_KEY = "path";

    private ConsulClient consulClient;
    private static final Logger logger = LoggerFactory.getLogger(ConsulRegistrarImpl.class);

    @Inject
    private CurrentServerNodeSettings currentServerNodeSettings;

    @Inject
    private AppServicesRegConfig appServicesRegConfig;

    @Inject
    private ConsulRegistrationContext consulRegistrationContext;

    private List<String> registeredWebServicesIds = new ArrayList<>();

    @PostConstruct
    public void initialize() {
        String consulHostOverride = consulRegistrationContext.getConsulHostOverride();
        String consulPortOverride = consulRegistrationContext.getConsulPortOverride();
        String usedHost = consulHostOverride != null ? consulHostOverride : CONSUL_AGENT_HOST_DEFAULT;
        Integer usedPort = consulPortOverride != null ? Integer.valueOf(consulPortOverride) : CONSUL_AGENT_PORT_DEFAULT;
        this.consulClient = new ConsulClient(usedHost, usedPort);
        logger.info(String.format("Consul client was initialized with address %s:%d", usedHost, usedPort));
    }

    @Override
    public void registerWebServices() {
        WebServicesRegConfig webServicesRegConfig = appServicesRegConfig.getWebServicesRegConfig();
        List<WebServiceRegConfig> webServiceRegConfigs = webServicesRegConfig != null ? webServicesRegConfig.getWebServiceRegConfigs() : null;
        if (webServiceRegConfigs != null) {
            for (WebServiceRegConfig conf : webServiceRegConfigs) {
                registerWebService(conf);
            }
        } else {
            logger.warn("There are no web services registration configuration. Nothing to register");
        }
    }

    @Override
    public void deregisterWebServices() {
        for (String serviceId : registeredWebServicesIds) {
            consulClient.agentServiceDeregister(serviceId);
            logger.info(String.format("Service with id = \"%s\" was deregistered from Consul", serviceId));
        }
    }

    private void registerWebService(WebServiceRegConfig webServiceRegConfig) {
        String serviceHost = currentServerNodeSettings.getWebServiceHost();
        Integer servicePort = currentServerNodeSettings.getWebServicePort();

        NewService webService = buildNewWebService(serviceHost, servicePort, webServiceRegConfig);
        String webServiceName = webService.getName();
        String webServiceId = webService.getId();
        Map<String, String> metaMap = webService.getMeta();
        List<String> tags = webService.getTags();
        consulClient.agentServiceRegister(webService);
        logger.info(String.format("Service with name = \"%s\", id = \"%s\", " +
                "host = \"%s\", port = \"%s\", meta = \"%s\", tags = \"%s\" was registered in Consul",
                webServiceName, webServiceId, serviceHost, servicePort, metaMap, tags));

        registeredWebServicesIds.add(webServiceId);
    }

    private NewService buildNewWebService(String serviceHost, Integer servicePort, WebServiceRegConfig webServiceRegConfig) {
        NewService service = new NewService();

        String webApplicationContextPath = consulRegistrationContext.getWebApplicationContextPath();
        webApplicationContextPath = trimAllSlashes(webApplicationContextPath);
        String serviceName = webServiceRegConfig.getName();
        String serviceId = MessageFormat.format("{0}.{1}", serviceName, UUID.randomUUID());
        WebServiceRegConfig.Meta meta = webServiceRegConfig.getMeta();
        String servicePath = meta.getPath();
        servicePath = String.format("/%s/%s", webApplicationContextPath, trimAllSlashes(servicePath));
        String httpHealthCheckPath = webServiceRegConfig.getHttpHealthCheckPath();
        httpHealthCheckPath = String.format("%s/%s", webApplicationContextPath, trimAllSlashes(httpHealthCheckPath));
        List<String> tags = webServiceRegConfig.getTags();

        Map<String, String> metaMap = new HashMap<>();
        metaMap.put(WEB_SERVICE_PATH_META_KEY, servicePath);

        service.setId(serviceId);
        service.setName(serviceName);
        service.setAddress(serviceHost);
        service.setPort(servicePort);
        service.setMeta(metaMap);
        service.setTags(tags);

        NewService.Check check = new NewService.Check();
        check.setHttp(String.format("http://%s:%s/%s", serviceHost, servicePort, httpHealthCheckPath));
        check.setInterval("30s");
        service.setCheck(check);

        return service;
    }

    private String trimAllSlashes(String value) {
        return StringUtils.strip(value, "/");
    }
}
