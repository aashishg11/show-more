package com.aashishgodambe.showmore.views;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.aashishgodambe.showmore.models.Photo;
import com.aashishgodambe.showmore.paging.PhotoDataSource;
import com.aashishgodambe.showmore.paging.PhotoDataSourceFactory;

public class MainActivityViewModel extends ViewModel {

    LiveData<PagedList<Photo>> itemPagedList;
    LiveData<PageKeyedDataSource<Integer, Photo>> liveDataSource;
    PhotoDataSourceFactory photoDataSourceFactory;
    PagedList.Config pagedListConfig;

    public MainActivityViewModel() {

        photoDataSourceFactory = new PhotoDataSourceFactory();

        //Getting PagedList config
        pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(PhotoDataSource.PAGE_SIZE).build();
        //itemPagedList = (new LivePagedListBuilder<Integer,Photo>(photoDataSourceFactory,pagedListConfig)).build();


    }

    public void getPhotos(String search){
        liveDataSource = photoDataSourceFactory.getItemLiveDataSource(search);
        itemPagedList = (new LivePagedListBuilder<Integer,Photo>(photoDataSourceFactory,pagedListConfig)).build();

    }

    public LiveData<PagedList<Photo>> getItemPagedList() {
        return itemPagedList;
    }
}
