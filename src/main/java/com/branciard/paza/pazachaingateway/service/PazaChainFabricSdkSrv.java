package com.branciard.paza.pazachaingateway.service;

import com.branciard.paza.pazachaingateway.client.AuthorizedFeignClient;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by McFrancis on 16/11/2016.
 */
@AuthorizedFeignClient(name = "pazagateway")
public interface PazaChainFabricSdkSrv {
    @RequestMapping(value = "pazachainfabricsdksrv/api/profile-info")
    String profileInfo();
}
