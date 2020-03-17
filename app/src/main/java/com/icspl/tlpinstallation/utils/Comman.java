package com.icspl.tlpinstallation.utils;

import com.icspl.tlpinstallation.handler.ApiService;

public class Comman {
   // public static final String BASE_URL = "http://icspl.org:/Home/"; // testing live url

     private static final String BASE_URL = "http://192.168.0.243:4004/Home/";
    public static ApiService getAPI() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}
