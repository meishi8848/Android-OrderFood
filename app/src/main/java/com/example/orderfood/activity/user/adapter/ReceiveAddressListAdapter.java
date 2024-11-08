package com.example.orderfood.activity.user.adapter;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.Bean.AddressBean;
import com.example.orderfood.Bean.OrderDetailBean;
import com.example.orderfood.R;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用来显示商家商品的一个适配器
 */
public class ReceiveAddressListAdapter extends RecyclerView.Adapter<ReceiveAddressListAdapter.ReceiveAddressViewHolder> {

    private List<AddressBean> list;

    private View parent;

    public ReceiveAddressListAdapter(View parent, List<AddressBean> list) {
        //super(context, R.layout.list_boss_waiting_order_list, list);
        this.list=list;
        this.parent=parent;
    }

    @NonNull
    @Override
    public ReceiveAddressListAdapter.ReceiveAddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View convertView=inflater.inflate(R.layout.list_user_receive_address_list,parent,false);
        return new ReceiveAddressViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiveAddressListAdapter.ReceiveAddressViewHolder holder, int position) {
        AddressBean temp=list.get(position);

        holder.consignee.setText(temp.getCustomerName());

        holder.address.setText(temp.getCustomerAddress());

        holder.phone.setText(temp.getCustomerPhone());

        TextView receiver=parent.findViewById(R.id.user_buy_food_pay_receiver);
        receiver.setText("");

        TextView address=parent.findViewById(R.id.user_buy_food_pay_address);
        address.setText("");

        TextView phone=parent.findViewById(R.id.user_buy_food_pay_phone);
        phone.setText("");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiver.setText(temp.getCustomerName());
                address.setText(temp.getCustomerAddress());
                phone.setText(temp.getCustomerPhone());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ReceiveAddressViewHolder extends  RecyclerView.ViewHolder{
        TextView consignee,address,phone;
        View itemView;
        public ReceiveAddressViewHolder(@NonNull View itemView) {
            super(itemView);
            consignee=itemView.findViewById(R.id.list_user_address_receiver);
            address=itemView.findViewById(R.id.list_user_address_address);
            phone=itemView.findViewById(R.id.list_user_address_phone);
            this.itemView=itemView;
        }
    }

}
