package com.example.orderfood.activity.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.orderfood.Bean.AddressBean;
import com.example.orderfood.R;
import com.example.orderfood.activity.boss.ManageBossActivity;
import com.example.orderfood.activity.boss.ManageBossUpdateFoodActivity;
import com.example.orderfood.dao.AddressDao;
import com.example.orderfood.dao.FoodDao;

import java.io.Serializable;

public class ManageUserUpdateAddressActivity extends AppCompatActivity {

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_user_update_address);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //加载上个界面传过来的数据
        Toolbar toolbar=findViewById(R.id.user_manage_myAddress_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManageUserUpdateAddressActivity.this,ManageUserAddressActivity.class);
                startActivity(intent);
            }
        });

        Intent intent=getIntent();
        AddressBean temp=(AddressBean) intent.getSerializableExtra("address");
        id=temp.getAddressId();

        EditText name=findViewById(R.id.user_manage_myAddress_name);
        name.setText(temp.getCustomerName());

        EditText address=findViewById(R.id.user_manage_myAddress_address);
        address.setText(temp.getCustomerAddress());

        EditText phone=findViewById(R.id.user_manage_myAddress_phone);
        phone.setText(temp.getCustomerPhone());

        Button confirm=findViewById(R.id.user_manage_myAddress_confirmButton);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameT=name.getText().toString();
                String addressT=address.getText().toString();
                String phoneT=phone.getText().toString();

                if(nameT.isEmpty()){
                    Toast.makeText(ManageUserUpdateAddressActivity.this, "请输入收货人姓名", Toast.LENGTH_SHORT).show();
                }else if(addressT.isEmpty()){
                    Toast.makeText(ManageUserUpdateAddressActivity.this, "请输入收货地址", Toast.LENGTH_SHORT).show();
                }else if(phoneT.isEmpty()){
                    Toast.makeText(ManageUserUpdateAddressActivity.this, "请输入收货人联系方式", Toast.LENGTH_SHORT).show();
                }else{
                    if(AddressDao.updateAddress(id,nameT,addressT,phoneT)){
                        Toast.makeText(ManageUserUpdateAddressActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ManageUserUpdateAddressActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_address_del_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int a=item.getItemId();
        if(a==R.id.user_address_menu_del){
            //删除，是否要删除，删除成功会返回主页面
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("删除地址");
            builder.setMessage("确认删除该地址？");
            builder.setCancelable(false);
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(AddressDao.deleteAddressById(id)){
                        Toast.makeText(ManageUserUpdateAddressActivity.this, "成功删除", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ManageUserUpdateAddressActivity.this, ManageUserAddressActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(ManageUserUpdateAddressActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}