package com.example.turtle_neck;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView tv_email, tv_pw;
    private Button btn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_email = findViewById(R.id.user_Email);
        tv_pw = findViewById(R.id.user_Password);

        btn_back = findViewById(R.id.button);

        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("userEmail");
        String userPw = intent.getStringExtra("userPw");

        tv_email.setText(userEmail);
        tv_pw.setText(userPw);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, activity_login.class);
                startActivity(intent1);
            }
        });
    }
}