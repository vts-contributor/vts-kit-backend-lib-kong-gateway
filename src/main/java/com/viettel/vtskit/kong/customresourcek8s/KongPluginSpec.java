package com.viettel.vtskit.kong.customresourcek8s;



import com.viettel.vtskit.kong.customresourcek8s.KongPluginDTO.Config;


public class KongPluginSpec {
    private Config config;
    private String plugin;



    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public String getPlugin() {
        return plugin;
    }

    public void setPlugin(String plugin) {
        this.plugin = plugin;
    }
}
