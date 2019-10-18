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
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aashishgodambe.showmore.R;
import com.aashishgodambe.showmore.adapter.PhotoAdapter;
import com.aashishgodambe.showmore.models.Photo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements PhotoAdapter.OnItemClickListener {

    private final String TAG = getClass().getSimpleName();

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MainActivityViewModel viewModel;
    private PhotoAdapter adapter;
    private Toast mToast;

    @BindView(R.id.editText)
    EditText editText;

    @BindView(R.id.progressBar)
    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //setting up recyclerview
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        adapter = new PhotoAdapter(this,this);
        recyclerView.setAdapter(adapter);

    }

    @OnClick(R.id.button)
    public void submitClick(){
        spinner.setVisibility(View.VISIBLE);
        viewModel.getPhotos(editText.getText().toString());
        viewModel.itemPagedList.observe(this, new Observer<PagedList<Photo>>() {
            @Override
            public void onChanged(PagedList<Photo> photos) {
                adapter.submitList(photos);
                spinner.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onItemClick(Photo item) {
        if (mToast != null){
            mToast.cancel();
        }
        mToast = Toast.makeText(this,item.getTitle(),Toast.LENGTH_SHORT);
        mToast.show();
    }
}
