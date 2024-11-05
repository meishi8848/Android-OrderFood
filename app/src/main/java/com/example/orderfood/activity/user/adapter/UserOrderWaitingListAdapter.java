package com.example.orderfood.activity.user.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.example.orderfood.R;
import com.example.orderfood.activity.boss.adapter.OrderWaitingListDetailAdapter;
import com.example.orderfood.dao.AdminDao;
import com.example.orderfood.dao.OrderDao;

import java.util.List;

/**
 * 用来显示商家商品的一个适配器
 */
public class UserOrderWaitingListAdapter extends ArrayAdapter<OrderBean> {

    private List<OrderBean> list;

    private Context context;

    public UserOrderWaitingListAdapter(@NonNull Context context, List<OrderBean> list) {
        super(context, R.layout.list_user_waiting_order_list, list);
        this.context=context;
        this.list=list;
    }

    //显示列表数据的关键方法
    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup viewGroup){
        if(convertView==null){//如果convertView为空，说明这是个新建视图或者没有可重用的视图
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.list_user_waiting_order_list,viewGroup,false);
        }

        OrderBean temp=list.get(position);

        String userId=temp.getCustomer_id();
        UserBean userBean=AdminDao.getCustomerInformation(userId);

        ImageView imageView=convertView.findViewById(R.id.list_user_waiting_order_list_img);
        imageView.setImageBitmap(BitmapFactory.decodeFile(userBean.getU_Img()));
        //需要加载用户的头像，但是读取的订单数据表中没有头像数据

        TextView name=convertView.findViewById(R.id.list_user_waiting_order_list_name);
        name.setText(userBean.getU_Name());

        TextView time=convertView.findViewById(R.id.list_user_waiting_order_list_time);
        time.setText(temp.getOrder_time());

        String addressT[]=temp.getOrder_address().split("-");

        TextView receiver=convertView.findViewById(R.id.list_user_waiting_order_list_receiver);
        receiver.setText(addressT[0]);//该地方由于数据表没有收货人真姓名，故只能使用昵称代替

        TextView address=convertView.findViewById(R.id.list_user_waiting_order_list_address);
        address.setText(addressT[1]);

        TextView phone=convertView.findViewById(R.id.list_user_waiting_order_list_phone);
        phone.setText(addressT[2]);

        RecyclerView foodList=convertView.findViewById(R.id.list_user_waiting_order_list_foodList);

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

        TextView total=convertView.findViewById(R.id.list_user_waiting_order_list_total);
        total.setText("￥"+orderWaitingListDetailAdapter.getSumPrice());

        Button cancel=convertView.findViewById(R.id.list_user_waiting_order_list_cancelOrder);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将状态改为2
                if(OrderDao.updateOrderStatus(temp.getOrder_id(),"2")){
                    list.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(getContext(), "取消订单成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "取消订单失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }

}
