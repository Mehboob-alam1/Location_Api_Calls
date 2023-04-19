package com.mehboob.demo;



import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GetData {


    @Headers({"Content-Type: application/json"})
    @GET(" ")
    Call<Location> getLocation(@Query(value = "") String location);


}

