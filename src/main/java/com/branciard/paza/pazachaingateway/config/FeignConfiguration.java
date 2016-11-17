package com.branciard.paza.pazachaingateway.config;


import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * Created by McFrancis on 16/11/2016.
 */

@Configuration
@EnableFeignClients(basePackages = "com.branciard.paza.pazachaingateway")
public class FeignConfiguration {

}
