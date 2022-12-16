package com.viettel.vtskit.kong;


import com.viettel.vtskit.kong.configuration.KongAutoConfiguration;
import com.viettel.vtskit.kong.service.KubernetesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;


@Component
public class KongService {


    static RestTemplate restTemplate = new RestTemplate();

    static KongAutoConfiguration kongConfiguration = KongAutoConfiguration.getInstance();

    public static KongAutoConfiguration getKongConfiguration(){
        return kongConfiguration;
    }

    @Autowired
    ApplicationContext context;

    @Autowired
    KubernetesService kubernetesService;


    @PostConstruct
    public void initialize() throws Exception {
        this.kongConfiguration = context.getBean(KongAutoConfiguration.class);
//        System.out.printf("init");
    }


    public static void addService(String serviceName, String serviceUrl) throws Exception {
        try {
            String routeUrl = kongConfiguration.getUrl() + "/services/";
            MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
            body.add("name", serviceName);
            body.add("url",serviceUrl);
            ResponseEntity<String> response
                    = restTemplate.postForEntity(routeUrl, body, String.class);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage(),e);
        }
    }

    public static String isKongReady() throws Exception {
        try{
            String routeUrl = kongConfiguration.getUrl() +"/services";
            MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
            ResponseEntity<String> response
                    = restTemplate.getForEntity(routeUrl, String.class);
            System.out.println(response.getStatusCode().toString());
            return response.getStatusCode().toString();
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage(),e);
        }
    }
    public static  void addRoute(String service, String routeName, String route) throws Exception {
        try{
            String routeUrl = kongConfiguration.getUrl() +"/services/"+service+"/routes";
            MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
            body.add("name", routeName);
            body.add("paths[]",route);
            ResponseEntity<String> response
                    = restTemplate.postForEntity(routeUrl, body, String.class);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage(),e);
        }
    }

    public static void addPlugin(String name) throws Exception {
        try{
            String pluginUrl = kongConfiguration.getUrl() + "/plugins/";
            MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
            body.add("name", name);
            ResponseEntity<String> response
                    = restTemplate.postForEntity(pluginUrl, body, String.class);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage(),e);
        }
    }

}
