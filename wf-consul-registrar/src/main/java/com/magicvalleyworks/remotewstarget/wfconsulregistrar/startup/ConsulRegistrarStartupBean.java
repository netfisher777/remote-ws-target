package com.magicvalleyworks.remotewstarget.wfconsulregistrar.startup;

import com.magicvalleyworks.remotewstarget.wfconsulregistrar.api.ConsulRegistrar;
import com.magicvalleyworks.remotewstarget.wfconsulregistrar.regconf.api.AppServicesRegConfig;
import com.magicvalleyworks.remotewstarget.wfconsulregistrar.regconf.api.WebServiceRegConfig;
import com.magicvalleyworks.remotewstarget.wfconsulregistrar.regconf.api.WebServicesRegConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.List;

@Startup
@Singleton
public class ConsulRegistrarStartupBean {
    private static final Logger logger = LoggerFactory.getLogger(ConsulRegistrarStartupBean.class);

    @Inject
    private ConsulRegistrar consulRegistrar;

    @Inject
    private AppServicesRegConfig appServicesRegConfig;

    @PostConstruct
    public void initialize() {
        registerServices();
    }

    private void registerServices() {
        logger.info("Services registration was started");
        registerWebServices();
        logger.info("Services registration was ended");
    }

    private void registerWebServices() {
        appServicesRegConfig.loadWebServicesRegistrationConfiguration();
        WebServicesRegConfig webServicesRegConfig = appServicesRegConfig.getWebServicesRegConfig();
        List<WebServiceRegConfig> webServiceRegConfigs = webServicesRegConfig.getWebServiceRegConfigs();
        for(WebServiceRegConfig conf : webServiceRegConfigs) {
            consulRegistrar.registerWebService(conf);
        }
    }


    @PreDestroy
    public void terminate() {
        consulRegistrar.deregisterServices();
    }
}
