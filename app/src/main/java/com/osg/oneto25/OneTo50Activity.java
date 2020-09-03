package com.osg.oneto25;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

    TextView tv;
    Button[] btns = new Button[25];
    Button btnStart, btnMain;

    ArrayList<Integer> nums=new ArrayList<>();

    Drawable btnBack;

    static int clicked=0;
    static int index=0;
    int cnt=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_to50);

        tv=findViewById(R.id.tv);

        for(int i=0;i<btns.length;i++){
            btns[i]=findViewById(R.id.btn01+i);
            btns[i].setClickable(false);
        }
        btnBack=btns[0].getBackground();

        //두 번째 랜덤 숫자 생성
        for(int i=26;i<=50;i++){
            nums.add(i);
        }
        Collections.shuffle(nums);

        initial();
    }

    public void initial(){
        ArrayList<Integer> nums1=new ArrayList<>();
        for(int i=1;i<=25;i++){
            nums1.add(i);
        }
        Collections.shuffle(nums1);

        for(int i=0;i<btns.length;i++){
            btns[i].setText(nums1.get(i).toString());
            btns[i].setBackground(btnBack);
            btns[i].setTextColor(Color.BLACK);
            btns[i].setClickable(false);
        }

    }

    public void clickStart(View view) {
        for(int i=0;i<btns.length;i++){
            btns[i].setClickable(true);
        }
    }

    public void clickMain(View view) {

    }

    public void clickBtn(View view) {
        Button btn=(Button)view;
        int num = Integer.parseInt(btn.getText().toString());
        if(num==cnt){
            if(cnt<=25){
                btn.setBackgroundColor(Color.WHITE);
                btn.setText(nums.get(index).toString());
                index++;
                cnt++;
            }else if(cnt>25){
                btn.setBackgroundColor(Color.WHITE);
                btn.setText("OK");
                index++;
                cnt++;
            }else if(cnt>50){
                Toast.makeText(this, "Rmc", Toast.LENGTH_SHORT).show();   
            }
        }

    }
}