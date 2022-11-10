package com.if5b.contact.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.if5b.contact.R;
import com.if5b.contact.adapter.UserViewAdapter;
import com.if5b.contact.databinding.ActivityMainBinding;
import com.if5b.contact.entities.User;
import com.if5b.contact.loaders.getDataLoader;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private  static  final int DATA_LOADER_CODE = 123;
    private  static final int DELETE_LOADER_CODE = 124;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
    }
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        showProgressBar();
        LoaderManager.getInstance(this).restartLoader(DATA_LOADER_CODE, null, new LoaderManager.LoaderCallbacks<List<User>>() {
            @NonNull
            @Override
            public Loader<List<User>> onCreateLoader(int id, @Nullable Bundle args) {
                return new getDataLoader(MainActivity.this);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<List<User>> loader, List<User> data) {
                hideProgressBar();
                initAdapter(data);

            }

            @Override
            public void onLoaderReset(@NonNull Loader<List<User>> loader) {

            }
        }).forceLoad();
    }

    private void initAdapter(List<User> data) {
        UserViewAdapter userViewAdapter = new UserViewAdapter();
        binding.rvUser.setLayoutManager(new LinearLayoutManager(this));
        binding.rvUser.setAdapter(userViewAdapter);
        userViewAdapter.setData(data);
        userViewAdapter.setOnClickListener(new UserViewAdapter.OnClickListener() {
            @Override
            public void onEditClicked(User user) {
                gotoUpdateUserActivity(user);
                
            }

            @Override
            public void onDeleteClicked(int UserId) {
                deleteUser(userId);

            }
        });
    }

    private void gotoUpdateUserActivity(User user) {
    }

    private void  hideProgressBar(){
        binding.progressBar.setVisibility(View.GONE);
    }
    private  void  showProgressBar(){
        binding.progressBar.setVisibility(View.VISIBLE);
    }
}