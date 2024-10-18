package com.example.orderfood.dao;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderfood.Bean.CommentBean;
import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.db.DBUntil;
import com.example.orderfood.until.Tools;

import java.util.ArrayList;
import java.util.List;

public class CommentDao {

    public static SQLiteDatabase db= DBUntil.con;

    public static List<CommentBean> getCommentByBossId(String id){
        List<CommentBean> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * From d_comment where s_comment_boss_id=?",new String[]{id});
        if(cursor.moveToNext()) {
            CommentBean commentBean = new CommentBean();
            String commentId=Tools.getResultString(cursor,"s_comment_id");
            commentBean.setCommentId(commentId);

            String userId=Tools.getResultString(cursor,"s_comment_customer_id");
            commentBean.setCommentUserId(userId);

            String bossId=Tools.getResultString(cursor,"s_comment_boss_id");
            commentBean.setCommentBossId(bossId);

            String content=Tools.getResultString(cursor,"s_comment_content");
            commentBean.setCommentContent(content);

            String time=Tools.getResultString(cursor,"s_comment_time");
            commentBean.setCommentTime(time);

            String score=Tools.getResultString(cursor,"s_comment_score");
            commentBean.setCommentScore(score);

            String img=Tools.getResultString(cursor,"s_comment_img");
            commentBean.setCommentImg(img);

            list.add(commentBean);
        }

        return list;
    }
}
