package com.aashishgodambe.showmore.network;

import com.aashishgodambe.showmore.models.PhotosResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrApi {

    @GET("services/rest/")
    Call<PhotosResponse> getPhotos(@Query("method") String method,
                                   @Query("api_key") String apiKey,
                                   @Query("text") String text,
                                   @Query("extras") String extras,
                                   @Query("format") String format,
                                   @Query("nojsoncallback") int num,
                                   @Query("page") int page);

}
