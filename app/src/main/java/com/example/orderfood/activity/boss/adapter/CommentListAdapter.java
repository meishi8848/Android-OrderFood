package com.example.orderfood.activity.boss.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.orderfood.Bean.CommentBean;
import com.example.orderfood.Bean.CommentBean;
import com.example.orderfood.Bean.UserBean;
import com.example.orderfood.R;
import com.example.orderfood.activity.boss.ManageBossUpdateFoodActivity;
import com.example.orderfood.dao.AdminDao;
import com.example.orderfood.dao.FoodDao;

import java.util.List;

/**
 * 用来显示商家商品的一个适配器
 */
public class CommentListAdapter extends ArrayAdapter<CommentBean> {

    private List<CommentBean> list;

    private Context context;

    public CommentListAdapter(@NonNull Context context, List<CommentBean> list) {
        super(context, R.layout.list_boss_comment_list, list);
        this.context=context;
        this.list=list;
    }

    //显示列表数据的关键方法
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup){
        if(convertView==null){//如果convertView为空，说明这是个新建视图或者没有可重用的视图
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.list_boss_comment_list,viewGroup,false);
        }

        CommentBean temp=list.get(position);

        String userId=temp.getCommentUserId();
        UserBean userBean= AdminDao.getCustomerInformation(userId);
        ImageView imgHead=convertView.findViewById(R.id.comment_img_head);
        imgHead.setImageBitmap(BitmapFactory.decodeFile(userBean.getU_Img()));

        TextView name=convertView.findViewById(R.id.comment_user_name);
        name.setText(userBean.getU_Name());

        String con[]={"非常差","比较差","一般","比较满意","非常满意"};//代表5个满意程度
        TextView satisfy=convertView.findViewById(R.id.comment_satisfy);

        TextView time=convertView.findViewById(R.id.comment_time);
        time.setText(temp.getCommentTime());

        int icoId[]={R.id.comment_review_star1,R.id.comment_review_star2,R.id.comment_review_star3,R.id.comment_review_star4,R.id.comment_review_star5};
        for(int i:icoId){
            View finalConvertView = convertView;
            finalConvertView.findViewById(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int idT=v.getId();
                    int select=1;
                    if(idT==icoId[0]){
                        select=1;
                    }
                    if(idT==icoId[1]){
                        select=2;
                    }
                    if(idT==icoId[2]){
                        select=3;
                    }
                    if(idT==icoId[3]){
                        select=4;
                    }
                    if(idT==icoId[4]){
                        select=5;
                    }
                    satisfy.setText(con[select-1]);
                    for(int i = 0; i< select; i++){
                        ImageView temp=finalConvertView.findViewById(icoId[i]);
                        temp.setImageResource(R.drawable.xx);
                    }
                    for(int i = select; i< 5; i++){
                        ImageView temp=finalConvertView.findViewById(icoId[i]);
                        temp.setImageResource(R.drawable.wxx);
                    }
                }
            });
        }

        TextView sale=convertView.findViewById(R.id.boss_food_list_sale);
        TextView price=convertView.findViewById(R.id.boss_food_list_price);
        TextView des=convertView.findViewById(R.id.boss_food_list_des);

        return convertView;
    }

}
