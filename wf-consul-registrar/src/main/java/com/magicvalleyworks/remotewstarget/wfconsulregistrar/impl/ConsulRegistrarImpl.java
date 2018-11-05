package com.magicvalleyworks.remotewstarget.wfconsulregistrar.impl;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;
import com.magicvalleyworks.remotewstarget.wfconsulregistrar.api.ConsulRegistrar;
import com.magicvalleyworks.remotewstarget.wfconsulregistrar.regconf.api.WebServiceRegConfig;
import com.magicvalleyworks.remotewstarget.wfconsulregistrar.serversettings.api.CurrentServerNodeSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.*;

@Dependent
public class ConsulRegistrarImpl implements ConsulRegistrar {
    // CONSUL_AGENT_HOST must be localhost
    // Assumes that the consul agent exists on the same machine as the app server,
    // on which this application is installed on
    private static final String CONSUL_AGENT_HOST = "localhost";
    private static final Integer CONSUL_AGENT_PORT = 8500;
    private static final String WEB_SERVICE_PATH_META_KEY = "path";

    private ConsulClient consulClient;
    private static final Logger logger = LoggerFactory.getLogger(ConsulRegistrarImpl.class);

    @Inject
    private CurrentServerNodeSettings currentServerNodeSettings;

    private List<String> registeredServicesIds = new ArrayList<>();

    @PostConstruct
    public void initialize() {
        this.consulClient = new ConsulClient(CONSUL_AGENT_HOST, CONSUL_AGENT_PORT);
    }

    @Override
    public void registerWebService(WebServiceRegConfig webServiceRegConfig) {
        String serviceHost = currentServerNodeSettings.getWebServiceHost();
        Integer servicePort = currentServerNodeSettings.getWebServicePort();

        NewService webService = buildNewWebService(serviceHost, servicePort, webServiceRegConfig);
        String webServiceName = webService.getName();
        String webServiceId = webService.getId();
        consulClient.agentServiceRegister(webService);
        logger.info(String.format("Service with name = \"%s\", id = \"%s\", host = \"%s\", port = \"%s\" was registered in Consul", webServiceName, webServiceId, serviceHost, servicePort));

        registeredServicesIds.add(webServiceId);
    }

    private NewService buildNewWebService(String serviceHost, Integer servicePort, WebServiceRegConfig webServiceRegConfig) {
        NewService service = new NewService();

        String serviceName = webServiceRegConfig.getName();
        String serviceId = MessageFormat.format("{0}.{1}", serviceName, UUID.randomUUID());
        String httpHealthCheckPath = webServiceRegConfig.getHttpHealthCheckPath();
        WebServiceRegConfig.Meta meta = webServiceRegConfig.getMeta();
        String servicePath = meta.getPath();
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

    @Override
    public void deregisterServices() {
        for (String serviceId : registeredServicesIds) {
            consulClient.agentServiceDeregister(serviceId);
            logger.info(String.format("Service with id = \"%s\" was deregistered from Consul", serviceId));
        }
    }
}
