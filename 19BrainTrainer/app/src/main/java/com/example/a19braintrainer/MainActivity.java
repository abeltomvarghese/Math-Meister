package com.example.a19braintrainer;

import android.arch.lifecycle.ViewModelStoreOwner;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button[] allButtons = new Button[4];
    Button startB;
    CountDownTimer timer;
    TextView timeLeft;
    TextView displayQuestion;
    TextView score;
    Boolean isRunning = false;
    Integer[] answers = new Integer[4];
    Button b0;
    Button b1;
    Button b2;
    Button b3;
    int locationAns;
    int totalScore;
    int correctScore;
    TextView percent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalScore = 0;
        correctScore = 0;

        score = (TextView) findViewById(R.id.textView3);
        percent = (TextView) findViewById(R.id.textView2);

        startB = (Button) findViewById(R.id.startButton);
        timeLeft = (TextView) findViewById(R.id.textViewTime);
        displayQuestion = (TextView) findViewById(R.id.textView4);

        b0 = (Button) findViewById(R.id.button0);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);

        allButtons[0] = b0;
        allButtons[1] = b1;
        allButtons[2] = b2;
        allButtons[3] = b3;
        checkButtons(false);
        newQuestion();


    }
    public void choose(View view) {
        totalScore++;
        if(Integer.toString(locationAns).equals(view.getTag().toString())){
            correctScore++;
        }
        score.setText(correctScore + "/" + totalScore);
        double percentage = ((correctScore/ (double) totalScore) * 100);
        String output = String.format(String.valueOf("%.0f"),percentage);

        percent.setText(output + "%");
        if (percentage >= 90) {
            percent.setTextColor(Color.parseColor("#308014"));
        } else if (percentage >= 75) {
            percent.setTextColor(Color.parseColor("#7BCC70"));
        } else if (percentage >= 40) {
            percent.setTextColor(Color.parseColor("#eabe6e"));
        } else {
            percent.setTextColor(Color.parseColor("#e53111"));
        }
        newQuestion();
    }
    public void playGame(View view) {
        if (isRunning) {
            isRunning = false;
            timer.cancel();
            changeControl(true);

            startOver();
            checkButtons(false);
        } else {
            newQuestion();
            checkButtons(true);
            isRunning = true;
            timer = new CountDownTimer(30100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTime((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    checkButtons(false);
                    changeControl(true);
                }
            }.start();
            changeControl(false);
            
        }
    }

    public void updateTime(int secondsLeft) {
        String formatTime;
        formatTime = String.valueOf(secondsLeft) + "s";
        timeLeft.setText(formatTime);
    }

    public void newQuestion() {
        Random rand = new Random();
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);
        displayQuestion.setText(Integer.toString(a) + " + " + Integer.toString(b));

        locationAns = rand.nextInt(4);
        answers[locationAns] = a+b;

        for (int x = 0; x < 4; x++) {
            if (x != locationAns) {
                int wrongAns = 0;
                do {
                    wrongAns = rand.nextInt(42);
                } while (wrongAns == a+b);
                answers[x] = wrongAns;
            }
        }

        for (int counter = 0; counter < 4; counter++) {
            allButtons[counter].setText(Integer.toString(answers[counter]));
        }
    }

    public void checkButtons (boolean status) {
        if (status) {
            for (Button b : allButtons) {
                b.setEnabled(true);
            }
        } else {
            for (Button b : allButtons) {
                b.setEnabled(false);
            }
        }
    }
    public void changeControl (boolean text) {
        if (text) {
            startB.setText("Start");
            startB.getBackground().setColorFilter(Color.parseColor("#06b844"),PorterDuff.Mode.SRC_OVER);
        } else {
            startB.setText("Quit");
            startB.getBackground().setColorFilter(Color.parseColor("#d13411"),PorterDuff.Mode.SRC_OVER);
        }
    }
    public void startOver() {
        totalScore =0;
        correctScore =0;
        timeLeft.setText("30s");
        score.setText("0/0");
    }
}
