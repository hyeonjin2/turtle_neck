package com.example.turtle_neck;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class activity_join extends AppCompatActivity {
    private Button btn_check;
    private Dialog dialog;
    private EditText edit_nickname, edit_email, edit_e_check,
            edit_pw, edit_pw_check;
    private TextView edit_pw_ch_show, nick_double, pw_condition;

    private boolean b_name, b_name_ch, b_email, b_email_ch, b_pw, b_pw_ch, validate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        b_name = false;
        b_name_ch = false;
        b_email = false;
        b_email_ch = false;
        b_pw = false;
        b_pw_ch = false;

        // dialog layout item 불러오기
        edit_nickname = findViewById(R.id.nick_name);
        nick_double = findViewById(R.id.double_check);

        edit_email = findViewById(R.id.email);
        edit_e_check = findViewById(R.id.email_check);
        btn_check = findViewById(R.id.btn_check);

        edit_pw = findViewById(R.id.password);
        pw_condition = findViewById(R.id.pw_condition);
        edit_pw_check = findViewById(R.id.pw_check);
        edit_pw_ch_show = findViewById(R.id.text_password_check);


        // 비밀번호 입력창 event handler
        edit_pw.addTextChangedListener(new TextWatcher() {

            // 비밀번호를 입력하기 전
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            // 비밀번호 입력 중 -> editText에 변화가 왔을 때
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String password = edit_pw.getText().toString();
                if (password.length() >= 8){
                    pw_condition.setText("8자 이상입니다!");
                    b_pw = true;
                }
                else {
                    pw_condition.setText("8자 이상이어야 합니다.");
                    b_pw = false;
                }
            }
            // 입력이 끝났을 때
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 비밀번호 확인창 event handler
        edit_pw_check.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            // 비밀번호 확인창 입력 중
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String password = edit_pw.getText().toString();
                String pw_check = edit_pw_check.getText().toString();

                if(password.equals(pw_check)){
                    edit_pw_ch_show.setText("일치합니다!");
                    edit_pw_ch_show.setTextColor(Color.rgb(36,203,173));
                    b_pw_ch = true;
                }else{
                    edit_pw_ch_show.setText("일치하지 않습니다!");
                    edit_pw_ch_show.setTextColor(Color.RED);
                    b_pw_ch = false;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void nickCheck() {
        // 사용자가 입력한 닉네임 가져오기
        String nick_name = edit_nickname.getText().toString();
        if (validate) {
            return; //검증 완료
        }

        if (nick_name.length() <= 0) {
            Toast.makeText(activity_join.this, "닉네임을 입력하세요!", Toast.LENGTH_SHORT).show();
            return;
        }

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        nick_double.setText("사용할 수 있는 닉네임 입니다!");
                        nick_double.setTextColor(Color.rgb(36, 203, 173)); //#24CBAD
                        b_name_ch = true;
                        edit_nickname.setEnabled(false); //아이디값 고정
                        validate = true; //검증 완료
                    } else {
                        nick_double.setText("이미 존재하는 닉네임 입니다!");
                        nick_double.setTextColor(Color.RED);
                        b_name_ch = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ValidateRequest validateRequest = new ValidateRequest(nick_name, responseListener);
        RequestQueue queue = Volley.newRequestQueue(activity_join.this);
        queue.add(validateRequest);
    }

    public void emailCheck(){

        // 이메일을 입력 후 버튼을 눌렀을 때 확인버튼으로 바뀜
        // 이메일을 입력하지 않은 경우 메세지 출력
        String email = edit_email.getText().toString();
        if(email.length() > 0) {
            b_email = true;
            btn_check.setText("확인"); // 버튼 클릭시 확인으로 메세지 변함
            edit_e_check.setHint("인증번호 입력"); // + 뒤에 시간 서버에서 받아와 카운트다운
            /*
             * 카운트다운 구현
             *
             *
             * */

            edit_e_check.setHintTextColor(Color.rgb(36,203,173));

            // 버튼 메세지가 확인으로 변한 후 메세지 입력 안하고 확인 버튼 눌렀을 때의 메세지 출력
            String check = edit_e_check.getText().toString();
            if(check.length() > 0) {
                // 인증번호가 서버에서 보낸 번호와 맞는지 확인
                String check_pw = "1234";
                if (Objects.equals(check, check_pw)) {
                    Toast.makeText(activity_join.this, "일치합니다!"
                            , Toast.LENGTH_SHORT).show();
                    b_email_ch = true;
                }else {
                    Toast.makeText(activity_join.this, "일치하지 않습니다. 다시 입력해주세요!"
                            , Toast.LENGTH_SHORT).show();
                    b_email_ch = false;
                }
            }
            else
                Toast.makeText(activity_join.this, "인증번호를 입력해주세요!"
                        , Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(activity_join.this, "이메일을 입력해주세요!"
                    , Toast.LENGTH_SHORT).show();
    }

    public void signIn(){

        // 항목들 값 부족한 부분 있으면 경고메세지 보내기
        if(b_name&&b_name_ch&&b_email&&b_email_ch&&b_pw&&b_pw_ch) {

            // 서버에 정보 보내기
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if(success){    // 회원가입에 성공한 경우
                            Toast.makeText(activity_join.this, "회원가입이 완료되었습니다!"
                                    , Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(activity_join.this, activity_login.class);
                            startActivity(intent);
                        }else{  // 회원가입에 실패한 경우
                            Toast.makeText(activity_join.this, "회원가입에 실패하였습니다!"
                                    , Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            };
            // 항목 값 가져오기
            String userEmail = edit_email.getText().toString();
            String userPw = edit_pw.getText().toString();
            String userName = edit_nickname.getText().toString();

            // Volley를 이용해서 서버로 요청을 함
            RegisterRequest registerRequest =
                    new RegisterRequest(userEmail, userPw, userName,responseListener);
            RequestQueue queue = Volley.newRequestQueue(activity_join.this);
            queue.add(registerRequest);

            // 창 닫기
            //dismissHandler();
            //dialog.dismiss();
        }else{
            if(!b_name){
                Toast.makeText(activity_join.this, "닉네임을 입력해주세요!"
                        , Toast.LENGTH_SHORT).show();
            }
            else if(!b_name_ch){
                Toast.makeText(activity_join.this, "닉네임 중복확인을 해주세요!"
                        , Toast.LENGTH_SHORT).show();
            }
            else if(!b_email){
                Toast.makeText(activity_join.this, "이메일을 입력해주세요!"
                        , Toast.LENGTH_SHORT).show();
            }
            else if(!b_email_ch){
                Toast.makeText(activity_join.this, "인증번호를 입력해주세요!"
                        , Toast.LENGTH_SHORT).show();
            }
            else if(!b_pw){
                Toast.makeText(activity_join.this, "비밀번호를 입력해주세요!"
                        , Toast.LENGTH_SHORT).show();
            }
            else if(!b_pw_ch){
                Toast.makeText(activity_join.this, "비밀번호를 다시 한번 입력해주세요!"
                        , Toast.LENGTH_SHORT).show();
            }
        }
    }
/*
    public void dismissHandler(){
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                edit_nickname.getText().clear();
                nick_double.setText(null);
                edit_email.getText().clear();
                edit_e_check.getText().clear();
                edit_e_check.setHint(null);
                btn_check.setText("인증요청");
                edit_pw.getText().clear();
                edit_pw_check.getText().clear();
                pw_condition.setText("8자 이상이어야 합니다.");
                edit_pw_ch_show.setText(null);
            }
        });
    }*/

    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.text_double_check:
                nickCheck();
                break;
            case R.id.btn_check:
                emailCheck();
                break;
            case R.id.btn_close:
                Intent intent = new Intent(activity_join.this,activity_login.class);
                startActivity(intent);
                break;
            case R.id.btn_sign_in:
                signIn();
                break;
        }
    }
}
