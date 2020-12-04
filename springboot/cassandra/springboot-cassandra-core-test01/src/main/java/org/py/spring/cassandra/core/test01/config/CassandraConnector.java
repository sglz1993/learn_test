package org.py.spring.cassandra.core.test01.config;

import com.datastax.driver.core.*;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
public class CassandraConnector {

    private static final CassandraConnector instance = new CassandraConnector();
    private static final Logger logger = LoggerFactory.getLogger(CassandraConnector.class);
    private Cluster cluster; // Cassandra cluster
    private Session session; // Cassandra session

    private CassandraConnector() {
    }

    public static CassandraConnector getInstance() {
        return instance;
    }

    public void setDataSource(CassandraConnectorConfig conf) {

        // Connect to Cassandra Cluster specified by provided node IP address and port number
        this.cluster = Cluster.builder()
            .addContactPoints(conf.getNodeList())
            .withPort(conf.getPort())
            .withRetryPolicy(DefaultRetryPolicy.INSTANCE)
            .withLoadBalancingPolicy(new TokenAwarePolicy(DCAwareRoundRobinPolicy.builder().build()))
            .withPoolingOptions(defaultOptions())
            .build();

        final Metadata metadata = cluster.getMetadata();

        logger.info("Connected to cluster: {}\n", metadata.getClusterName());

        for (final Host host : metadata.getAllHosts()) {
            logger.info("Datacenter: {}; Host: {}; Rack: {}\n", host.getDatacenter(), host.getAddress(),
                host.getRack());
        }

        if (StringUtils.isBlank(conf.getKeyspace()) || StringUtils.isEmpty(conf.getKeyspace())) {
            session = cluster.connect();
        } else {
            session = cluster.connect(conf.getKeyspace());
        }
    }

    public Session getSession() {
        return this.session;
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

    public void close() {
        cluster.close();
    }
}
