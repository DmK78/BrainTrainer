package com.android.dmk78.braintrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinishActivity extends AppCompatActivity {
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        textView=findViewById(R.id.textView);

        Intent intent = getIntent();
        int myScore = intent.getIntExtra("myScore",0);
        int topScore = intent.getIntExtra("topScore",0);
        textView.setText("Ваш результат: "+ myScore+"\n" +
                "Максимальный результат: "+topScore);


    }

    public void toMain(View view) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
