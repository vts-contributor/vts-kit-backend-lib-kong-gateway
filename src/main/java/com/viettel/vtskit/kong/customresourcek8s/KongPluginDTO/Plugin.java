package com.viettel.vtskit.kong.customresourcek8s.KongPluginDTO;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.fabric8.kubernetes.api.model.runtime.RawExtension;

@JsonSerialize
public class Plugin extends RawExtension {
    private String plugin;

    public String getPlugin() {
        return plugin;
    }

    public void setPlugin(String plugin) {
        this.plugin = plugin;
    }
}
