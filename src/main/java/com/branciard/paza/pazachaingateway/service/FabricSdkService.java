package com.branciard.paza.pazachaingateway.service;

import com.branciard.paza.pazachaingateway.domain.ChainUser;
import com.branciard.paza.pazachaingateway.repository.ChainUserRepository;
import com.branciard.paza.pazachaingateway.service.dto.ChainUserDTO;
import com.branciard.paza.pazachaingateway.service.mapper.ChainUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import com.branciard.paza.pazachaingateway.config.MicroserviceSecurityConfiguration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import javax.inject.Inject;
import java.util.Map;

/**
 * Service Implementation for managing ChainUser.
 */
@Service
@Transactional
public class FabricSdkService {

    private final Logger log = LoggerFactory.getLogger(FabricSdkService.class);

   /* @Inject
    DiscoveryClient discoveryClient;


    @Bean
    public RestTemplate loadBalancedRestTemplate(RestTemplateCustomizer customizer) {
        RestTemplate restTemplate = new RestTemplate();
        customizer.customize(restTemplate);
        return restTemplate;
    }

    @Inject
    @Qualifier("loadBalancedRestTemplate")
    private RestTemplate keyUriRestTemplate;


    private String getKeyFromAuthorizationServer() {
        // Load available UAA servers
        discoveryClient.getServices();
        HttpEntity<Void> request = new HttpEntity<Void>(new HttpHeaders());
        return (String) this.keyUriRestTemplate
            .exchange("http://pazauaa/oauth/token_key", HttpMethod.GET, request, Map.class).getBody()
            .get("value");

    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(getKeyFromAuthorizationServer());
        return converter;
    }


    public String pingFabricSdkService(){
        String result="NOK";
        HttpHeaders httpHeaders= new HttpHeaders();

      //  OAuth2ClientContext context = new DefaultOAuth2ClientContext(microserviceSecurityConfiguration.tokenStore().findTokensByClientId());
      //  OAuth2ProtectedResourceDetails resource = new ClientCredentialsResourceDetails();
       // resource.
      //  DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
      //  OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(resourceDetails, clientContext);
       // log.error("pingFabricSdkService tokenStore ===>"+microserviceSecurityConfiguration.tokenStore().);
        try {
        httpHeaders.set("Authorization", "Bearer "+jwtAccessTokenConverter());

        HttpEntity<Void> request = new HttpEntity<Void>(httpHeaders);


                result=this.keyUriRestTemplate
                    .exchange("http://pazagateway/api/profile-info",HttpMethod.GET,request, String.class).toString();
                log.error("pingFabricSdkService ===>"+result);
            }
            catch(Throwable throwable){
                log.error("pingFabricSdkService",throwable);
            }


        return  result;


    }
*/

    public String pingFabricSdkService(){
        log.error(" pingFabricSdkService start");
     //   log.error("pingFabricSdkService"+ pazaChainFabricSdkSrv.profileInfo());
        log.error(" pingFabricSdkService end");
    return null;
    }


}
