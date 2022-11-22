package com.if5b.contact.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.if5b.contact.databinding.ActivityInputBinding;
import com.if5b.contact.databinding.ActivityMainBinding;
import com.if5b.contact.entities.User;
import com.if5b.contact.loaders.InsertLoader;
import com.if5b.contact.loaders.UpdateDataLoader;

public class InputActivity extends AppCompatActivity {

    private static final int INSERT_LOADER = 121;
    private static final int UPDATE_LOADER = 122;
    private ActivityInputBinding binding;
    private boolean editMode;
    private int userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInputBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        editMode = getIntent().getBooleanExtra("edit", false);
        if(editMode) {
            User user = getIntent().getParcelableExtra("user");
            userId = user.getId();
            setDetails(user);
        }

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateFiels()){
                    if(editMode){
                        updateUser();
                    }else {
                        insertUser();
                    }
                }
            }
        });
    }

    private void updateUser() {
        User user = new User();
        user.setId(userId);
        user.setName(binding.etName.getText().toString());
        user.setEmail(binding.etEmail.getText().toString());
        user.setPhone(binding.etPhone.getText().toString());
        showProgressbar();
        Bundle args = new Bundle();
        args.putParcelable("user", user);
        LoaderManager.getInstance(this).restartLoader(UPDATE_LOADER, args, new LoaderManager.LoaderCallbacks<Integer>() {
            @NonNull
            @Override
            public Loader<Integer> onCreateLoader(int id, @Nullable Bundle args) {
                return new UpdateDataLoader(InputActivity.this, args.getParcelable("user"));
            }

