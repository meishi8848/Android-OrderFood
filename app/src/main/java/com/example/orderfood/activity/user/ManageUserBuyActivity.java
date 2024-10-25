package com.example.orderfood.activity.user;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.orderfood.Bean.BossBean;
import com.example.orderfood.R;
import com.example.orderfood.activity.user.dialog.UserPayOrderDialog;
import com.example.orderfood.activity.user.frament.UserBuyFoodFragment;
import com.example.orderfood.activity.user.frament.UserReadBossCommentFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户购买界面
 */
public class ManageUserBuyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_user_buy);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar=findViewById(R.id.user_buy_toolbar);
        setSupportActionBar(toolbar);
        //返回有两种方式
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManageUserBuyActivity.this, ManageUserActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        BossBean boss=(BossBean) intent.getSerializableExtra("bossId");

        ImageView imgHead=findViewById(R.id.user_buy_bossHead);
        imgHead.setImageBitmap(BitmapFactory.decodeFile(boss.getB_Img()));

        TextView Name=findViewById(R.id.user_buy_bossName);
        Name.setText(boss.getB_Name());

        TextView Des=findViewById(R.id.user_buy_bossDes);
        Des.setText("简介:"+boss.getB_Des());

        //实现显示数量
        TextView sumPrice=this.findViewById(R.id.user_buy_sumPrice);
        sumPrice.setText("0");

        //点击结算，转到结算页面
        TextView foodDetail=this.findViewById(R.id.user_buy_foodDetail);
        Button pay=this.findViewById(R.id.user_buy_pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodDetailT=foodDetail.getText().toString();
                JSONArray jsonArray=JSONArray.parseArray(foodDetailT);
                List<JSONObject> FoodList=new ArrayList<>();//存放购买商品的列表
                for(Object o:jsonArray){
                    JSONObject temp= JSONObject.parseObject(o.toString());
                    if(!temp.get("num").equals("0")){
                        FoodList.add(temp);
                    }
                }
                if(foodDetailT.isEmpty()){
                    Toast.makeText(ManageUserBuyActivity.this, "未选择商品，无法结算", Toast.LENGTH_SHORT).show();
                }else if(FoodList.size()==0){
                    Toast.makeText(ManageUserBuyActivity.this, "未选择商品，无法结算", Toast.LENGTH_SHORT).show();
                }else{
                    UserPayOrderDialog userPayOrderDialog=new UserPayOrderDialog(ManageUserBuyActivity.this,boss.getB_Id());
                }
            }
        });

        TabLayout Tap=findViewById(R.id.user_buy_tap);
        ViewPager2 viewPager2=findViewById(R.id.user_buy_pager);
        viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                if(position==0){
                    return new UserBuyFoodFragment(boss.getB_Id(),ManageUserBuyActivity.this);
                }else{
                    return new UserReadBossCommentFragment(boss.getB_Id());
                }

            }

            @Override
            public int getItemCount() {
                return 2;
            }
        });

        new TabLayoutMediator(Tap,viewPager2,((tab, position) -> {

            if(position==0){
                tab.setText("点餐");
            }else{
                tab.setText("评论");
            }

        })).attach();

    }
}