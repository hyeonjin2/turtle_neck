package com.example.turtle_neck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class activity_login extends AppCompatActivity {

    private EditText edit_email, edit_pw;
    private Button btn_login, checkbox;
    private TextView btn_sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edit_email = findViewById(R.id.login_email);
        edit_pw = findViewById(R.id.login_pw);

        btn_login = findViewById(R.id.btn_login);
        btn_sign = findViewById(R.id.text_sign);
        checkbox = findViewById(R.id.checkBox_log_in);


        // 회원가입 버튼 눌렀을 때
        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_login.this, activity_join.class);
                startActivity(intent);
            }
        });

        // 로그인 버튼 눌렀을 때
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = edit_email.getText().toString();
                String userPw = edit_pw.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if(success){    // 로그인에 성공한 경우
                                String userEmail = jsonObject.getString("userEmail");
                                String userPw = jsonObject.getString("userPw");

                                Toast.makeText(activity_login.this, "로그인에 성공하였습니다!"
                                        , Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(activity_login.this, MainActivity.class);
                                intent.putExtra("userEmail", userEmail);
                                intent.putExtra("userPw", userPw);
                                startActivity(intent);
                            }else{  // 로그인에 실패한 경우
                                Toast.makeText(activity_login.this, "로그인에 실패하였습니다!"
                                        , Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userEmail, userPw,responseListener);
                RequestQueue queue = Volley.newRequestQueue(activity_login.this);
                queue.add(loginRequest);
            }
        });

        // 체크박스 눌렀을 때

    }
}