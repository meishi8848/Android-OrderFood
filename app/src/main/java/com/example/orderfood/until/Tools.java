package com.example.orderfood.until;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orderfood.Bean.OrderBean;
import com.example.orderfood.Bean.OrderDetailBean;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Tools {
    /**
     * 获取当前账号
     * @param context
     * @return
     */
    public static String getOnAccount(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("data", Context.MODE_PRIVATE);
        String boss_id=sharedPreferences.getString("account","root");//如果这个值没有就使用该默认值
        return boss_id;
    }

    /**
     * 获取结果集当中指定行的内容
     * @param rs
     * @param column_name
     * @return
     */
    @SuppressLint("Range")
    public static String getResultString(Cursor rs, String column_name){
       return rs.getString(rs.getColumnIndex(column_name));
    }

    /**
     * 判断用户名和商品名是否有关键词与搜索词一致
     * @param list
     * @param query
     * @return
     */
    public static List<OrderBean> filterOrder(List<OrderBean> list,String query){
        List<OrderBean> list1=new ArrayList<>();
        for (OrderBean orderBean:list){//判断用户名和商品名是否有关键词与搜索词一致
            if(orderBean.getUserName().contains(query)){
                list1.add(orderBean);
            }else{
                List<OrderDetailBean> list2=orderBean.getOrderDetailBeanList();
                for(OrderDetailBean orderDetailBean:list2){
                    if(orderDetailBean.getFood_name().contains(query)){
                        list1.add(orderBean);
                    }
                }
            }
        }
        return list1;
    }

    /**
     * 动态显示星星
     * @param context
     * @param score
     */
    public static void setCommentStar(View context, int score, int conId,int starId[]){

        TextView con=context.findViewById(conId);//显示非常满意
        String conT[]={"非常差","比较差","一般","比较满意","非常满意"};//代表5个满意程度
        con.setText(conT[score-1]);

        for(int i=0;i<starId.length;i++){
            ImageView img=context.findViewById(starId[i]);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击效果
                }
            });
        }

//        int icoId[]={R.id.comment_review_star1,R.id.comment_review_star2,R.id.comment_review_star3,R.id.comment_review_star4,R.id.comment_review_star5};
//        for(int i:icoId){
//            View finalConvertView = convertView;
//            finalConvertView.findViewById(i).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int idT=v.getId();
//                    int select=1;
//                    if(idT==icoId[0]){
//                        select=1;
//                    }
//                    if(idT==icoId[1]){
//                        select=2;
//                    }
//                    if(idT==icoId[2]){
//                        select=3;
//                    }
//                    if(idT==icoId[3]){
//                        select=4;
//                    }
//                    if(idT==icoId[4]){
//                        select=5;
//                    }
//                    satisfy.setText(con[select-1]);
//                    for(int i = 0; i< select; i++){
//                        ImageView temp=finalConvertView.findViewById(icoId[i]);
//                        temp.setImageResource(R.drawable.xx);
//                    }
//                    for(int i = select; i< 5; i++){
//                        ImageView temp=finalConvertView.findViewById(icoId[i]);
//                        temp.setImageResource(R.drawable.wxx);
//                    }
//                }
//            });
//        }
    }

}
