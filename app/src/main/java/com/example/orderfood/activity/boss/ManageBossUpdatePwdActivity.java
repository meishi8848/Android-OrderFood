package com.example.orderfood.activity.boss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.orderfood.R;
import com.example.orderfood.dao.AdminDao;
import com.example.orderfood.until.Tools;

public class ManageBossUpdatePwdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_boss_update_pwd);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar=findViewById(R.id.boss_manage_update_pwd_toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManageBossUpdatePwdActivity.this,ManageBossActivity.class);
                intent.putExtra("sta","1");
                startActivity(intent);
            }
        });

        String account= Tools.getOnAccount(this);

        EditText pwd=findViewById(R.id.boss_manage_update_pwd);
        EditText confirm=findViewById(R.id.boss_manage_update_pwd_confirm);

        Button button=findViewById(R.id.boss_manage_updatePwd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwdT=pwd.getText().toString().trim();
                String confirmT=confirm.getText().toString().trim();
                if(pwdT.isEmpty()){
                    pwd.setError("请输入新密码！");
                    pwd.requestFocus();
                }else if(confirmT.isEmpty()){
                    confirm.setError("请确认新密码！");
                    confirm.requestFocus();
                }else if(!pwdT.equals(confirmT)){
                    confirm.setError("两次密码不一致，请重新输入");
                    confirm.requestFocus();
                }else{
                    if(AdminDao.UpdateBossUserPwd(account,pwdT)==1){
                        Toast.makeText(ManageBossUpdatePwdActivity.this, "密码更改成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ManageBossUpdatePwdActivity.this, "密码更改失败", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
}