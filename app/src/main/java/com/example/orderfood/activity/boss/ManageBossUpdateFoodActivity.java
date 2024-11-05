package com.example.orderfood.activity.boss;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

import com.example.orderfood.Bean.FoodBean;
import com.example.orderfood.R;
import com.example.orderfood.dao.AdminDao;
import com.example.orderfood.dao.FoodDao;
import com.example.orderfood.until.FileImgUntil;

public class ManageBossUpdateFoodActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> getContentLauncher;

    Uri uri=null;

    String food_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_boss_update_food);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //实现返回功能
        Toolbar toolbar=findViewById(R.id.boss_manage_updateFood_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManageBossUpdateFoodActivity.this,ManageBossActivity.class);
                startActivity(intent);
            }
        });

        Intent intent=getIntent();
        FoodBean food = (FoodBean) intent.getSerializableExtra("food");
        food_id=food.getFoodId();

        ImageView imgText=findViewById(R.id.boss_manage_updateFood_img);
        EditText nameText=findViewById(R.id.boss_manage_updateFood_name);
        EditText priceText=findViewById(R.id.boss_manage_updateFood_price);
        EditText desText=findViewById(R.id.boss_manage_updateFood_des);
        Button addBtn=findViewById(R.id.boss_manage_updateFood_addButton);

        //将商品原有的图片设置成默认图片
        Bitmap bitmap=BitmapFactory.decodeFile(food.getFoodImg());
        imgText.setImageBitmap(bitmap);
        Drawable drawable = imgText.getDrawable();//获取当前头像
        Bitmap defaultDrawable = ((BitmapDrawable) drawable).getBitmap();//获取这个图片的二进制文件 获取默认头像

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
                    Toast.makeText(ManageBossUpdateFoodActivity.this, "未选择商品图片", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imgText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(v);
            }
        });

        nameText.setText(food.getFoodName());
        priceText.setText(food.getFoodPrice());
        desText.setText(food.getFoodDes());

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String price = priceText.getText().toString();
                String des = desText.getText().toString();
                Drawable drawable = imgText.getDrawable();//获取当前头像
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();//获取这个图片的二进制文件
                String path = FileImgUntil.getImgName();//获取图片的存储路径
                if (name.isEmpty()) {
                    Toast.makeText(ManageBossUpdateFoodActivity.this, "请输入商品名称", Toast.LENGTH_SHORT).show();
                } else if (price.isEmpty()) {
                    Toast.makeText(ManageBossUpdateFoodActivity.this, "请输入价格", Toast.LENGTH_SHORT).show();
                } else if (des.isEmpty()) {
                    Toast.makeText(ManageBossUpdateFoodActivity.this, "请输入商品描述", Toast.LENGTH_SHORT).show();
                } else {
                    if (bitmap.sameAs(defaultDrawable)) {
                        //检查bitmap是否与默认图片相同
                        path=food.getFoodImg();
                    }else {
                        FileImgUntil.saveImageBitmapToFileImg(uri, ManageBossUpdateFoodActivity.this, path);//保存图片
                    }
                    //准备就绪，写一个修改dao类
                    if(FoodDao.updateFood(food.getFoodId(),name,des,price,path)){
                        Toast.makeText(ManageBossUpdateFoodActivity.this, "成功修改商品", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ManageBossUpdateFoodActivity.this, "修改商品失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //打开文件选择器

    public void openGallery(View v) {
        getContentLauncher.launch("image/*");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.boss_manage_food_del_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int a=item.getItemId();
        if(a==R.id.boss_manage_food_del){
            //删除，是否要删除，删除成功会返回主页面
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("删除商品");
            builder.setMessage("确认删除？");
            builder.setCancelable(false);
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(FoodDao.delFoodById(food_id)){
                        Toast.makeText(ManageBossUpdateFoodActivity.this, "成功删除", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ManageBossUpdateFoodActivity.this,ManageBossActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(ManageBossUpdateFoodActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}