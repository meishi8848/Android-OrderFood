package com.example.orderfood.activity.user.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderfood.Bean.AddressBean;
import com.example.orderfood.R;
import com.example.orderfood.activity.user.ManageUserUpdateAddressActivity;

import java.util.List;

/**
 * 用来显示商家商品的一个适配器
 */
public class MyReceiveAddressListAdapter extends RecyclerView.Adapter<MyReceiveAddressListAdapter.ReceiveAddressViewHolder> {

    private List<AddressBean> list;

    public MyReceiveAddressListAdapter(List<AddressBean> list) {
        //super(context, R.layout.list_boss_waiting_order_list, list);
        this.list=list;
    }

    @NonNull
    @Override
    public MyReceiveAddressListAdapter.ReceiveAddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View convertView=inflater.inflate(R.layout.list_user_my_receive_address_list,parent,false);
        return new ReceiveAddressViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyReceiveAddressListAdapter.ReceiveAddressViewHolder holder, int position) {
        AddressBean temp=list.get(position);

        holder.consignee.setText(temp.getCustomerName());

        holder.address.setText(temp.getCustomerAddress());

        holder.phone.setText(temp.getCustomerPhone());

        //实现编辑功能
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(), ManageUserUpdateAddressActivity.class);
                intent.putExtra("address",temp);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ReceiveAddressViewHolder extends  RecyclerView.ViewHolder{
        TextView consignee,address,phone;
        ImageView edit;
        View itemView;
        public ReceiveAddressViewHolder(@NonNull View itemView) {
            super(itemView);
            consignee=itemView.findViewById(R.id.list_user_my_address_receiver);
            address=itemView.findViewById(R.id.list_user_my_address_address);
            phone=itemView.findViewById(R.id.list_user_my_address_phone);
            edit=itemView.findViewById(R.id.list_user_my_address_edit);
            this.itemView=itemView;
        }
    }

}
