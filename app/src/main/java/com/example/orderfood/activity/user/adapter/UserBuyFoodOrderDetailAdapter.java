package com.example.orderfood.activity.user.adapter;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.Bean.OrderDetailBean;
import com.example.orderfood.R;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用来显示商家商品的一个适配器
 */
public class UserBuyFoodOrderDetailAdapter extends RecyclerView.Adapter<UserBuyFoodOrderDetailAdapter.OrderViewHolder> {

    private List<OrderDetailBean> list;

    public UserBuyFoodOrderDetailAdapter(List<OrderDetailBean> list) {
        //super(context, R.layout.list_boss_waiting_order_list, list);
        this.list=list;
    }

    @NonNull
    @Override
    public UserBuyFoodOrderDetailAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View convertView=inflater.inflate(R.layout.list_user_buy_food_order_detail_list,parent,false);
        return new OrderViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserBuyFoodOrderDetailAdapter.OrderViewHolder holder, int position) {
        OrderDetailBean temp=list.get(position);

        holder.imageView.setImageBitmap(BitmapFactory.decodeFile(temp.getFood_img()));

        holder.name.setText(temp.getFood_name());

        holder.num.setText(temp.getFood_num()+"份");

        BigDecimal priceZ=new BigDecimal(temp.getFood_price());
        BigDecimal numZ=new BigDecimal(temp.getFood_num());
        BigDecimal total=priceZ.multiply(numZ);
        holder.price.setText("￥"+total.toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //获取总价
    public String getSumPrice(){
        //所有商品数量*价格的总和
        BigDecimal total=new BigDecimal(0);

        for(OrderDetailBean orderDetailBean:list){
            BigDecimal priceZ=new BigDecimal(orderDetailBean.getFood_price());
            BigDecimal numZ=new BigDecimal(orderDetailBean.getFood_num());

            total=total.add(priceZ.multiply(numZ));
        }
        return total.toString();
    }

    static class OrderViewHolder extends  RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name,num,price;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.list_user_buy_food_order_detail_list_img);
            name=itemView.findViewById(R.id.list_user_buy_food_order_detail_list_name);
            num=itemView.findViewById(R.id.list_user_buy_food_order_detail_list_num);
            price=itemView.findViewById(R.id.list_user_buy_food_order_detail_list_price);
        }
    }

}
