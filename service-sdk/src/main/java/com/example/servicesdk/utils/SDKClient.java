package com.example.servicesdk.utils;

import com.example.servicesdk.eum.SDKConfig;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SDKClient {
    private static PopClient popClient;
    public static PopClient getPopClient() {
        if (popClient == null) {
            Lock lock = new ReentrantLock(false);
            try {
                lock.lock();
                popClient = new PopHttpClient(SDKConfig.PDDKEY, SDKConfig.PDDSECRET);
                return popClient;
            } finally {
                lock.unlock();
            }
        }
        return null;
    }
}
