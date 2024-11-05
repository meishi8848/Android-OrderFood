package com.example.orderfood.activity.user.listen;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orderfood.R;
import com.example.orderfood.activity.user.ManageUserCommentActivity;

//待写
public class StartListen implements View.OnClickListener{

    private ManageUserCommentActivity manageUserCommentActivity;
    public StartListen(ManageUserCommentActivity manageUserCommentActivity) {
        this.manageUserCommentActivity=manageUserCommentActivity;
    }

    @Override
    public void onClick(View v){
        int idT=v.getId();
        ImageView img= (ImageView) v;
        img.setImageResource(R.drawable.wxx);
        //点击一个星星，将左边的星星都设置为亮

        String con[]={"非常差","比较差","一般","比较满意","非常满意"};//代表5个满意程度
        int icoId[]={R.id.user_comment_review_star1,R.id.user_comment_review_star2,R.id.user_comment_review_star3,R.id.user_comment_review_star4,R.id.user_comment_review_star5};

        boolean flag=true;//星星标志位
        for(int i = 0; i< icoId.length; i++){

            ImageView imgZ=manageUserCommentActivity.findViewById(icoId[i]);
            TextView satisfy=manageUserCommentActivity.findViewById(R.id.user_comment_satisfy);
            if(flag){
                imgZ.setImageResource(R.drawable.xx);
            }else{
                imgZ.setImageResource(R.drawable.wxx);
            }

            if(idT==icoId[i]){
                flag=false;
                satisfy.setText(con[i]);
            }

        }

    }

}
