package com.example.orderfood.activity.user.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.Bean.OrderBean;
import com.example.orderfood.Bean.OrderDetailBean;
import com.example.orderfood.Bean.UserBean;
import com.example.orderfood.MainActivity;
import com.example.orderfood.R;
import com.example.orderfood.activity.boss.RegisterBossActivity;
import com.example.orderfood.activity.boss.adapter.OrderWaitingListDetailAdapter;
import com.example.orderfood.activity.user.ManageUserCommentActivity;
import com.example.orderfood.activity.user.frament.UserFinishedOrderFragment;
import com.example.orderfood.dao.AdminDao;
import com.example.orderfood.dao.OrderDao;

import java.util.List;

/**
 * 用来显示商家商品的一个适配器
 */
public class UserFinishedOrderListAdapter extends ArrayAdapter<OrderBean> {

    private List<OrderBean> list;

    private Context context;

    public UserFinishedOrderListAdapter(@NonNull Context context, List<OrderBean> list) {
        super(context, R.layout.list_user_finished_order_list, list);
        this.context=context;
        this.list=list;
    }

    //显示列表数据的关键方法
    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup viewGroup){
        if(convertView==null){//如果convertView为空，说明这是个新建视图或者没有可重用的视图
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.list_user_finished_order_list,viewGroup,false);
        }

        OrderBean temp=list.get(position);

        String userId=temp.getCustomer_id();
        UserBean userBean=AdminDao.getCustomerInformation(userId);

        ImageView imageView=convertView.findViewById(R.id.list_user_finished_order_list_img);
        imageView.setImageBitmap(BitmapFactory.decodeFile(userBean.getU_Img()));
        //需要加载用户的头像，但是读取的订单数据表中没有头像数据

        TextView name=convertView.findViewById(R.id.list_user_finished_order_list_name);
        name.setText(userBean.getU_Name());

        TextView time=convertView.findViewById(R.id.list_user_finished_order_list_time);
        time.setText(temp.getOrder_time());

        String addressT[]=temp.getOrder_address().split("-");

        TextView receiver=convertView.findViewById(R.id.list_user_finished_order_list_receiver);
        receiver.setText(addressT[0]);//该地方由于数据表没有收货人真姓名，故只能使用昵称代替

        TextView address=convertView.findViewById(R.id.list_user_finished_order_list_address);
        address.setText(addressT[1]);

        TextView phone=convertView.findViewById(R.id.list_user_finished_order_list_phone);
        phone.setText(addressT[2]);

        RecyclerView foodList=convertView.findViewById(R.id.list_user_finished_order_list_foodList);

        List<OrderDetailBean> detailList=temp.getOrderDetailBeanList();
        //再加载一个listview
        OrderWaitingListDetailAdapter orderWaitingListDetailAdapter=new OrderWaitingListDetailAdapter(detailList);

        foodList.setLayoutManager(new LinearLayoutManager(getContext()));
        if(detailList==null||detailList.size()==0){
            foodList.setAdapter(null);
        }else{
            foodList.setAdapter(orderWaitingListDetailAdapter);
            orderWaitingListDetailAdapter.notifyDataSetChanged();
        }

        TextView total=convertView.findViewById(R.id.list_user_finished_order_list_total);
        total.setText("￥"+orderWaitingListDetailAdapter.getSumPrice());

        Button comment=convertView.findViewById(R.id.list_user_finished_order_list_comment);//订单评论
        TextView state=convertView.findViewById(R.id.list_user_finished_order_list_orderState);
        if(temp.getOrder_state().equals("1")){
            state.setText("订单待处理");
            comment.setVisibility(View.GONE);
        }else if(temp.getOrder_state().equals("2")){
            state.setText("订单已取消");
            comment.setVisibility(View.GONE);
        }else if(temp.getOrder_state().equals("3")){
            state.setText("订单已完成");
            comment.setVisibility(View.VISIBLE);
        }

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //评论功能
                Intent intent=new Intent(getContext(), ManageUserCommentActivity.class);
                intent.putExtra("bossId",temp.getBoss_id());
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

}
