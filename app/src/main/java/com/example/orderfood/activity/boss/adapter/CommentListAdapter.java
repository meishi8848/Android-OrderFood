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

import org.w3c.dom.Text;

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
        int score=Integer.valueOf(temp.getCommentScore());
        satisfy.setText(con[score-1]);

        int icoId[]={R.id.comment_review_star1,R.id.comment_review_star2,R.id.comment_review_star3,R.id.comment_review_star4,R.id.comment_review_star5};
        for(int i = 0; i< score; i++){
            ImageView tem=convertView.findViewById(icoId[i]);
            tem.setImageResource(R.drawable.xx);
        }
        for(int i = score; i< 5; i++){
            ImageView tem=convertView.findViewById(icoId[i]);
            tem.setImageResource(R.drawable.wxx);
        }

        TextView content=convertView.findViewById(R.id.comment_content);
        content.setText(temp.getCommentContent());

        TextView time=convertView.findViewById(R.id.comment_time);
        time.setText(temp.getCommentTime());

        ImageView imgFood=convertView.findViewById(R.id.comment_img_food);
        imgFood.setImageBitmap(BitmapFactory.decodeFile(temp.getCommentImg()));

        //下面被注释的代码可以在用户评论模块用到，暂时不删

        return convertView;
    }

}
