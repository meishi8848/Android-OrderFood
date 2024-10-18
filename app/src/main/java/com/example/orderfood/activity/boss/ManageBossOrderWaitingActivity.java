package com.example.orderfood.activity.boss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.orderfood.Bean.OrderBean;
import com.example.orderfood.Bean.OrderDetailBean;
import com.example.orderfood.R;
import com.example.orderfood.activity.boss.adapter.OrderWaitingListAdapter;
import com.example.orderfood.dao.OrderDao;
import com.example.orderfood.until.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 显示所有的订单，包括未处理订单
 */
public class ManageBossOrderWaitingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_boss_order_waiting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView listView=findViewById(R.id.Manage_Boss_My_Waiting_Order_listview);
        String account=Tools.getOnAccount(this);
        String sta="1";

        List<OrderBean> list=OrderDao.queryAllOrdersBySta(account,sta);
        OrderWaitingListAdapter orderWaitingListAdapter=new OrderWaitingListAdapter(this,list);
        if(list==null||list.size()==0){
            listView.setAdapter(null);
        }else{
            listView.setAdapter(orderWaitingListAdapter);
        }

        SearchView searchView=findViewById(R.id.Manage_Boss_My_Waiting_Order_Search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<OrderBean> list=OrderDao.queryAllOrdersBySta(account,sta);
                List<OrderBean> list1=Tools.filterOrder(list,query);
                OrderWaitingListAdapter orderWaitingListAdapter=new OrderWaitingListAdapter(ManageBossOrderWaitingActivity.this,list1);
                if(list==null||list.size()==0){
                    listView.setAdapter(null);
                }else{
                    listView.setAdapter(orderWaitingListAdapter);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<OrderBean> list=OrderDao.queryAllOrdersBySta(account,sta);
                List<OrderBean> list1=Tools.filterOrder(list,newText);
                OrderWaitingListAdapter orderWaitingListAdapter=new OrderWaitingListAdapter(ManageBossOrderWaitingActivity.this,list1);
                if(list==null||list.size()==0){
                    listView.setAdapter(null);
                }else{
                    listView.setAdapter(orderWaitingListAdapter);
                }
                return true;
            }
        });

        Toolbar toolbar=findViewById(R.id.Manage_Boss_My_Waiting_Order_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManageBossOrderWaitingActivity.this,ManageBossActivity.class);
                intent.putExtra("sta","1");
                startActivity(intent);
            }
        });

    }
}