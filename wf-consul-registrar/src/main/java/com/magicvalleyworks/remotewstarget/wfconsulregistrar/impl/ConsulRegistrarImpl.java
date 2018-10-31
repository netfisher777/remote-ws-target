package com.magicvalleyworks.remotewstarget.wfconsulregistrar.impl;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;
import com.magicvalleyworks.remotewstarget.wfconsulregistrar.api.ConsulRegistrar;
import com.magicvalleyworks.remotewstarget.wfconsulregistrar.serversettings.api.CurrentServerNodeSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Dependent
public class ConsulRegistrarImpl implements ConsulRegistrar {
    // Assumes that the consul agent exists on the same machine as the app server,
    // on which this application is installed on
    private static final String CONSUL_AGENT_HOST = "195.133.1.42";
    private static final Integer CONSUL_AGENT_PORT = 8500;

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
    public void registerWebService(String serviceName, String healthCheckWsdlPath) {
        UUID uuid = UUID.randomUUID();
        String serviceId = MessageFormat.format("{0}.{1}", serviceName, uuid);
        String serviceHost = currentServerNodeSettings.getWebServiceHost();
        Integer servicePort = currentServerNodeSettings.getWebServicePort();

        NewService service = buildNewService(serviceId, serviceName, serviceHost, servicePort, healthCheckWsdlPath);
        consulClient.agentServiceRegister(service);
        logger.info(String.format("Service with name = \"%s\", id = \"%s\", host = \"%s\", port = \"%s\" was registered in Consul", serviceName, serviceId, serviceHost, servicePort));

        registeredServicesIds.add(serviceId);
    }

    private NewService buildNewService(String webServiceId, String webserviceName, String webServiceHost, Integer webServicePort, String healthCheckWsdlPath) {
        NewService service = new NewService();
        service.setId(webServiceId);

        service.setName(webserviceName);
        service.setAddress(webServiceHost);
        service.setPort(webServicePort);

        NewService.Check check = new NewService.Check();
        check.setHttp(String.format("http://%s:%s/%s", webServiceHost, webServicePort, healthCheckWsdlPath));
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
