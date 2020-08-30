package com.example.androidpromoteroad.annotationprocess;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidpromoteroad.R;
import com.example.lib_annotation.BindView;
import com.example.lib_bind.Binding;

public class AnnotationMainActivity extends AppCompatActivity {

    @BindView(R.id.tvAnnotation)
    TextView tvAnnotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation_process);

        Binding.bind(this);
        tvAnnotation.setText("绑定text成功");
    }

}