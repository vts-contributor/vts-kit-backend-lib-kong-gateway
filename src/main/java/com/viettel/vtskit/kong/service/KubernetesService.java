package com.viettel.vtskit.kong.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface KubernetesService {

    void getAllPodAllNamespace() throws IOException;

    String createOrUpdateDeploy() throws Exception;

    String createServerSVC() throws Exception;

    String createPluginKong(String pluginName, String masterKey) throws Exception;

    String createIngressKong() throws IOException;

    String createKongPlugin() throws IOException;

    String test();

}
