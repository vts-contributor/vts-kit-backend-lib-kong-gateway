package com.viettel.vtskit.kong.konganotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KongMarkerConfig {

    @Bean
    public Marker kongMarker(){
        return new Marker() ;
    }
    public class Marker{

    }
}
