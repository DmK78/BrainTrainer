package com.android.dmk78.braintrainer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView textViewTimer;
    private int secondsTotal = 60;
    private int number1;
    private int number2;
    private int result;
    private TextView textViewVar1;
    private TextView textViewVar2;
    private TextView textViewVar3;
    private TextView textViewVar4;
    private TextView textViewDecision;
    private int totalQuestions = 0;
    private int rightQuestions = 0;
    private int topScore;
    private TextView textViewAnswers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewTimer = findViewById(R.id.textViewTimer);
        textViewVar1 = findViewById(R.id.textViewVar1);
        textViewVar2 = findViewById(R.id.textViewVar2);
        textViewVar3 = findViewById(R.id.textViewVar3);
        textViewVar4 = findViewById(R.id.textViewVar4);
        textViewDecision = findViewById(R.id.textViewDecision);
        textViewAnswers = findViewById(R.id.textViewAnswers);


        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //preferences.edit().putInt("topScore", 0);

        topScore = preferences.getInt("topScore", 0);


        textViewAnswers.setText("" + totalQuestions + "/" + rightQuestions);

        final CountDownTimer timer = new CountDownTimer(secondsTotal * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                seconds++;
                textViewTimer.setText(Integer.toString(seconds));

            }

            @Override
            public void onFinish() {
                //Toast.makeText(MainActivity.this, "Таймер завершен", Toast.LENGTH_SHORT).show();
                textViewTimer.setText(Integer.toString(0));
                if (rightQuestions > topScore) {
                    topScore = rightQuestions;
                }

                preferences.edit().putInt("topScore", topScore).apply();
                Intent intent = new Intent(getApplicationContext(), FinishActivity.class);
                intent.putExtra("myScore", rightQuestions);
                intent.putExtra("topScore", topScore);
                startActivity(intent);


            }
        };
        timer.start();
        guessNumber();
    }

    private void guessNumber() {


        number1 = (int) ((Math.random() * 100) + 1);
        number2 = (int) ((Math.random() * 100) + 1);
        int action = (int) (Math.random() * 100);
        if (action < 50) {
            result = number1 + number2;
            textViewDecision.setText(String.valueOf(number1 + "+" + number2));


        } else if (action >= 50) {

            result = number1 - number2;
            textViewDecision.setText(String.valueOf(number1 + "-" + number2));

        } else Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();

        int varRand1 = (int) (Math.random() * (number1 + number1));
        int varRand2 = (int) (Math.random() * (number1 - number2));
        int varRand3 = (int) (Math.random() * (number2 - number1));


        int randomTrueButton = (int) ((Math.random() * 4) + 1);
        switch (randomTrueButton) {
            case 1:
                textViewVar1.setText("" + result);

                textViewVar2.setText("" + varRand1);
                textViewVar3.setText("" + varRand2);
                textViewVar4.setText("" + varRand3);

                break;
            case 2:
                textViewVar2.setText("" + result);

                textViewVar1.setText("" + varRand1);
                textViewVar3.setText("" + varRand2);
                textViewVar4.setText("" + varRand3);
                break;
            case 3:
                textViewVar3.setText("" + result);

                textViewVar1.setText("" + varRand1);
                textViewVar2.setText("" + varRand2);
                textViewVar4.setText("" + varRand3);
                break;
            case 4:
                textViewVar4.setText("" + result);

                textViewVar1.setText("" + varRand1);
                textViewVar2.setText("" + varRand2);
                textViewVar3.setText("" + varRand3);
                break;

            default:
                break;
        }


    }

    public void checkAnswer(View view) {
        TextView answer = (TextView) view;
        int check = Integer.parseInt((String) answer.getText());
        if (check == result) {
            Toast.makeText(this, "Верно", Toast.LENGTH_SHORT).show();
            rightQuestions++;
        }
        totalQuestions++;
        textViewAnswers.setText("" + totalQuestions + "/" + rightQuestions);


        guessNumber();
    }
}
