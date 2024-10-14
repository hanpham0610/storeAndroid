package com.example.store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.store.Controller.SanPham.CreateProductController;
import com.example.store.databinding.ActivityMainBinding;

import com.example.store.Fragment.FragmentHomeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FloatingActionButton floatingActionButtonCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new FragmentHomeActivity());
        floatingActionButtonCreate = findViewById(R.id.createProduct);
        final Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scan_img);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });



        floatingActionButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateProductController();
            }
        });
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new FragmentHomeActivity());
                    break;
                case R.id.shorts:
                    replaceFragment(new FragmentHomeActivity());
                    break;
                case R.id.subscriptions:
                    replaceFragment(new FragmentHomeActivity());
                    break;
                case R.id.library:
                    replaceFragment(new FragmentHomeActivity());
                    break;
            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private  void  onCreateProductController(){
        Intent intent = new Intent(MainActivity.this, CreateProductController.class);
        startActivity(intent);
    }
}