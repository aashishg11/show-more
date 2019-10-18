package com.aashishgodambe.showmore.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.aashishgodambe.showmore.R;
import com.aashishgodambe.showmore.models.PhotosResponse;
import com.aashishgodambe.showmore.network.RetrofitClient;
import com.aashishgodambe.showmore.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RetrofitClient.getInstance()
                .getApi().getPhotos(Constants.METHOD, Constants.APIKEY,
                "cat", "url_s", "json", 1, 1)
                .enqueue(new Callback<PhotosResponse>() {
                    @Override
                    public void onResponse(Call<PhotosResponse> call, Response<PhotosResponse> response) {
                        Log.d(TAG, response.body().getPhotos().getPages() + "");
                    }

                    @Override
                    public void onFailure(Call<PhotosResponse> call, Throwable t) {
                        Log.d(TAG, t.getLocalizedMessage());
                    }
                });
    }
}
