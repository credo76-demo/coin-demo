package org.credo.labs.coindemo.core.client;

import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

/**
 * OkHttpClient 使用 Singleton Pattern 來確保只有一個 OkHttpClient 實例 (可以重複使用 connections 和 threads)。
 */
public class OkHttpSingleton {
    private static OkHttpClient instance;

    private OkHttpSingleton() {
    }

    public static OkHttpClient getInstance() {
        if (instance == null) {
            synchronized (OkHttpSingleton.class) {
                if (instance == null) {
                    instance = new OkHttpClient.Builder()
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(10, TimeUnit.SECONDS)
                            .connectionPool(new ConnectionPool(5, 5, TimeUnit.MINUTES))
                            .retryOnConnectionFailure(true) // Enable retry on connection failure
                            .build();
                }
            }
        }
        return instance;
    }
}
