package com.visperas.rolito.block6.p1.budget_tracking.activities;

import android.os.Bundle;
import android.renderscript.ScriptGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.progressindicator.BaseProgressIndicator;
import com.visperas.rolito.block6.p1.budget_tracking.R;
import com.visperas.rolito.block6.p1.budget_tracking.databinding.ActivityMainBinding;

public class Dashboard extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}
