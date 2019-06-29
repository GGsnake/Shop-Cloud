package com.example.servicesdk.utils;

import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import org.springframework.stereotype.Component;

@Component
public class SDKClient {
    public static PopClient getPopClient(){
        PopClient client = new PopHttpClient("48dcc8985c8e4838be2dea3aa9b6176f", "62b91c07f697bce84beec4123b01f6e108d2fa38");
        return client;
    }
}
