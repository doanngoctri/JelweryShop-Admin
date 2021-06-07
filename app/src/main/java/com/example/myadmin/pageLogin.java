package com.example.myadmin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myadmin.getData.CallAPI;
import com.example.myadmin.getData.GetDataUser;
import com.example.myadmin.model.ResultLogin;
import com.example.myadmin.model.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class pageLogin extends AppCompatActivity {

    Button btnSignUp,btnSignIn,btnForgotPassWord;
    EditText user,password;
    int idUser=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_login);
        setStatusBarTransparent(this);
        btnSignIn = findViewById(R.id.button_signin);
        btnForgotPassWord = findViewById(R.id.button_forgot_password);
        btnSignUp = findViewById(R.id.button_signup);
        user = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        btnSignUp.setOnClickListener(setEvent);
        btnForgotPassWord.setOnClickListener(setEvent);
        btnSignIn.setOnClickListener(setEvent);
    }

    private void setStatusBarTransparent(AppCompatActivity activity) {
        Window window = activity.getWindow();
        //Make Status bar transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //Make status bar icons color dark
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setNextFocusUpId(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.WHITE);
        }
    }

    private View.OnClickListener setEvent = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId() /*to get clicked view id**/) {
                case R.id.button_signin:
                    String ten= user.getText().toString();
                    String pass=password.getText().toString();
                    // bắt ngoại lệ chưa nhập thông tin
                    if(ten.length() ==0){
                        user.setError("Chưa nhập tên tài khoản");
                        user.requestFocus();
                        return;
                    }
                    if(pass.length() ==0){
                        password.setError("Chưa nhập mật khẩu");
                        password.requestFocus();
                        return;
                    }
                    GetDataUser service = CallAPI.getRetrofitInstance().create(GetDataUser.class);
                    Call<ArrayList<ResultLogin>> call = service.getLogin(ten,pass);
                    call.enqueue(new Callback<ArrayList<ResultLogin>>() {
                        @Override
                        public void onResponse(Call<ArrayList<ResultLogin>> call, Response<ArrayList<ResultLogin>> response) {
                            assert response.body() != null;
                            idUser = response.body().get(0).getResult();
                            //GetDataUser service =  CallAPI.getRetrofitInstance().create(GetDataUser.class);

                            if(response.body().get(0).getResult()!= -1 && response.body().get(0).getResult()!= -2){

                                Call<ArrayList<User>> call_again = service.getInfoUser(idUser);
                                call_again.enqueue(new Callback<ArrayList<User>>() {
                                    @Override
                                    public void onResponse(Call<ArrayList<User>> call_again, Response<ArrayList<User>> response_again) {
                                        assert response_again.body() != null;
                                        //String info="Name:"+ response.body().get(0).getName()+"\nEmail:"+response.body().get(0).getEmail()+"\nPhone:"+response.body().get(0).getPhone()+"\nUserName:"+response.body().get(0).getUserName()+"\nAddress:"+response.body().get(0).getAddress()+"\n";
                                        //tvInfoUser.setText(info);
                                        int role = response_again.body().get(0).getRodeID();
                                        if(role==1){
                                            user.requestFocus();
                                            Toast.makeText(pageLogin.this,"Ứng dụng này chỉ dành cho admin",Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(pageLogin.this,"Đăng nhập thành công!!",Toast.LENGTH_LONG).show();
                                            Intent intent= new Intent(pageLogin.this,MainActivity.class);
                                            intent.putExtra("idUser",idUser);
                                            startActivity(intent);
                                            // setResult(Activity.RESULT_OK,intent);
                                            finish();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<ArrayList<User>> call_again, Throwable t) {

                                    }
                                });

                            }else{
                                user.requestFocus();
                                Toast.makeText(pageLogin.this,"Đăng nhập thất bại!!",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<ResultLogin>> call, Throwable t) {

                        }
                    });
//                    Intent intent = new Intent(pageLogin.this, MainActivity.class);
//                    startActivity(intent);
//                    break;
                case R.id.button_signup:


                    break;
                case R.id.button_forgot_password:

                    break;
                default:
                    break;
            }
        }
    };
}