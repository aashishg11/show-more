package com.aashishgodambe.showmore.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.aashishgodambe.showmore.models.Photo;

public class PhotoDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, Photo>> photoLiveDataSource = new MutableLiveData<>();
    private String search = "";

    @NonNull
    @Override
    public DataSource create() {
        PhotoDataSource photoDataSource = new PhotoDataSource(search);

        photoLiveDataSource.postValue(photoDataSource);

        return photoDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Photo>> getItemLiveDataSource(String search) {
        this.search = search;
        return photoLiveDataSource;
    }
}
