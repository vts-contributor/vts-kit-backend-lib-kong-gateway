package com.viettel.vtskit.kong.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan(basePackages = {"com.*"})
public class KongAutoConfiguration {
    private String url;
    private Integer port;
    private String servicename;
    private String route;
    private String pluginname;

    @Bean
    @Primary
    @ConfigurationProperties(
            prefix = "kong.k8s.db"
    )
    public KongAutoConfiguration kongConfigurationProperties(){
        return new KongAutoConfiguration();
    }


    public KongAutoConfiguration(){

    }
    private static KongAutoConfiguration INSTANCE;


    public static synchronized KongAutoConfiguration init(String kongAdminUrl,Integer port, String servicename, String route, String pluginname){
        if(INSTANCE == null) {
            INSTANCE = new KongAutoConfiguration(kongAdminUrl,port,servicename, route, pluginname);
        }
        return INSTANCE;
    }
    public static KongAutoConfiguration getInstance() {
        return INSTANCE;
    }

    private KongAutoConfiguration(String url, Integer port, String servicename, String route, String pluginname) {
        this.url = url;
        this.port = port;
        this.servicename = servicename;
        this.route = route;
        this.pluginname = pluginname;
    }


    public String getPluginname() {
        return pluginname;
    }

    public void setPluginname(String pluginname) {
        this.pluginname = pluginname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

//    @Bean
//    EnableKongService enableKongService(){
//        return new EnableKongService();
//    }

//    @Bean
//    KongService kongService(){
//        return new KongService();
//    }
}
