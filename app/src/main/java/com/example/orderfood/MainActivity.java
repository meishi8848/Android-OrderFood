package com.example.orderfood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.orderfood.activity.boss.ManageBossActivity;
import com.example.orderfood.activity.boss.ManageBossCommentActivity;
import com.example.orderfood.activity.boss.ManageBossFinishOrderActivity;
import com.example.orderfood.activity.boss.ManageBossOrderWaitingActivity;
import com.example.orderfood.activity.boss.RegisterBossActivity;
import com.example.orderfood.activity.user.ManageUserActivity;
import com.example.orderfood.activity.user.ManageUserAddressActivity;
import com.example.orderfood.activity.user.ManageUserCommentActivity;
import com.example.orderfood.activity.user.RegisterUserActivity;
import com.example.orderfood.dao.AdminDao;
import com.example.orderfood.db.DBUntil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DBUntil dbUntil=new DBUntil(this);
        DBUntil.con = dbUntil.getWritableDatabase();

//        Intent intent=new Intent(MainActivity.this, ManageUserAddressActivity.class);
//        startActivity(intent);

        //实现数据账号共享
        SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=sharedPreferences.edit();

        RadioButton sjRadio=findViewById(R.id.login_sj);
        sjRadio.setChecked(true);//让运行时候商家单选按钮默认选择

        Button RegBoss=findViewById(R.id.reg_boss);
        RegBoss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册商家界面
                Intent intent=new Intent(MainActivity.this, RegisterBossActivity.class);
                startActivity(intent);
            }
        });

        Button RegUser=findViewById(R.id.reg_user);
        RegUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到注册用户界面
                Intent intent=new Intent(MainActivity.this, RegisterUserActivity.class);
                startActivity(intent);
            }
        });

        //登录功能
        EditText login_id=findViewById(R.id.login_account);
        EditText login_pwd=findViewById(R.id.login_pwd);
        RadioButton role=findViewById(R.id.login_sj);
        Button login=findViewById(R.id.login_lg);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到用户首页界面
                String id=login_id.getText().toString();
                String pwd=login_pwd.getText().toString();
                if(id.isEmpty()){
                    Toast.makeText(MainActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                }
                else if(pwd.isEmpty()){
                    Toast.makeText(MainActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }
                else{
                    edit.putString("account",id);
                    edit.apply();
                    if(role.isChecked()&&AdminDao.loginBoss(id,pwd)){
                        //确认为商家用户
                        Toast.makeText(MainActivity.this, "登录成功，请稍等", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MainActivity.this, ManageBossActivity.class);
                        startActivity(intent);
                    }
                    else if(!role.isChecked()&&AdminDao.loginUser(id,pwd)){
                        //否则是顾客用户
                        Toast.makeText(MainActivity.this, "登录成功，请稍等", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MainActivity.this, ManageUserActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "账号或密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}