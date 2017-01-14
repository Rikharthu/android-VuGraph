package com.example.uberv.vugraph.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.uberv.vugraph.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingActivity extends AppCompatActivity {

    @BindView(R.id.logoIv)
    ImageView logoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        ButterKnife.bind(this);

        logoImage.startAnimation(
                AnimationUtils.loadAnimation(this, R.anim.rotate_indefinitely) );


    }

    private void onLoadingFinished(){

        Intent loginRegisterIntent = new Intent();
    }


}
