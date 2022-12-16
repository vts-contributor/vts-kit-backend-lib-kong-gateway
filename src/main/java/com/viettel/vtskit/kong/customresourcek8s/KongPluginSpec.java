package com.viettel.vtskit.kong.customresourcek8s;



import com.viettel.vtskit.kong.customresourcek8s.KongPluginDTO.Config;


public class KongPluginSpec {
    private Config config;
    private String plugin;
    private String foo;
    private String bar;

    @Override
    public String toString() {
        return "DummySpec{" +
                "foo='" + foo + '\'' +
                ", bar='" + bar + '\'' +
                '}';
    }

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

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
