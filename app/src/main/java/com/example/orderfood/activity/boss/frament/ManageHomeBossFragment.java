package com.example.orderfood.activity.boss.frament;

import android.os.Bundle;
import  androidx.appcompat.widget.SearchView;

import androidx.appcompat.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.R;
import com.example.orderfood.activity.boss.adapter.FoodListAdapter;
import com.example.orderfood.dao.FoodDao;
import com.example.orderfood.until.Tools;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ManageHomeBossFragment extends Fragment {

    View root_view;//根视图

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root_view=inflater.inflate(R.layout.fragment_manage_home_boss,container,false);

        //适配器
        ListView listView=root_view.findViewById(R.id.Manage_Boss_Home_listview);
        List<FoodBean> list=FoodDao.queryAllFoods();
        FoodListAdapter adapter=new FoodListAdapter(getContext(),list);
        if(list==null||list.size()==0){
            listView.setAdapter(null);
        }else{
            listView.setAdapter(adapter);
        }

        String account=Tools.getOnAccount(getContext());

        //搜索功能
        SearchView searchView=root_view.findViewById(R.id.Manage_Boss_Home_FoodSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //根据标题来进行查找商品
                List<FoodBean> list=FoodDao.queryFoodsBytitleAndBossId(account,query);
                FoodListAdapter adapter=new FoodListAdapter(getContext(),list);
                if(list==null||list.size()==0){
                    listView.setAdapter(null);
                }else{
                    listView.setAdapter(adapter);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<FoodBean> list=FoodDao.queryFoodsBytitleAndBossId(account,newText);
                FoodListAdapter adapter=new FoodListAdapter(getContext(),list);
                if(list==null||list.size()==0){
                    listView.setAdapter(null);
                }else{
                    listView.setAdapter(adapter);
                }
                return false;
            }
        });

        return root_view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
    }
}