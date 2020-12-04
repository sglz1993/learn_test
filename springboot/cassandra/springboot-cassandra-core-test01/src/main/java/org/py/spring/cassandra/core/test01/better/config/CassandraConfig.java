package org.py.spring.cassandra.core.test01.better.config;

import com.datastax.driver.core.*;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;
import com.datastax.driver.mapping.MappingManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.py.spring.cassandra.core.test01.config.CassandraConnectorConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
public class CassandraConfig {

    @Value("#{'${spring.data.cassandra.contact-points}'.split(',')}")
    private List<String> points;

    @Value("${spring.data.cassandra.port}")
    private Integer port;

    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspace;

    @Bean
    public CassandraConnectorConfig cassandraConnectorConfig() {
        CassandraConnectorConfig connectorConfig = new CassandraConnectorConfig();
        connectorConfig.setKeyspace(keyspace);
        connectorConfig.setNodeList(points);
        connectorConfig.setPort(port);
        return connectorConfig;
    }

    @Bean
    public Session session(CassandraConnectorConfig conf) {

        // Connect to Cassandra Cluster specified by provided node IP address and port number
        Cluster cluster = Cluster.builder()
                .addContactPoints(conf.getNodeList())
                .withPort(conf.getPort())
                .withRetryPolicy(DefaultRetryPolicy.INSTANCE)
                .withLoadBalancingPolicy(new TokenAwarePolicy(DCAwareRoundRobinPolicy.builder().build()))
                .withPoolingOptions(defaultOptions())
                .build();

        final Metadata metadata = cluster.getMetadata();

        log.info("Connected to cluster: {}\n", metadata.getClusterName());

        for (final Host host : metadata.getAllHosts()) {
            log.info("Datacenter: {}; Host: {}; Rack: {}\n", host.getDatacenter(), host.getAddress(),
                    host.getRack());
        }
        Session session = null;
        if (StringUtils.isBlank(conf.getKeyspace()) || StringUtils.isEmpty(conf.getKeyspace())) {
            session = cluster.connect();
        } else {
            session = cluster.connect(conf.getKeyspace());
        }
        return session;
    }

    @Bean
    public MappingManager mappingManager(Session session) {
        MappingManager mappingManager = new MappingManager(session);
//        mappingManager.udtCodec(Teacher.class);
        return mappingManager;
    }


    private PoolingOptions defaultOptions() {
        PoolingOptions poolingOptions = new PoolingOptions();
        poolingOptions.setCoreConnectionsPerHost(HostDistance.LOCAL, 4)
                .setMaxConnectionsPerHost(HostDistance.LOCAL, 10)
                .setCoreConnectionsPerHost(HostDistance.REMOTE, 2)
                .setMaxConnectionsPerHost(HostDistance.REMOTE, 4)
                .setMaxRequestsPerConnection(HostDistance.LOCAL, 1024)
                .setMaxRequestsPerConnection(HostDistance.REMOTE, 256)
                .setHeartbeatIntervalSeconds(60);

        return poolingOptions;
    }


}
