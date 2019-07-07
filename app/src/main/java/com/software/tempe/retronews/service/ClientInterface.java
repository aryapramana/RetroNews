package com.software.tempe.retronews.service;

import com.software.tempe.retronews.model.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClientInterface {
    @GET("top-headlines")
    Call<News> getNews(
            @Query("country") String country,
            @Query("pageSize") int pageSize,
            @Query("page") int page,
            @Query("apiKey") String apiKey
    );

    @GET("everything")
    Call<News> getKeyword (
            @Query("q") String q,
            @Query("pageSize") int pageSize,
            @Query("page") int page,
            @Query("language") String language,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey
    );
}
