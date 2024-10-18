package com.example.orderfood.activity.boss;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.orderfood.R;
import com.example.orderfood.activity.boss.frament.ManageHomeBossFragment;
import com.example.orderfood.activity.boss.frament.ManageMyBossFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ManageBossActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_boss);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FragmentManager fragment_container=getSupportFragmentManager();
        FragmentTransaction transaction=fragment_container.beginTransaction();
        //实现第一次访问跳转的界面
        //后续处理其他界面
        Intent intent=getIntent();
        String sta=intent.getStringExtra("sta");
        if(sta==null||sta.isEmpty())
        {
            transaction.replace(R.id.boss_manage_frame,new ManageHomeBossFragment());
            transaction.commit();
        }else{
            transaction.replace(R.id.boss_manage_frame,new ManageMyBossFragment());
            transaction.commit();
        }

        //查找底部菜单
        BottomNavigationView bottomNavigationView=findViewById(R.id.boss_manage_bottom_menu);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager f=getSupportFragmentManager();
                FragmentTransaction transaction1=f.beginTransaction();
                int id=item.getItemId();
                if(id==R.id.boss_manage_bottom_menu_home){
                    transaction1.replace(R.id.boss_manage_frame,new ManageHomeBossFragment());
                }
                if(id==R.id.boss_manage_bottom_menu_addfood){
                    //跳转到新的界面
                    Intent intent=new Intent(ManageBossActivity.this,ManageBossAddFoodActivity.class);
                    startActivity(intent);
                    return true;
                }
                if(id==R.id.boss_manage_bottom_menu_my){
                    transaction1.replace(R.id.boss_manage_frame,new ManageMyBossFragment());
                }
                transaction1.commit();

                return true;
            }
        });

        //实现搜索功能

    }
}