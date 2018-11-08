package com.magicvalleyworks.remotewstarget.wfconsulregistrar.startup;

import com.magicvalleyworks.remotewstarget.wfconsulregistrar.api.ConsulRegistrar;
import com.magicvalleyworks.remotewstarget.wfconsulregistrar.regconf.api.AppServicesRegConfig;
import com.magicvalleyworks.remotewstarget.wfconsulregistrar.regconf.api.WebServiceRegConfig;
import com.magicvalleyworks.remotewstarget.wfconsulregistrar.regconf.api.WebServicesRegConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

public class ConsulRegistrarServletContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(ConsulRegistrarServletContextListener.class);
    private static final String WS_REG_CONFIG_FILE_INIT_PARAM = "WEB_SERVICES_REGISTRATION_CONFIGURATION_FILE";
    private static final String CONSUL_HOST_OVERRIDE_INIT_PARAM = "CONSUL_HOST_OVERRIDE";
    private static final String CONSUL_PORT_OVERRIDE_INIT_PARAM = "CONSUL_PORT_OVERRIDE";

    @Inject
    private ConsulRegistrar consulRegistrar;

    @Inject
    private AppServicesRegConfig appServicesRegConfig;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Services registration was started");
        try {
            ServletContext servletContext = sce.getServletContext();
            String wsRegConfigFile = servletContext.getInitParameter(WS_REG_CONFIG_FILE_INIT_PARAM);
            String consulHostOverride = servletContext.getInitParameter(CONSUL_HOST_OVERRIDE_INIT_PARAM);
            String consulPortOverride = servletContext.getInitParameter(CONSUL_PORT_OVERRIDE_INIT_PARAM);
            if (wsRegConfigFile != null) {
                logger.info(String.format("Was found %s parameter in servlet context with value = %s", WS_REG_CONFIG_FILE_INIT_PARAM, wsRegConfigFile));
                registerWebServices();
            } else {
                logger.info(String.format("Was not found %s parameter in servlet context", WS_REG_CONFIG_FILE_INIT_PARAM));
            }
        } catch (Throwable ex) {
            logger.error("Can't register services due to unexpected exception", ex);
        }

        logger.info("Services registration was ended");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        consulRegistrar.deregisterServices();
    }

    private void registerWebServices() {
        appServicesRegConfig.loadWebServicesRegistrationConfiguration();
        WebServicesRegConfig webServicesRegConfig = appServicesRegConfig.getWebServicesRegConfig();
        List<WebServiceRegConfig> webServiceRegConfigs = webServicesRegConfig != null ? webServicesRegConfig.getWebServiceRegConfigs() : null;
        if (webServiceRegConfigs != null) {
            for (WebServiceRegConfig conf : webServiceRegConfigs) {
                consulRegistrar.registerWebService(conf);
            }
        }
    }
}
