package com.derbysoft.sentinel.provider;

import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

public class NacosDataSourceInitFunc implements InitFunc {

    private final String CLUSTER_SERVER_HOST = "127.0.0.1"; //token-server的地址
    private final int CLUSTER_SERVER_PORT = 9999;
    // 过短容易降级
    private final int REQUEST_TIME_OUT = 200000; //请求超时时间

    private final String APP_NAME = "derbysoft1"; //namespace

    private final String remoteAddress = "127.0.0.1"; //nacos 配置中心的服务host
    private final String groupId = "SENTINEL_GROUP";
    private final String FLOW_POSTFIX = "-flow-rules"; //dataid（names+postfix）

    @Override
    public void init() throws Exception {
        loadClusterClientConfig();

        registryClusterFlowRuleProperty();
    }

    private void loadClusterClientConfig() {
        ClusterClientAssignConfig assignConfig = new ClusterClientAssignConfig();
        assignConfig.setServerHost(CLUSTER_SERVER_HOST);
        assignConfig.setServerPort(CLUSTER_SERVER_PORT);
        ClusterClientConfigManager.applyNewAssignConfig(assignConfig);

        ClusterClientConfig clientConfig = new ClusterClientConfig();
        clientConfig.setRequestTimeout(REQUEST_TIME_OUT);
        ClusterClientConfigManager.applyNewConfig(clientConfig);
    }

    //注册数据源
    private void registryClusterFlowRuleProperty() {
        ReadableDataSource<String, List<FlowRule>> rds =
                new NacosDataSource<List<FlowRule>>(remoteAddress, groupId, APP_NAME + FLOW_POSTFIX,
                        source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                        }));
        FlowRuleManager.register2Property(rds.getProperty());
    }
}