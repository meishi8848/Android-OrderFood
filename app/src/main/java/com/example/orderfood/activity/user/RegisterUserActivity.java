package com.example.orderfood.activity.user;
/**
 * 注册界面
 */

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.orderfood.R;
import com.example.orderfood.activity.boss.RegisterBossActivity;
import com.example.orderfood.dao.AdminDao;
import com.example.orderfood.until.FileImgUntil;

public class RegisterUserActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> getContentLauncher;

    Uri uri=null;
    private String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar=findViewById(R.id.register_user_toolbar);
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
        ImageView imgText=findViewById(R.id.register_user_head);
        EditText idText=findViewById(R.id.Reg_User_id);
        EditText pwdText=findViewById(R.id.Reg_User_pwd);
        EditText nameText=findViewById(R.id.Reg_User_name);

        sex="女";
        RadioButton human=findViewById(R.id.Reg_User_man);
        human.setChecked(true);
        if(human.isChecked()){
            sex="男";
        }

        EditText addressText=findViewById(R.id.Reg_User_address);
        EditText phoneText=findViewById(R.id.Reg_User_phone);

        Button reg=findViewById(R.id.Reg_User_reg);

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
                    Toast.makeText(RegisterUserActivity.this, "请选择头像", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=idText.getText().toString();
                String pwd=pwdText.getText().toString();
                String name=nameText.getText().toString();
                String address=addressText.getText().toString();
                String phone=phoneText.getText().toString();
                Drawable drawable=imgText.getDrawable();//获取当前头像
                //实现注册
                //判断头像是否为空
                if(drawable instanceof BitmapDrawable)//判断是不是图片类型
                {
                    Bitmap bitmap=((BitmapDrawable) drawable).getBitmap();//获取这个图片的二进制文件
                    //检查bitmap是否与默认图片相同
                    if(bitmap.sameAs(((BitmapDrawable) defaultDrawable).getBitmap()))
                    {
                        Toast.makeText(RegisterUserActivity.this, "请点击图片添加头像", Toast.LENGTH_SHORT).show();
                    }
                    else if(id.isEmpty())//判断id是否为空
                    {
                        Toast.makeText(RegisterUserActivity.this,"请输入用户账号",Toast.LENGTH_SHORT).show();
                    }
                    else if(pwd.isEmpty())//判断密码是否为空
                    {
                        Toast.makeText(RegisterUserActivity.this,"请输入用户密码",Toast.LENGTH_SHORT).show();
                    }
                    else if(name.isEmpty())//判断名称是否为空
                    {
                        Toast.makeText(RegisterUserActivity.this,"请输入用户昵称",Toast.LENGTH_SHORT).show();
                    }
                    else if(address.isEmpty())//判断描述是否为空
                    {
                        Toast.makeText(RegisterUserActivity.this,"请输入用户住址",Toast.LENGTH_SHORT).show();
                    }
                    else if(phone.isEmpty())//判断店铺类型是否为空
                    {
                        Toast.makeText(RegisterUserActivity.this,"请输入联系方式",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //可以向数据库进行保存数据
                        //将bitmap保存到另一个文件中，并且把保存路径输入进去
                        String path= FileImgUntil.getImgName();//获取图片的存储路径
                        FileImgUntil.saveImageBitmapToFileImg(uri,RegisterUserActivity.this,path);//保存图片
                        int a=AdminDao.saveCustomerUser(id,pwd,name,sex,address,phone,path);
                        if(a==1)
                        {
                            Toast.makeText(RegisterUserActivity.this,"用户注册成功",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(RegisterUserActivity.this,"用户注册失败，账号冲突",Toast.LENGTH_SHORT).show();
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
    public void openGallery(View v) {
        getContentLauncher.launch("image/*");
    }
}