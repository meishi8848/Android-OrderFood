package com.example.orderfood.activity.boss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.orderfood.Bean.CommentBean;
import com.example.orderfood.R;
import com.example.orderfood.activity.boss.adapter.CommentListAdapter;
import com.example.orderfood.dao.CommentDao;
import com.example.orderfood.until.Tools;

import java.util.List;

/**
 * 这个是评论内容
 */
public class ManageBossCommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_boss_comment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar=findViewById(R.id.manage_boss_my_comment_bar);
        setSupportActionBar(toolbar);
        //返回有两种方式
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManageBossCommentActivity.this,ManageBossActivity.class);
                intent.putExtra("sta","1");
                startActivity(intent);
            }
        });

        ListView listView=findViewById(R.id.manage_boss_my_comment_listview);
        String account= Tools.getOnAccount(this);
        List<CommentBean> list= CommentDao.getCommentByBossId(account);
        //适配器
        CommentListAdapter commentListAdapter=new CommentListAdapter(this,list);
        if(list==null||list.size()==0){
            listView.setAdapter(null);
        }else{
            listView.setAdapter(commentListAdapter);
        }
    }
}