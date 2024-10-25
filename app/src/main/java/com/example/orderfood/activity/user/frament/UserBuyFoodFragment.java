package com.example.orderfood.activity.user.frament;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.R;
import com.example.orderfood.activity.user.adapter.UserBuyFoodListAdapter;
import com.example.orderfood.activity.user.adapter.UserFoodListAdapter;
import com.example.orderfood.dao.FoodDao;
import com.example.orderfood.until.Tools;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class UserBuyFoodFragment extends Fragment {

    View root_view;//根视图

    private Context context;
    private String bossId;
    public UserBuyFoodFragment(String bossId,Context context){
        this.bossId=bossId;
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root_view=inflater.inflate(R.layout.fragment_user_buy_food,container,false);

        //适配器
        ListView listView=root_view.findViewById(R.id.User_Buy_Food_listview);
        List<FoodBean> list=FoodDao.queryAllFoods(bossId);
        UserBuyFoodListAdapter adapter=new UserBuyFoodListAdapter(getContext(),list,context);
        if(list==null||list.size()==0){
            listView.setAdapter(null);
        }else{
            listView.setAdapter(adapter);
        }

        return root_view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
    }
}