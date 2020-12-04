package org.py.spring.cassandra.core.test01.config;

import java.util.List;

/**
 */
public class CassandraConnectorConfig {

    private String node; // Cluster node IP address
    private List<String> nodeList;
    private int port; // Port of cluster host
    private String keyspace;

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String[] getNodeList() {
        return nodeList.toArray(new String[]{});
    }

    public void setNodeList(List<String> nodeList) {
        this.nodeList = nodeList;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getKeyspace() {
        return keyspace;
    }

    public void setKeyspace(String keyspace) {
        this.keyspace = keyspace;
    }
}
