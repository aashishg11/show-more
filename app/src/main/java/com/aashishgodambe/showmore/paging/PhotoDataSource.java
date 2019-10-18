package com.aashishgodambe.showmore.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.aashishgodambe.showmore.models.Photo;
import com.aashishgodambe.showmore.models.PhotosResponse;
import com.aashishgodambe.showmore.network.RetrofitClient;
import com.aashishgodambe.showmore.utils.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoDataSource extends PageKeyedDataSource<Integer, Photo> {

    private final String TAG = getClass().getSimpleName();

    public static final int PAGE_SIZE = 100;
    private static final int FIRST_PAGE = 1;
    private static final String FORMAT = "json";
    private static final String EXTRAS = "url_s";
    private String search = "";

    public PhotoDataSource(String search) {
        this.search = search;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Photo> callback) {
        RetrofitClient.getInstance()
                .getApi().getPhotos(Constants.METHOD, Constants.APIKEY,
                search, EXTRAS, FORMAT, 1, FIRST_PAGE)
                .enqueue(new Callback<PhotosResponse>() {
                    @Override
                    public void onResponse(Call<PhotosResponse> call, Response<PhotosResponse> response) {
                        if (response.body().getPhotos() != null) {
                            Set<String> photoSet= new HashSet<String>();
                            ArrayList uniquePhotos = new ArrayList();
                            for (Photo photo : response.body().getPhotos().getPhoto()){
                                if (!photoSet.contains(photo.getTitle())){
                                    photoSet.add(photo.getTitle());
                                    uniquePhotos.add(photo);
                                }
                            }
                            callback.onResult(uniquePhotos, null, FIRST_PAGE + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<PhotosResponse> call, Throwable t) {
                        Log.d(TAG, t.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Photo> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Photo> callback) {
        RetrofitClient.getInstance()
                .getApi().getPhotos(Constants.METHOD, Constants.APIKEY,
                search, EXTRAS, FORMAT, 1, params.key)
                .enqueue(new Callback<PhotosResponse>() {
                    @Override
                    public void onResponse(Call<PhotosResponse> call, Response<PhotosResponse> response) {
                        if (response.body().getPhotos() != null) {
                            Set<String> photoSet= new HashSet<String>();
                            ArrayList uniquePhotos = new ArrayList();
                            for (Photo photo : response.body().getPhotos().getPhoto()){
                                if (!photoSet.contains(photo.getTitle())){
                                    photoSet.add(photo.getTitle());
                                    uniquePhotos.add(photo);
                                }
                            }
                            callback.onResult(uniquePhotos, params.key + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<PhotosResponse> call, Throwable t) {
                        Log.d(TAG, t.getLocalizedMessage());
                    }
                });
    }

}
