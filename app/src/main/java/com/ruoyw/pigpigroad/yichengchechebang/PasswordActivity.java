package com.ruoyw.pigpigroad.yichengchechebang;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.ruoyw.pigpigroad.yichengchechebang.SlideBack.SlidingLayout;

/**
 * Created by XYSM on 2018/3/17.
 */

public class PasswordActivity extends AppCompatActivity {
    private LinearLayout back_ll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.password_layout);
        back_ll = findViewById(R.id.back_ll);
        back_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (enableSliding()) {
            SlidingLayout rootView = new SlidingLayout(this);
            rootView.bindActivity(this);
        }
    }

    protected boolean enableSliding() {
        return true;
    }

}

