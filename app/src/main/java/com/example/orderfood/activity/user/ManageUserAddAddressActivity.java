package com.example.orderfood.activity.user;

import static com.example.orderfood.R.*;

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

import com.example.orderfood.Bean.AddressBean;
import com.example.orderfood.R;
import com.example.orderfood.dao.AddressDao;
import com.example.orderfood.until.Tools;

public class ManageUserAddAddressActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_user_add_address);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //加载上个界面传过来的数据
        Toolbar toolbar=findViewById(R.id.user_manage_addMyAddress_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManageUserAddAddressActivity.this,ManageUserAddressActivity.class);
                startActivity(intent);
            }
        });

        String id= Tools.getOnAccount(this);
        EditText name=findViewById(R.id.user_manage_addMyAddress_name);
        EditText address=findViewById(R.id.user_manage_addMyAddress_address);
        EditText phone=findViewById(R.id.user_manage_addMyAddress_phone);
        Button confirm=findViewById(R.id.user_manage_addMyAddress_confirmButton);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameT=name.getText().toString();
                String addressT=address.getText().toString();
                String phoneT=phone.getText().toString();

                if(nameT.isEmpty()){
                    Toast.makeText(ManageUserAddAddressActivity.this, "请输入收货人姓名", Toast.LENGTH_SHORT).show();
                }else if(addressT.isEmpty()){
                    Toast.makeText(ManageUserAddAddressActivity.this, "请输入收货地址", Toast.LENGTH_SHORT).show();
                }else if(phoneT.isEmpty()){
                    Toast.makeText(ManageUserAddAddressActivity.this, "请输入收货人联系方式", Toast.LENGTH_SHORT).show();
                }else{
                    if(AddressDao.addAddress(id,nameT,addressT,phoneT)){
                        Toast.makeText(ManageUserAddAddressActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ManageUserAddAddressActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        
    }
}