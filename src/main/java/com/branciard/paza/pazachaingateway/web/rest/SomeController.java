package com.branciard.paza.pazachaingateway.web.rest;


import com.branciard.paza.pazachaingateway.service.PazaChainFabricSdkSrv;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
public class SomeController {

    @Inject
    private PazaChainFabricSdkSrv pazaChainFabricSdkSrv;

    @GetMapping("/api/client/profile-info")
    public String profileInfo() {
        return pazaChainFabricSdkSrv.profileInfo();
    }
}
