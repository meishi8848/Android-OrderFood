package com.example.orderfood.activity.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.Bean.AddressBean;
import com.example.orderfood.R;
import com.example.orderfood.activity.boss.ManageBossActivity;
import com.example.orderfood.activity.boss.ManageBossCommentActivity;
import com.example.orderfood.activity.user.adapter.MyReceiveAddressListAdapter;
import com.example.orderfood.dao.AddressDao;
import com.example.orderfood.until.Tools;

import java.util.List;

public class ManageUserAddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_user_address);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //实现返回功能
        Toolbar toolbar=findViewById(R.id.user_address_toolbar);
        setSupportActionBar(toolbar);
        //返回有两种方式
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManageUserAddressActivity.this, ManageUserActivity.class);
                intent.putExtra("sta","1");
                startActivity(intent);
            }
        });

        RecyclerView listView=findViewById(R.id.user_address_listview);
        listView.setLayoutManager(new LinearLayoutManager(this));

        String account= Tools.getOnAccount(this);
        List<AddressBean> list = AddressDao.getAllAddressByUserId(account);
        MyReceiveAddressListAdapter myReceiveAddressListAdapter=new MyReceiveAddressListAdapter(list);
        if(list==null||list.size()==0){
            listView.setAdapter(null);
        }else{
            listView.setAdapter(myReceiveAddressListAdapter);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_address_add_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int a=item.getItemId();
        if(a==R.id.user_address_menu_add){
            Intent intent=new Intent(this, ManageUserAddAddressActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}