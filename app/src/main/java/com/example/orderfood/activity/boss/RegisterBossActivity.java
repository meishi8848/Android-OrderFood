package com.example.orderfood.activity.boss;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.BitmapCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.orderfood.R;
import com.example.orderfood.dao.AdminDao;
import com.example.orderfood.until.FileImgUntil;

import java.util.UUID;

public class RegisterBossActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> getContentLauncher;

    Uri uri=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_boss);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //实现返回功能
        Toolbar toolbar=findViewById(R.id.register_boss_toolbar);
        setSupportActionBar(toolbar);
        //返回有两种方式
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                //finish();//都是返回函数
            }
        });

        Drawable defaultDrawable= ContextCompat.getDrawable(this,R.drawable.upimg);//获取默认头像
        ImageView imgText=findViewById(R.id.register_boss_head);
        EditText idText=findViewById(R.id.Reg_Boss_id);
        EditText pwdText=findViewById(R.id.Reg_Boss_pwd);
        EditText nameText=findViewById(R.id.Reg_Boss_name);
        EditText describeText=findViewById(R.id.Reg_Boss_describe);
        EditText typeText=findViewById(R.id.Reg_Boss_type);

        Button reg=findViewById(R.id.Reg_Boss_reg);

        //初始化文件选择器
        getContentLauncher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result!=null)
                {
                    imgText.setImageURI(result);
                    uri=result;
                }
                else
                {
                    Toast.makeText(RegisterBossActivity.this, "请选择头像", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=idText.getText().toString();
                String pwd=pwdText.getText().toString();
                String name=nameText.getText().toString();
                String describe=describeText.getText().toString();
                String type=typeText.getText().toString();
                Drawable drawable=imgText.getDrawable();//获取当前头像
                //实现注册
                //判断头像是否为空
                if(drawable instanceof BitmapDrawable)//判断是不是图片类型
                {
                    Bitmap bitmap=((BitmapDrawable) drawable).getBitmap();//获取这个图片的二进制文件
                    //检查bitmap是否与默认图片相同
                    if(bitmap.sameAs(((BitmapDrawable) defaultDrawable).getBitmap()))
                    {
                        Toast.makeText(RegisterBossActivity.this, "请点击图片添加头像", Toast.LENGTH_SHORT).show();
                    }
                    else if(id.isEmpty())//判断id是否为空
                    {
                        Toast.makeText(RegisterBossActivity.this,"请输入店铺账号",Toast.LENGTH_SHORT).show();
                    }
                    else if(pwd.isEmpty())//判断密码是否为空
                    {
                        Toast.makeText(RegisterBossActivity.this,"请输入店铺密码",Toast.LENGTH_SHORT).show();
                    }
                    else if(name.isEmpty())//判断名称是否为空
                    {
                        Toast.makeText(RegisterBossActivity.this,"请输入店铺名称",Toast.LENGTH_SHORT).show();
                    }
                    else if(describe.isEmpty())//判断描述是否为空
                    {
                        Toast.makeText(RegisterBossActivity.this,"请输入店铺描述",Toast.LENGTH_SHORT).show();
                    }
                    else if(type.isEmpty())//判断店铺类型是否为空
                    {
                        Toast.makeText(RegisterBossActivity.this,"请输入店铺类型",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //可以向数据库进行保存数据
                        //将bitmap保存到另一个文件中，并且把保存路径输入进去
                        String path=FileImgUntil.getImgName();//获取图片的存储路径
                        FileImgUntil.saveImageBitmapToFileImg(uri,RegisterBossActivity.this,path);//保存图片
                        int a=AdminDao.saveBossUser(id,pwd,name,describe,type,path);
                        if(a==1)
                        {
                            Toast.makeText(RegisterBossActivity.this,"商家注册成功",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(RegisterBossActivity.this,"商家注册失败，账号冲突",Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }

        });
        //实现选择图片
        imgText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开文件选择器
                openGallery(v);
            }
        });

    }
    //打开文件选择器

    public void openGallery(View v) {
        getContentLauncher.launch("image/*");
    }

}