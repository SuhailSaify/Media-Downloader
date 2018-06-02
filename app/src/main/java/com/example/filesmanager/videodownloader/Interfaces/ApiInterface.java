package com.example.filesmanager.videodownloader.Interfaces;

/**
 * Created by Suhail on 8/7/2017.
 */


import com.example.filesmanager.videodownloader.model.Example;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

import retrofit2.http.GET;

import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


public interface ApiInterface {

    /**
     *Login Client
     */

    @GET("process/")
    Call <Example> getvideo(
            @Query("url") String url,
            @Query("type") String type);

}

