package com.viettel.vtskit.kong.konganotation;

import com.viettel.vtskit.kong.configuration.KongAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;



@Target({ElementType.TYPE, ElementType.FIELD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Import(KongAutoConfiguration.class)
public @interface EnableKongGateway {
}
