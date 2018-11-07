package com.magicvalleyworks.remotewstarget.wfconsulregistrar.startup;

import com.magicvalleyworks.remotewstarget.wfconsulregistrar.api.ConsulRegistrar;
import com.magicvalleyworks.remotewstarget.wfconsulregistrar.regconf.api.AppServicesRegConfig;
import com.magicvalleyworks.remotewstarget.wfconsulregistrar.regconf.api.WebServiceRegConfig;
import com.magicvalleyworks.remotewstarget.wfconsulregistrar.regconf.api.WebServicesRegConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

public class ConsulRegistrarServletContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(ConsulRegistrarServletContextListener.class);

    @Inject
    private ConsulRegistrar consulRegistrar;

    @Inject
    private AppServicesRegConfig appServicesRegConfig;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        registerServices();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        consulRegistrar.deregisterServices();
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
}
