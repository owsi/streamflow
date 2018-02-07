/**
 * Copyright 2014 Lockheed Martin Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package streamflow.model.config;

import java.io.Serializable;
import java.util.Objects;

public class ServerConfig implements Serializable {

    private int port = 8080;
    private String contextPath = "/";

    public ServerConfig() {
    }

    public int getPort() {
        if (System.getProperty("server.port") != null) {
            try {
                port = Integer.parseInt(System.getProperty("server.port"));
            } catch (Exception ex) {
            }
        }
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getContextPath() {
        if (System.getProperty("server.contextPath") != null) {
            try {
                contextPath = System.getProperty("server.contextPath");
            } catch (Exception ex) {
            }
        }
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerConfig that = (ServerConfig) o;
        return port == that.port &&
                Objects.equals(contextPath, that.contextPath);
    }

    @Override
    public int hashCode() {

        return Objects.hash(port, contextPath);
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "port=" + port +
                ", contextPath='" + contextPath + '\'' +
                '}';
    }
}
