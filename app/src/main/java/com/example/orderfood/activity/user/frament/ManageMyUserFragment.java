package com.example.orderfood.activity.user.frament;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.orderfood.Bean.BossBean;
import com.example.orderfood.Bean.UserBean;
import com.example.orderfood.MainActivity;
import com.example.orderfood.R;
import com.example.orderfood.activity.boss.ManageBossActivity;
import com.example.orderfood.activity.boss.ManageBossCommentActivity;
import com.example.orderfood.activity.boss.ManageBossFinishOrderActivity;
import com.example.orderfood.activity.boss.ManageBossOrderWaitingActivity;
import com.example.orderfood.activity.boss.ManageBossUpdateMesActivity;
import com.example.orderfood.activity.boss.ManageBossUpdatePwdActivity;
import com.example.orderfood.activity.user.ManageUserActivity;
import com.example.orderfood.activity.user.ManageUserAddressActivity;
import com.example.orderfood.activity.user.ManageUserUpdateMesActivity;
import com.example.orderfood.activity.user.ManageUserUpdatePwdActivity;
import com.example.orderfood.dao.AdminDao;
import com.example.orderfood.until.Tools;

/**
 * A fragment representing a list of Items.
 */
public class ManageMyUserFragment extends Fragment {

    View root_view;//根视图

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root_view=inflater.inflate(R.layout.fragment_manage_my_user,container,false);

        //第一步需要实现管理员信息的加载，信息的加载就必须要有一个账号
        String account=Tools.getOnAccount(getContext());
        //同步这个账号的相关信息
        UserBean user = AdminDao.getCustomerInformation(account);

        ImageView head=root_view.findViewById(R.id.user_manage_my_head);
        Bitmap bitmap=BitmapFactory.decodeFile(user.getU_Img());
        head.setImageBitmap(bitmap);

        TextView accountZ=root_view.findViewById(R.id.user_manage_my_account);
        accountZ.setText(account);

        TextView name=root_view.findViewById(R.id.user_manage_my_name);
        name.setText(user.getU_Name());

        TextView des=root_view.findViewById(R.id.user_manage_my_recentAddress);
        des.setText("当前收货地址:"+user.getU_Address());

        //接下来写退出账号
        ManageUserActivity activity=(ManageUserActivity)getActivity();//父界面
        TextView exit=root_view.findViewById(R.id.user_manage_my_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finishAffinity();
            }
        });

        //注销账号
        TextView logout=root_view.findViewById(R.id.user_manage_my_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        TextView checkFinishedOrder=root_view.findViewById(R.id.user_manage_my_finishedOrder);
        checkFinishedOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.showOrder();
            }
        });

        TextView myReceiveAddress=root_view.findViewById(R.id.user_manage_my_receiveAddress);
        myReceiveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ManageUserAddressActivity.class);
                startActivity(intent);
            }
        });

        TextView changeInfor=root_view.findViewById(R.id.user_manage_my_changeinfor);
        changeInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ManageUserUpdateMesActivity.class);
                startActivity(intent);
            }
        });

        //修改密码
        TextView changePwd=root_view.findViewById(R.id.user_manage_my_changepwd);
        changePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ManageUserUpdatePwdActivity.class);
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