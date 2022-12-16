package com.viettel.vtskit.kong.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class KubernetesConfiguration {
    private String namespace;
    private String configfile;
    @Bean
    @Primary
    @ConfigurationProperties(
            prefix = "kong.k8s"
    )
    public KubernetesConfiguration k8sConfigurationProperties(){
        return new KubernetesConfiguration();
    }


    public KubernetesConfiguration(){

    }
    private static KubernetesConfiguration INSTANCE;

    public static synchronized KubernetesConfiguration init(String configfile){
        if(INSTANCE == null) {
            INSTANCE = new KubernetesConfiguration(configfile);
        }
        return INSTANCE;
    }
    public static KubernetesConfiguration getInstance() {
        return INSTANCE;
    }

    private KubernetesConfiguration(String configpath) {
        this.configfile = configpath;

    }

    public String getConfigfile() {
        return configfile;
    }

    public void setConfigfile(String configpath) {
        this.configfile = configpath;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
