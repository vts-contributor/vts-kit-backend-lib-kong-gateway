package com.viettel.vtskit.kong.service.impl;

import com.viettel.vtskit.kong.configuration.KubernetesConfiguration;
import com.viettel.vtskit.kong.configuration.PluginConfiguration;
import com.viettel.vtskit.kong.configuration.ServiceConfiguration;
import com.viettel.vtskit.kong.customresourcek8s.KongPlugin;
import com.viettel.vtskit.kong.customresourcek8s.KongPluginList;
import com.viettel.vtskit.kong.service.KubernetesService;
import com.viettel.vtskit.kong.utils.NameTemplate;
import com.viettel.vtskit.kong.utils.Utils;

import io.fabric8.kubernetes.api.model.HostAlias;
import io.fabric8.kubernetes.api.model.ServiceList;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentList;
import io.fabric8.kubernetes.api.model.apps.ReplicaSet;
import io.fabric8.kubernetes.api.model.networking.v1.Ingress;
import io.fabric8.kubernetes.api.model.networking.v1.IngressList;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.dsl.MixedOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.fabric8.kubernetes.client.dsl.RollableScalableResource;
import io.fabric8.kubernetes.client.dsl.ServiceResource;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;


@Service
public class KubernetesServiceImpl implements KubernetesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KubernetesServiceImpl.class);
    private KubernetesClient kubernetesClient;
    private CustomResourceDefinitionContext customResourceDefinitionContext;

    @Autowired
    private KubernetesConfiguration kubernetesConfiguration;

    @Autowired
    private PluginConfiguration pluginConfiguration;

    @Autowired
    private ServiceConfiguration serviceConfiguration;

    @Autowired
    private void setKubernetesConfiguration(KubernetesConfiguration kubernetesConfiguration) throws IOException {
        String k8sConfig = Utils.readFileToStringFromResource(kubernetesConfiguration.getConfigfile());
        kubernetesClient = new DefaultKubernetesClient(io.fabric8.kubernetes.client.Config.fromKubeconfig(new String(k8sConfig)));
    }


    @Override
    public void getAllPodAllNamespace() throws IOException {

    }

    @Override
    public String createOrUpdateDeploy() throws Exception {
        try {
            MixedOperation<Deployment, DeploymentList, RollableScalableResource<Deployment>> operation = kubernetesClient.apps().deployments();
            String svcTemplateYaml = Utils.readFileToStringFromResource(NameTemplate.NAME_DEPLOY_TEMPLATE);
            File yamlFile = Utils.createTempFile(".yaml");
            Utils.writeStringToFile(yamlFile, svcTemplateYaml);
            System.out.println("ve nau an");
            Deployment deployment = operation.inNamespace("kong-dbless").load(yamlFile).get();
            if(deployment == null){
                throw new Exception("Init deployment error");
            }
//            setDeployHostAlias(deployment, new ArrayList<>());
            operation.inNamespace("kong-dbless").createOrReplace(deployment);
            Utils.deleteFile(yamlFile);
            //waitDeployRollingSuccess(namespace, deployName, 1, OPERATION_TIMEOUT_SECOND, 10);
            //return deployment.getMetadata().getName();

            return "Successfully";
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public String createServerSVC() throws Exception {
        try {
            MixedOperation<io.fabric8.kubernetes.api.model.Service, ServiceList, ServiceResource<io.fabric8.kubernetes.api.model.Service>> operation = kubernetesClient.services();
            String svcTemplateYaml = Utils.readFileToStringFromResource(NameTemplate.NAME_SVC_TEMPLATE);
            File yamlFile = Utils.createTempFile(".yaml");
            Utils.writeStringToFile(yamlFile, svcTemplateYaml);
            System.out.println("ve nau an");
            io.fabric8.kubernetes.api.model.Service service = operation.load(yamlFile).get();
            operation.inNamespace("kong-dbless").createOrReplace(service);
            Utils.deleteFile(yamlFile);
            return service.getMetadata().getName();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }



    @Override
    public String createPluginKong(String pluginName, String masterKey) throws Exception {
        try {
            CustomResourceDefinitionContext kongResource = new CustomResourceDefinitionContext.Builder()
                    .withKind("KongPlugin")
                    .withScope("Namespaced")
                    .withName("PLUGIN__NAME")
                    .withVersion("configuration.konghq.com/v1")
                    .withPlural("")
                    .build();
//            RawCustomResourceOperationsImpl
//                    operation = kubernetesClient.customResource(kongResource);
//            String ingressTemplateYaml = VBCSUtils.readFileToStringFromResource(ConstantDefine.NAME_PLUGIN_TEMPLATE);
//            String ingressYaml = ingressTemplateYaml
//                    .replace("INGRESS__NAME", masterKey)
//                    .replace("PLUGIN__NAME","plugin-validation");
//            File yamlFIle = VBCSUtils.createTempFile(".yaml");
//            VBCSUtils.writeStringToFile(yamlFIle,ingressYaml);
////            CustomResource customResource = operation.load(ingressYaml).get();
////            operation.createOrReplace(customResource);
//            operation.inNamespace("kong").createOrReplace(ingressYaml);
//            VBCSUtils.deleteFile(yamlFIle);
//            return pluginName;
            return "Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String createIngressKong() throws IOException {
        try{
            MixedOperation<io.fabric8.kubernetes.api.model.networking.v1.Ingress, IngressList, Resource<io.fabric8.kubernetes.api.model.networking.v1.Ingress>> operation = kubernetesClient.network().v1().ingresses();
            String ingressTemplateYaml = Utils.readFileToStringFromResource(NameTemplate.NAME_INGRESS_TEMPLATE);
            String ingressYaml = ingressTemplateYaml
                    .replace("__INGRESSNAME__",serviceConfiguration.getServicename()+"-ingress")
                    //.replace("__PLUGINNAME__",serviceConfiguration.getServicename()+"-plugin")
                    .replace("__NAMESPACE__", kubernetesConfiguration.getNamespace())
                    .replace("__ROUTE__", serviceConfiguration.getRoute())
                    .replace("__SERVICENAME__", serviceConfiguration.getServicename())
                    .replace("__PORT__", serviceConfiguration.getPort());
            File yamlFIle = Utils.createTempFile(".yaml");
            Utils.writeStringToFile(yamlFIle,ingressYaml);
            Ingress ingress = operation.load(yamlFIle).get();
            operation.inNamespace("kong-dbless").createOrReplace(ingress);
            Utils.deleteFile(yamlFIle);
            return ingress.getMetadata().getName();
        } catch (IOException e){
            Utils.logException(LOGGER, e);
            throw new IOException(e);
        }


    }

    @Override
    public String test(){
        System.out.println(pluginConfiguration.getMinute().toString());
        return "Test";
    }

    @Override
    public String createKongPlugin() throws IOException {
        MixedOperation<KongPlugin, KongPluginList, Resource<KongPlugin>> operation = null;
        operation = kubernetesClient.resources(KongPlugin.class, KongPluginList.class);
        String pluginTemplateYaml = Utils.readFileToStringFromResource(NameTemplate.NAME_PLUGIN_TEMPLATE);
        String pluginYaml = pluginTemplateYaml
                .replace("__PLUGINNAME__",serviceConfiguration.getServicename()+"-plugin")
                .replace("__NAMESPACE__", kubernetesConfiguration.getNamespace())
                .replace("__SECOND__", pluginConfiguration.getSecond().toString())
                .replace("__MINUTE__", pluginConfiguration.getMinute().toString())
                .replace("__HOUR__", pluginConfiguration.getHour().toString());
        File yamlFIle = Utils.createTempFile(".yaml");
        Utils.writeStringToFile(yamlFIle,pluginYaml);
        KongPlugin kongPlugin = operation.load(yamlFIle).get();
        operation.inNamespace("kong-dbless").createOrReplace(kongPlugin);
        return "done";
    }

    private void waitDeployRollingSuccess(String namespace, String deployName, int retryCount, int timeoutSecond, int maxRetry) throws Exception {
        try {
            kubernetesClient.apps().replicaSets().inNamespace(namespace).withLabel("app", deployName).waitUntilCondition(new Predicate<ReplicaSet>() {
                @Override
                public boolean test(ReplicaSet replicaSet) {
                    if(replicaSet.getStatus() == null){
                        return false;
                    }
                    //System.out.println(replicaSet.getMetadata().getName());
                    //System.out.println(replicaSet.getStatus());
                    return replicaSet.getStatus().getReadyReplicas() != null && replicaSet.getStatus().getReadyReplicas() > 0;
                }
            }, timeoutSecond, TimeUnit.SECONDS);
        } catch (KubernetesClientException e) {
            Utils.logException(LOGGER, e);
            if(retryCount > maxRetry){
                throw new Exception(e.getMessage());
            }
            Utils.logInfo(LOGGER, "Retry waiting... " + retryCount);
            waitingBySeconds(5 * retryCount);
            retryCount++;
            waitDeployRollingSuccess(namespace, deployName, retryCount, timeoutSecond, maxRetry);
        }catch (Exception e) {
            Utils.logException(LOGGER, e);
            throw new Exception(e.getMessage());
        }
    }
    private void setDeployHostAlias(Deployment deployment, List<HostAlias> hostAliases){
        if(deployment != null && !CollectionUtils.isEmpty(hostAliases)){
            deployment.getSpec().getTemplate().getSpec().setHostAliases(hostAliases);
        }
    }

    protected void waitingBySeconds(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (Exception e) {
            Utils.skipException(e);
        }
    }
}
