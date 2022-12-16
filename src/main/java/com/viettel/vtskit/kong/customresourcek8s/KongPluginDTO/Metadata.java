package com.viettel.vtskit.kong.customresourcek8s.KongPluginDTO;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.fabric8.kubernetes.api.model.runtime.RawExtension;

@JsonSerialize
public class Metadata extends RawExtension {
    String name;
    String namespace;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
