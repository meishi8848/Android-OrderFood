package com.example.orderfood.activity.user.frament;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.Bean.OrderBean;
import com.example.orderfood.R;
import com.example.orderfood.activity.boss.ManageBossActivity;
import com.example.orderfood.activity.boss.ManageBossOrderWaitingActivity;
import com.example.orderfood.activity.boss.adapter.OrderWaitingListAdapter;
import com.example.orderfood.activity.user.adapter.UserFoodListAdapter;
import com.example.orderfood.activity.user.adapter.UserOrderWaitingListAdapter;
import com.example.orderfood.dao.FoodDao;
import com.example.orderfood.dao.OrderDao;
import com.example.orderfood.until.Tools;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class UserWaitingOrderFragment extends Fragment {

    View root_view;//根视图

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root_view=inflater.inflate(R.layout.fragment_manage_user_order_waiting,container,false);

        //适配器
        ListView listView=root_view.findViewById(R.id.User_Waiting_Order_listview);
        String account=Tools.getOnAccount(root_view.getContext());
        String sta="1";

        List<OrderBean> list= OrderDao.queryAllOrdersByStaAndUserId(account,sta);
        UserOrderWaitingListAdapter userOrderWaitingListAdapter=new UserOrderWaitingListAdapter(root_view.getContext(),list);
        if(list==null||list.size()==0){
            listView.setAdapter(null);
        }else{
            listView.setAdapter(userOrderWaitingListAdapter);
        }

        SearchView searchView=root_view.findViewById(R.id.User_Waiting_Order_Search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<OrderBean> list=OrderDao.queryAllOrdersByStaAndUserId(account,sta);
                List<OrderBean> list1=Tools.filterOrder(list,query);
                UserOrderWaitingListAdapter orderWaitingListAdapter=new UserOrderWaitingListAdapter(root_view.getContext(),list1);
                if(list==null||list.size()==0){
                    listView.setAdapter(null);
                }else{
                    listView.setAdapter(orderWaitingListAdapter);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<OrderBean> list=OrderDao.queryAllOrdersByStaAndUserId(account,sta);
                List<OrderBean> list1=Tools.filterOrder(list,newText);
                UserOrderWaitingListAdapter orderWaitingListAdapter=new UserOrderWaitingListAdapter(root_view.getContext(),list1);
                if(list==null||list.size()==0){
                    listView.setAdapter(null);
                }else{
                    listView.setAdapter(orderWaitingListAdapter);
                }
                return true;
            }
        });

        return root_view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
    }
}