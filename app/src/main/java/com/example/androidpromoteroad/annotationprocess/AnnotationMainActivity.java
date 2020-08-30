package com.example.androidpromoteroad.annotationprocess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.androidpromoteroad.R;
import com.example.lib_reflection.BindView;
import com.example.lib_reflection.Binding;

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

    /**
     * replace deprecated way {@link #newFunction()}
     */
    @Deprecated
    void testDeprecated() {

    }

    void newFunction() {

    }
}