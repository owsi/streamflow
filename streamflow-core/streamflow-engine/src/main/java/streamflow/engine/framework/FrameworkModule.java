/**
 * Copyright 2014 Lockheed Martin Corporation
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package streamflow.engine.framework;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import org.apache.storm.task.TopologyContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamflow.model.Topology;
import streamflow.model.TopologyComponent;
import streamflow.model.config.StreamflowConfig;

import java.util.Map.Entry;

public class FrameworkModule extends AbstractModule {

    private final Logger LOG = LoggerFactory.getLogger(FrameworkModule.class);

    private final Topology topology;

    private final TopologyComponent component;

    private final StreamflowConfig streamflowConfig;

    private final TopologyContext context;

    public FrameworkModule(Topology topology, TopologyComponent component,
                           StreamflowConfig streamflowConfig, TopologyContext context) {
        this.topology = topology;
        this.component = component;
        this.streamflowConfig = streamflowConfig;
        this.context = context;
    }

    @Override
    protected void configure() {
        // Iterate over each of the properties and bind the named properties
        for (Entry<String, String> propertyEntry : component.getProperties().entrySet()) {
            bindConstant().annotatedWith(Names.named(propertyEntry.getKey()))
                    .to(propertyEntry.getValue());
        }

        if (streamflowConfig.getProxy().getHost() != null) {
            bindConstant().annotatedWith(Names.named("http.proxy.host")).to(
                    streamflowConfig.getProxy().getHost());
        }

        if (streamflowConfig.getProxy().getPort() > 0) {
            bindConstant().annotatedWith(Names.named("http.proxy.port")).to(
                    streamflowConfig.getProxy().getPort());
        }

        // Bind streamflow specific properties in case underlying bolts/resources require them
        bindConstant().annotatedWith(
                Names.named("streamflow.topology.id")).to(topology.getId());
        //bindConstant().annotatedWith(
        //        Names.named("streamflow.topology.name")).to(topology.getName());
        bindConstant().annotatedWith(
                Names.named("streamflow.component.key")).to(component.getKey());
        bindConstant().annotatedWith(
                Names.named("streamflow.component.label")).to(component.getLabel());
        bindConstant().annotatedWith(
                Names.named("streamflow.component.name")).to(component.getName());
        bindConstant().annotatedWith(
                Names.named("streamflow.component.framework")).to(component.getFramework());
        //bindConstant().annotatedWith(
        //        Names.named("streamflow.user.id")).to(topology.getUserId());
        //bindConstant().annotatedWith(
        //        Names.named("streamflow.cluster.id")).to(topology.getClusterId());
        //bindConstant().annotatedWith(
        //        Names.named("streamflow.cluster.name")).to(topology.getClusterName());
    }

    @Provides
    public Logger provideLogger() {
        Logger logger = LoggerFactory.getLogger(component.getMainClass());
        return logger;
    }
}
