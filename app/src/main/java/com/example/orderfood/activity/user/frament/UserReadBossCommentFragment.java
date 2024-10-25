package com.example.orderfood.activity.user.frament;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.orderfood.Bean.CommentBean;
import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.R;
import com.example.orderfood.activity.boss.adapter.CommentListAdapter;
import com.example.orderfood.activity.user.adapter.UserFoodListAdapter;
import com.example.orderfood.dao.CommentDao;
import com.example.orderfood.dao.FoodDao;
import com.example.orderfood.until.Tools;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class UserReadBossCommentFragment extends Fragment {

    View root_view;//根视图

    private String bossId;

    public UserReadBossCommentFragment(String bossId){
        this.bossId=bossId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root_view=inflater.inflate(R.layout.fragment_user_comment,container,false);

        List<CommentBean> list=CommentDao.getCommentByBossId(bossId);

        ListView listView=root_view.findViewById(R.id.User_Comment_listview);

        //适配器
        CommentListAdapter commentListAdapter=new CommentListAdapter(getContext(),list);
        if(list==null||list.size()==0){
            listView.setAdapter(null);
        }else{
            listView.setAdapter(commentListAdapter);
        }

        return root_view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
    }
}