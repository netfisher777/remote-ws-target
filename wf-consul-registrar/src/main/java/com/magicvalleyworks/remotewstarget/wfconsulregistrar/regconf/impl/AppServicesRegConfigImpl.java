package com.magicvalleyworks.remotewstarget.wfconsulregistrar.regconf.impl;

import com.magicvalleyworks.remotewstarget.wfconsulregistrar.regconf.api.AppServicesRegConfig;
import com.magicvalleyworks.remotewstarget.wfconsulregistrar.regconf.api.WebServicesRegConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import java.io.IOException;
import java.io.InputStream;

@ApplicationScoped
public class AppServicesRegConfigImpl implements AppServicesRegConfig {
    private WebServicesRegConfig webServicesRegConfig;
    private static final Logger logger = LoggerFactory.getLogger(AppServicesRegConfigImpl.class);
    private static final String WS_REG_CONFIG_FILE = "WEB-INF/ws-reg-conf.json";

    @Override
    public void loadWebServicesRegistrationConfiguration() {
        this.webServicesRegConfig = loadObjectFromJsonFileInClasspath(WS_REG_CONFIG_FILE, WebServicesRegConfig.class);
    }

    private <T> T loadObjectFromJsonFileInClasspath(String filename, Class<T> type) {
        T deserializedObject = null;
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filename)) {
            if (inputStream != null) {
                logger.info(String.format("File with name %s was found in classpath. Trying to load", filename));
                Jsonb jsonb = JsonbBuilder.create();
                deserializedObject = jsonb.fromJson(inputStream, type);
                logger.info(String.format("Data from %s was successfully loaded", filename));
            } else {
                logger.info(String.format("File with name %s was not found in classpath. Nothing to load", filename));
            }
        } catch (JsonbException ex) {
            logger.error(String.format("JsonbException: Can't serialize data from %s", filename), ex);
        } catch (IOException ex) {
            logger.error(String.format("IOException: Can't load data from %s", filename), ex);
        }
        return deserializedObject;
    }

    @Override
    public WebServicesRegConfig getWebServicesRegConfig() {
        return webServicesRegConfig;
    }
}
