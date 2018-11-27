package com.magicvalleyworks.remotewstarget.cbwc.factories;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.catalog.model.CatalogService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import java.util.List;
import java.util.Map;

public abstract class AbstractConsulBasedWebServiceFactory {

    private final static Logger logger = LoggerFactory.getLogger(AbstractConsulBasedWebServiceFactory.class);

    private static final String CONSUL_HOST_OVERRIDE_SYSTEM_PROPERTY = "consul.host.default.override";
    private static final String CONSUL_PORT_OVERRIDE_SYSTEM_PROPERTY = "consul.port.default.override";
    private static final String CONSUL_AGENT_HOST_DEFAULT = "localhost";
    private static final Integer CONSUL_AGENT_PORT_DEFAULT = 8500;
    private static final String PRIMARY_SERVICE_TAG = "internal";
    private static final String WEB_SERVICE_PATH_META_KEY = "path";

    private volatile ConsulClient consulClient;

    @PostConstruct
    private void init() {
        String consulHostOverride = System.getProperty(CONSUL_HOST_OVERRIDE_SYSTEM_PROPERTY);
        String consulPortOverride = System.getProperty(CONSUL_PORT_OVERRIDE_SYSTEM_PROPERTY);
        String usedHost = consulHostOverride != null ? consulHostOverride : CONSUL_AGENT_HOST_DEFAULT;
        Integer usedPort = consulPortOverride != null ? Integer.valueOf(consulPortOverride) : CONSUL_AGENT_PORT_DEFAULT;
        this.consulClient = new ConsulClient(usedHost, usedPort);
    }

    protected final <T, S extends Service> T createWebServicePort(S service, Class<T> tClass, String serviceName) {
        T port = service.getPort(tClass);
        BindingProvider bp = (BindingProvider) port;
        String location = discoverWebServiceUrl(serviceName);
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, location);
        return port;
    }


    protected String discoverWebServiceUrl(String serviceName) {
        String serviceUrl = null;
        Response<List<CatalogService>> response = this.consulClient.getCatalogService(serviceName, PRIMARY_SERVICE_TAG, QueryParams.DEFAULT);
        List<CatalogService> services = response != null ? response.getValue() : null;

        // Must be just one primary service
        CatalogService service = services != null && services.size() > 0 ? services.get(0) : null;
        serviceUrl = constructWebServiceUrl(service);
        if (serviceUrl == null) {
            logger.error(String.format("Can't construct web service url by name = %s, tag = %s from consul service catalog", serviceName, PRIMARY_SERVICE_TAG));
            throw new IllegalStateException();
        } else {
            logger.info(String.format("Service url was constructed = %s", serviceUrl));
        }
        return serviceUrl;
    }

    private String constructWebServiceUrl(CatalogService service) {
        String serviceUrl = null;
        if (service != null) {
            String serviceAddress = service.getServiceAddress();
            Integer servicePort = service.getServicePort();
            Map<String, String> serviceMeta = service.getServiceMeta();
            String servicePath = serviceMeta.get(WEB_SERVICE_PATH_META_KEY);
            if (serviceAddress != null && servicePort != null && servicePath != null) {
                serviceUrl = String.format("http://%s:%s/%s", serviceAddress, servicePort, trimAllSlashes(servicePath));
            }
        }
        return serviceUrl;
    }

    private String trimAllSlashes(String value) {
        return StringUtils.strip(value, "/");
    }
}
