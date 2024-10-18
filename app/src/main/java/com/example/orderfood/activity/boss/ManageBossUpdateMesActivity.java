package com.example.orderfood.activity.boss;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.orderfood.Bean.BossBean;
import com.example.orderfood.R;
import com.example.orderfood.dao.AdminDao;
import com.example.orderfood.until.FileImgUntil;
import com.example.orderfood.until.Tools;

/**
 * 实现修改商家信息与密码
 */
public class ManageBossUpdateMesActivity extends AppCompatActivity {

    private EditText Boss_Name;
    private EditText Boss_Des;
    private EditText Boss_Type;
    private ImageView Boss_Head;
    private Button Update_Button;
    private Uri uri;
    private ActivityResultLauncher<String> getContentLauncher;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_boss_update_mes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar=findViewById(R.id.boss_manage_update_mes_toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManageBossUpdateMesActivity.this,ManageBossActivity.class);
                intent.putExtra("sta","1");
                startActivity(intent);
            }
        });

        //加载这个商家的个人信息
        BossBean boss=AdminDao.getBossInformation(Tools.getOnAccount(this));

        Boss_Name=findViewById(R.id.boss_manage_update_name);
        Boss_Name.setText(boss.getB_Name());//加载名称

        Boss_Des=findViewById(R.id.boss_manage_update_des);
        Boss_Des.setText(boss.getB_Des());//加载描述

        Boss_Type=findViewById(R.id.boss_manage_update_type);
        Boss_Type.setText(boss.getB_Type());//加载类型

        Boss_Head=findViewById(R.id.boss_manage_update_head);
        Bitmap bitmap=BitmapFactory.decodeFile(boss.getB_Img());
        Boss_Head.setImageBitmap(bitmap);//加载头像
        Boss_Head.setOnClickListener(new View.OnClickListener() {//加载图片
            @Override
            public void onClick(View v) {
                getContentLauncher.launch("image/*");
            }
        });

        getContentLauncher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result!=null)
                {
                    Boss_Head.setImageURI(result);
                    uri=result;
                }
                else
                {
                    Toast.makeText(ManageBossUpdateMesActivity.this, "未选择店铺图片", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Update_Button=findViewById(R.id.boss_manage_information_update);

        Drawable drawable = Boss_Head.getDrawable();//获取当前头像
        Bitmap defaultDrawable = ((BitmapDrawable) drawable).getBitmap();//获取这个图片的二进制文件 获取默认头像

        //点击修改出现对应提示
        Update_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=Boss_Name.getText().toString().trim();
                String des=Boss_Des.getText().toString().trim();
                String type=Boss_Type.getText().toString().trim();

                if(name.isEmpty()){
                    Boss_Name.setError("店铺名称不能为空");
                    Boss_Name.requestFocus();
                }else if(des.isEmpty()){
                    Boss_Des.setError("店铺描述不能为空");
                    Boss_Des.requestFocus();
                }else if(type.isEmpty()){
                    Boss_Type.setError("店铺类型不能为空");
                    Boss_Type.requestFocus();
                }else{
                    Drawable drawable = Boss_Head.getDrawable();//获取当前头像
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();//获取这个图片的二进制文件

                    String path = FileImgUntil.getImgName();//获取图片的存储路径
                    if (bitmap.sameAs(defaultDrawable)) {
                        //检查bitmap是否与默认图片相同
                        path=boss.getB_Img();
                    }else {
                        FileImgUntil.saveImageBitmapToFileImg(uri, ManageBossUpdateMesActivity.this, path);//保存图片
                    }

                    if(AdminDao.UpdateBossUser(boss.getB_Id(),name,des,type,path)==1){
                        Toast.makeText(ManageBossUpdateMesActivity.this, "更改成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ManageBossUpdateMesActivity.this, "更改成功", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });

    }

}