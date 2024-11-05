package com.example.orderfood.activity.boss.frament;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.orderfood.Bean.BossBean;
import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.MainActivity;
import com.example.orderfood.R;
import com.example.orderfood.activity.boss.ManageBossActivity;
import com.example.orderfood.activity.boss.ManageBossCommentActivity;
import com.example.orderfood.activity.boss.ManageBossFinishOrderActivity;
import com.example.orderfood.activity.boss.ManageBossOrderWaitingActivity;
import com.example.orderfood.activity.boss.ManageBossUpdateMesActivity;
import com.example.orderfood.activity.boss.ManageBossUpdatePwdActivity;
import com.example.orderfood.activity.boss.adapter.FoodListAdapter;
import com.example.orderfood.dao.AdminDao;
import com.example.orderfood.dao.FoodDao;
import com.example.orderfood.until.Tools;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ManageMyBossFragment extends Fragment {

    View root_view;//根视图

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root_view=inflater.inflate(R.layout.fragment_manage_my_boss,container,false);

        //第一步需要实现管理员信息的加载，信息的加载就必须要有一个账号
        String account=Tools.getOnAccount(getContext());
        //同步这个账号的相关信息
        BossBean boss= AdminDao.getBossInformation(account);

        ImageView head=root_view.findViewById(R.id.boss_manage_my_head);
        Bitmap bitmap=BitmapFactory.decodeFile(boss.getB_Img());
        head.setImageBitmap(bitmap);

        TextView accountZ=root_view.findViewById(R.id.boss_manage_my_account);
        accountZ.setText(account);

        TextView name=root_view.findViewById(R.id.boss_manage_my_name);
        name.setText(boss.getB_Name());

        TextView des=root_view.findViewById(R.id.boss_manage_my_des);
        des.setText("店铺简介:"+boss.getB_Des());

        //接下来写退出账号
        ManageBossActivity activity=(ManageBossActivity)getActivity();//父界面
        TextView exit=root_view.findViewById(R.id.boss_manage_my_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finishAffinity();
            }
        });

        //注销账号
        TextView logout=root_view.findViewById(R.id.boss_manage_my_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //修改密码
        TextView changepwd=root_view.findViewById(R.id.boss_manage_my_changepwd);
        changepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ManageBossUpdatePwdActivity.class);
                startActivity(intent);
            }
        });

        TextView changeinfor=root_view.findViewById(R.id.boss_manage_my_changeinfor);
        changeinfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ManageBossUpdateMesActivity.class);
                startActivity(intent);
            }
        });

        TextView orderManage=root_view.findViewById(R.id.boss_manage_my_orderManage);
        orderManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ManageBossOrderWaitingActivity.class);
                startActivity(intent);
            }
        });

        TextView commentManage=root_view.findViewById(R.id.boss_manage_my_commentManage);
        commentManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ManageBossCommentActivity.class);
                startActivity(intent);
            }
        });

        TextView orderFinish=root_view.findViewById(R.id.boss_manage_my_finishedOrderManage);
        orderFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ManageBossFinishOrderActivity.class);
                startActivity(intent);
            }
        });

        return root_view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
    }
}