package com.osg.oneto25;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class OneTo50Activity extends AppCompatActivity {

    TextView tvStopWatch, tvRec;
    Button[] btns = new Button[25];
    Button btnStart, btnGone;

    ArrayList<Integer> nums = new ArrayList<>();

    Drawable btnBack, btnBack2;

    static int index = 0;
    int cnt = 1;

    boolean isRun;
    int min = 0;
    int sec = 0;
    int milsec = 0;
    int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_to50);


        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("1-50");

        tvStopWatch = findViewById(R.id.tvStopWatch);
        tvRec = findViewById(R.id.tvRec);
        btnStart = findViewById(R.id.btnStart);
        btnGone=findViewById(R.id.btnBack2);
        showRec();

        for (int i = 0; i < btns.length; i++) {
            btns[i] = findViewById(R.id.btn01 + i);
            btns[i].setClickable(false);
        }
        btnBack = btns[0].getBackground();

        //두 번째 랜덤 숫자 생성
        for (int i = 26; i <= 50; i++) nums.add(i);
        Collections.shuffle(nums);

        //화면 초기화
        initial();
    }

    public void initial() {
        ArrayList<Integer> nums1 = new ArrayList<>();
        for (int i = 1; i <= 25; i++) {
            nums1.add(i);
        }
        Collections.shuffle(nums1);

        for (int i = 0; i < btns.length; i++) {
            btns[i].setText(nums1.get(i).toString());
            btns[i].setBackground(btnBack);
            btns[i].setTextColor(Color.BLACK);
            btns[i].setClickable(false);
            btns[i].setTag(nums1.get(i));
        }

    }

    public void clickStart(View view) {
        initial();
        for (int i = 0; i < btns.length; i++) {
            btns[i].setClickable(true);
        }
        btnStart.setEnabled(false);

        TimeThread timeThread=new TimeThread();
        timeThread.start();

    }

    public void clickBtn(View view) {
        Button btn = (Button) view;
        int num = Integer.parseInt(btn.getTag().toString());
        if (num == cnt) {
            if (cnt <= 25) {
                btnBack2=btnGone.getBackground();
                btn.setBackground(btnBack2);
                btn.setText(nums.get(index).toString());
                btn.setTag(nums.get(index));
                index++;
                cnt++;
            } else if (cnt > 25 && cnt<=50) {
                btn.setBackgroundColor(Color.WHITE);
                btn.setText("OK");
                index++;
                cnt++;
            }
        }

        if (cnt > 50) {
            isRun = false;
            btnStart.setEnabled(true);
            btnStart.setText("restart");

            int result = milsec + (sec * 10) + (min * 600);
            SharedPreferences sharedPreferences = getSharedPreferences("record2", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            int record = sharedPreferences.getInt("50", 0);
            if (result < record || record == 0) {
                editor.putInt("50", result);
                editor.commit();
                tvRec.setText("최고 기록 " + min + " : " + sec + "." + milsec);
                new AlertDialog.Builder(this).setMessage("축하합니다! \n새 기록을 달성했습니다").show();
            }
        }
    }

    public void clickMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void showRec(){
        SharedPreferences sharedPreferences=getSharedPreferences("record2", MODE_PRIVATE);
        int recordhistory=sharedPreferences.getInt("50", 0);
        int m=recordhistory/6000;
        int s=recordhistory%6000/10;
        int ms=recordhistory%6000%10;

        tvRec.setText("최고 기록 " + m + " : " + s + "." + ms);
    }

    class TimeThread extends Thread{
        @Override
        public void run() {
            isRun = true;
            while (isRun) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        time++;
                        milsec = time % 10;
                        sec = (time / 100) % 60;
                        min = (time / 100) / 60;
                        String record = String.format("%02d : %02d.%02d", min, sec, milsec);
                        tvStopWatch.setText(record);
                    }
                });
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopThread();
    }

    public void stopThread(){
        isRun=false;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}