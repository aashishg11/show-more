package com.aashishgodambe.showmore.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.aashishgodambe.showmore.R;
import com.aashishgodambe.showmore.adapter.PhotoAdapter;
import com.aashishgodambe.showmore.models.Photo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MainActivityViewModel viewModel;
    private PhotoAdapter adapter;

    @BindView(R.id.editText)
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //setting up recyclerview
        recyclerView = findViewById(R.id.recyclerview);
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
//                StaggeredGridLayoutManager.VERTICAL);
//        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
//        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        adapter = new PhotoAdapter(this);


        recyclerView.setAdapter(adapter);

    }

    @OnClick(R.id.button)
    public void submitClick(){
        viewModel.getPhotos(editText.getText().toString());
        viewModel.itemPagedList.observe(this, new Observer<PagedList<Photo>>() {
            @Override
            public void onChanged(PagedList<Photo> photos) {
                adapter.submitList(photos);
            }
        });
    }
}
