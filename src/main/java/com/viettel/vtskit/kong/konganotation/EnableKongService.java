package com.viettel.vtskit.kong.konganotation;

import com.viettel.vtskit.kong.service.KubernetesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;



@Component
public class EnableKongService {

    KubernetesService kubernetesService;

    @Autowired
    public void setKubernetesService(KubernetesService kubernetesService){
        this.kubernetesService = kubernetesService;
    }

    @PostConstruct
    public void initialize() throws Exception {
//        kubernetesService.createOrUpdateDeploy();
//        kubernetesService.createKongPlugin();
        kubernetesService.createIngressKong();

    }

}
