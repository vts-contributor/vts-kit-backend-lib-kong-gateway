[![][kong-logo]][kong-url]


## VTS KIT LIB KONG
The vts-kit-lib-kong library is developed with the intention of automating the deployment of serivce with the kong ingress controller, making it easier and faster to deploy and develop microservices. In this first version we provide automation of service registration, route configuration to kong gateway.

## Get started


* Just add the dependency to an existing Spring Boot project
```xml
<dependency>
    <groupId>com.atviettelsolutions</groupId>
    <artifactId>vts-kit-ms-kong</artifactId>
    <version>1.0.0</version>
</dependency>
```
* Then, beyond the simple Boot annotation, notice that we're using the enable-style of annotation for the Zuul proxy as well, which is pretty cool, clean and concise. 
```Java
@EnableKongGateway
@SpringBootApplication
public class VtsKitMsTestKongApplication implements CommandLineRunner{
    public static void main(String[] args) {
            SpringApplication.run(VtsKitMsTestKongApplication.class, args);
        }
    public void run(String... args) throws Exception {       
    }
}
```
* Then, add the following properties to your `application-*.yml` file.
```yaml
kong:
  k8s:
    configfile: config/config
    namespace: kong-dbless
    dbless:
      service:
        config:
          route: /test
          port: 8080
          servicename: echo-svc
```
`configfile`: File config k8s \
`namespace`: Namespace in k8s in which you are deploying the service \
`route`: Specified route for each service\
`port`: Service port in k8s\
`servicename`: Service name in k8s


Note: These configs are needed for deploying your service with the kong ingress controller, however all of this is deployed in the container environment, not localhost.



[kong-url]: https://konghq.com/
[kong-logo]: https://konghq.com/wp-content/uploads/2018/05/kong-logo-github-readme.png
