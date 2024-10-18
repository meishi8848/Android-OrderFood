package com.example.orderfood.activity.boss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.orderfood.Bean.OrderBean;
import com.example.orderfood.R;
import com.example.orderfood.activity.boss.adapter.OrderFinishListAdapter;
import com.example.orderfood.activity.boss.adapter.OrderWaitingListAdapter;
import com.example.orderfood.dao.OrderDao;
import com.example.orderfood.until.Tools;

import java.util.List;

public class ManageBossFinishOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_boss_finish_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView listView=findViewById(R.id.Manage_Boss_My_Finished_Order_listview);
        String account= Tools.getOnAccount(this);

        List<OrderBean> list= OrderDao.queryAllOrdersFinished(account);
        OrderFinishListAdapter orderFinishListAdapter=new OrderFinishListAdapter(this,list);
        if(list==null||list.size()==0){
            listView.setAdapter(null);
        }else{
            listView.setAdapter(orderFinishListAdapter);
        }

        SearchView searchView=findViewById(R.id.Manage_Boss_My_Finished_Order_Search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<OrderBean> list=OrderDao.queryAllOrdersFinished(account);
                List<OrderBean> list1=Tools.filterOrder(list,query);
                OrderFinishListAdapter orderFinishListAdapter=new OrderFinishListAdapter(ManageBossFinishOrderActivity.this,list1);
                if(list==null||list.size()==0){
                    listView.setAdapter(null);
                }else{
                    listView.setAdapter(orderFinishListAdapter);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<OrderBean> list=OrderDao.queryAllOrdersFinished(account);
                List<OrderBean> list1=Tools.filterOrder(list,newText);
                OrderFinishListAdapter orderFinishListAdapter=new OrderFinishListAdapter(ManageBossFinishOrderActivity.this,list1);
                if(list==null||list.size()==0){
                    listView.setAdapter(null);
                }else{
                    listView.setAdapter(orderFinishListAdapter);
                }
                return true;
            }
        });

        Toolbar toolbar=findViewById(R.id.Manage_Boss_My_Finished_Order_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManageBossFinishOrderActivity.this,ManageBossActivity.class);
                intent.putExtra("sta","1");
                startActivity(intent);
            }
        });

    }
}