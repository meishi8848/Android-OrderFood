package com.example.orderfood.activity.boss;
/**
 * 这个是添加食物的界面
 */
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.orderfood.dao.AdminDao;
import com.example.orderfood.dao.FoodDao;
import com.example.orderfood.until.FileImgUntil;

public class ManageBossAddFoodActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> getContentLauncher;

    Uri uri=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_boss_add_food);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //实现返回功能
        Toolbar toolbar=findViewById(R.id.boss_manage_addFood_back);
        setSupportActionBar(toolbar);
        //返回有两种方式
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                //finish();//都是返回函数
            }
        });

        //实现数据账号共享
        SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
        String boss_id=sharedPreferences.getString("account","root");//如果这个值没有就使用该默认值

        Drawable defaultDrawable= ContextCompat.getDrawable(this,R.drawable.upimg);//获取默认头像

        ImageView imgText=findViewById(R.id.boss_manage_addFood_img);
        EditText nameText=findViewById(R.id.boss_manage_addFood_name);
        EditText priceText=findViewById(R.id.boss_manage_addFood_price);
        EditText desText=findViewById(R.id.boss_manage_addFood_des);
        Button addBtn=findViewById(R.id.boss_manage_addFood_addButton);

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
                    Toast.makeText(ManageBossAddFoodActivity.this, "未选择商品图片", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imgText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(v);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String price = priceText.getText().toString();
                String des = desText.getText().toString();
                Drawable drawable = imgText.getDrawable();//获取当前头像
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();//获取这个图片的二进制文件
                if (name.isEmpty()) {
                    Toast.makeText(ManageBossAddFoodActivity.this, "请输入商品名称", Toast.LENGTH_SHORT).show();
                } else if (price.isEmpty()) {
                    Toast.makeText(ManageBossAddFoodActivity.this, "请输入价格", Toast.LENGTH_SHORT).show();
                } else if (des.isEmpty()) {
                    Toast.makeText(ManageBossAddFoodActivity.this, "请输入商品描述", Toast.LENGTH_SHORT).show();
                } else if (bitmap.sameAs(((BitmapDrawable) defaultDrawable).getBitmap())) {
                    //检查bitmap是否与默认图片相同
                    Toast.makeText(ManageBossAddFoodActivity.this, "未选择商品图片，请点击图片添加商品图片", Toast.LENGTH_SHORT).show();
                } else {
                    String path = FileImgUntil.getImgName();//获取图片的存储路径
                    FileImgUntil.saveImageBitmapToFileImg(uri, ManageBossAddFoodActivity.this, path);//保存图片
                    //准备就绪，写一个添加dao类
                    if(FoodDao.addFood(boss_id,name,des,price,path)){
                        Toast.makeText(ManageBossAddFoodActivity.this, "成功添加商品", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ManageBossAddFoodActivity.this, "添加商品失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //打开文件选择器

    public void openGallery(View v) {
        getContentLauncher.launch("image/*");
    }
}