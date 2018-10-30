package com.magicvalleyworks.remotewstarget.wfconsulregistrar.startup;

import com.magicvalleyworks.remotewstarget.wfconsulregistrar.api.ConsulRegistrar;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;

@Startup
@Singleton
public class ConsulRegistrarStartupBean {
    private static final Logger logger = LoggerFactory.getLogger(ConsulRegistrarStartupBean.class);

    @Inject
    private ConsulRegistrar consulRegistrar;

    @PostConstruct
    public void initialize() {
        registerServices();
    }

    private void registerServices() {

        try (InputStream wsRegConfInputStream = ClassLoader.class.getResourceAsStream("ws-reg-conf.json")) {
            if (wsRegConfInputStream != null) {
                String wsRegConfString = IOUtils.toString(wsRegConfInputStream);
                logger.info(String.format("Content of the wsRegConfString = %s", wsRegConfString));
            } else {
                logger.info("wsRegConfInputStream is null");
            }
        } catch(IOException ex) {
            logger.error("", ex);
        }


        consulRegistrar.registerWebService("SimpleWebService", "rwst/SimpleWebService?wsdl");
    }


    @PreDestroy
    public void terminate() {
        consulRegistrar.deregisterServices();
    }
}
