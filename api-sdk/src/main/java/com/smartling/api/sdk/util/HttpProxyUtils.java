/*
 * Copyright 2012 Smartling, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this work except in compliance with the License.
 * You may obtain a copy of the License in the LICENSE file, or at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smartling.api.sdk.util;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.smartling.api.sdk.ProxyConfiguration;

public class HttpProxyUtils
{

    public HttpProxyUtils()
    {

    }

    /**
     * Get a request config given the applicable request and proxy config if any
     * @param httpRequest
     * @param proxyConfiguration
     * @return org.apache.http.client.config.RequestConfig
     */
    public RequestConfig getProxyRequestConfig(HttpRequestBase httpRequest, final ProxyConfiguration proxyConfiguration)
    {
        if (hasActiveProxyConfiguration(proxyConfiguration))
        {
            HttpHost proxyHttpHost = new HttpHost(proxyConfiguration.getHost(), proxyConfiguration.getPort());
            return RequestConfig.custom().setProxy(proxyHttpHost).build();
        }
        return null;
    }

    /**
     * Get an httpclient given a proxy config if any
     * @param proxyConfiguration
     * @return org.apache.http.impl.client.CloseableHttpClient
     */
    public CloseableHttpClient getHttpClient(final ProxyConfiguration proxyConfiguration)
    {
        HttpClientBuilder httpClientBuilder = null;

        if (!hasActiveProxyConfiguration(proxyConfiguration))
        {
            httpClientBuilder = getHttpClientBuilder();
        }
        else
        {
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(
                new AuthScope(proxyConfiguration.getHost(), proxyConfiguration.getPort()),
                new UsernamePasswordCredentials(proxyConfiguration.getUsername(), proxyConfiguration.getPassword()));

            httpClientBuilder = getHttpClientBuilder().setDefaultCredentialsProvider(credentialsProvider);
        }

        return httpClientBuilder.build();
    }

    HttpClientBuilder getHttpClientBuilder()
    {
        return HttpClientBuilder.create();
    }

    private boolean hasActiveProxyConfiguration(final ProxyConfiguration proxyConfiguration)
    {
        return proxyConfiguration != null && proxyConfiguration.getHost() != null && proxyConfiguration.getPort() != 0;
    }
}
