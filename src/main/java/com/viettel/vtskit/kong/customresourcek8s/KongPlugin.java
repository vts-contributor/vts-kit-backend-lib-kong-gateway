package com.viettel.vtskit.kong.customresourcek8s;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Kind;
import io.fabric8.kubernetes.model.annotation.Version;



@Version(KongPlugin.VERSION)
@Group(KongPlugin.GROUP)
@Kind(KongPlugin.KIND)
@JsonIgnoreProperties({ "spec", "status" })
public class KongPlugin extends CustomResource<KongPluginSpec, Void> implements Namespaced{
    public static final String GROUP = "configuration.konghq.com";
    public static final String VERSION = "v1";
    public static final String KIND = "KongPlugin";



    @JsonProperty("config")
    private Object config;
    @JsonProperty("plugin")
    private String plugin;

    public Object getConfig() {
        return config;
    }

    public void setConfig(Object config) {
        this.config = config;
    }

    public String getPlugin() {
        return plugin;
    }

    public void setPlugin(String plugin) {
        this.plugin = plugin;
    }

}
