package com.derbysoft.sentinel.provider;

import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class SentinelProviderApplication {

    public static void main(String[] args) throws IOException {
        ClusterStateManager.applyState(ClusterStateManager.CLUSTER_CLIENT);
        SpringApplication.run(SentinelProviderApplication.class, args);
        System.in.read();
    }

}
