package com.example.orderfood.activity.user;

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

import com.example.orderfood.Bean.UserBean;
import com.example.orderfood.R;
import com.example.orderfood.activity.boss.ManageBossActivity;
import com.example.orderfood.activity.boss.ManageBossUpdateMesActivity;
import com.example.orderfood.dao.AdminDao;
import com.example.orderfood.until.FileImgUntil;
import com.example.orderfood.until.Tools;

public class ManageUserUpdateMesActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> getContentLauncher;
    Uri uri=null;
    private String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_user_update_mes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar=findViewById(R.id.user_manage_updateMes_bar);
        setSupportActionBar(toolbar);
        //返回有两种方式
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManageUserUpdateMesActivity.this, ManageUserActivity.class);
                intent.putExtra("sta","1");
                startActivity(intent);
            }
        });

        UserBean user=AdminDao.getCustomerInformation(Tools.getOnAccount(this));

        ImageView imgText=findViewById(R.id.user_manage_updateMes_head);
        imgText.setImageBitmap(BitmapFactory.decodeFile(user.getU_Img()));

        EditText nameText=findViewById(R.id.user_manage_updateMes_name);
        nameText.setText(user.getU_Name());

        RadioButton human=findViewById(R.id.user_manage_updateMes_sex_man);
        RadioButton woman=findViewById(R.id.user_manage_updateMes_sex_woman);
        sex=user.getU_sex();
        if(user.getU_sex().equals("男")){
            human.setChecked(true);
        }else{
            woman.setChecked(true);
        }

        EditText addressText=findViewById(R.id.user_manage_updateMes_address);
        addressText.setText(user.getU_Address());

        EditText phoneText=findViewById(R.id.user_manage_updateMes_phone);
        phoneText.setText(user.getU_Phone());

        Button confirm=findViewById(R.id.user_manage_updateMes_confirmButton);

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
                    Toast.makeText(ManageUserUpdateMesActivity.this, "请选择头像", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Drawable drawable = imgText.getDrawable();//获取当前头像
        Bitmap defaultDrawable = ((BitmapDrawable) drawable).getBitmap();//获取这个图片的二进制文件 获取默认头像

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameText.getText().toString();
                String address=addressText.getText().toString();
                String phone=phoneText.getText().toString();

                if(name.isEmpty()){
                    nameText.setError("用户名称不能为空");
                    nameText.requestFocus();
                }else if(address.isEmpty()){
                    addressText.setError("用户默认收货地址不能为空");
                    addressText.requestFocus();
                }else if(phone.isEmpty()){
                    phoneText.setError("用户联系方式不能为空");
                    phoneText.requestFocus();
                }else{
                    Drawable drawable = imgText.getDrawable();//获取当前头像
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();//获取这个图片的二进制文件

                    String path = FileImgUntil.getImgName();//获取图片的存储路径
                    if (bitmap.sameAs(defaultDrawable)) {
                        //检查bitmap是否与默认图片相同
                        path=user.getU_Img();
                    }else {
                        FileImgUntil.saveImageBitmapToFileImg(uri, ManageUserUpdateMesActivity.this, path);//保存图片
                    }

                    if(human.isChecked()){
                        sex="男";
                    }else{
                        sex="女";
                    }

                    if(AdminDao.UpdateCustomerUser(user.getU_Id(),name,sex,address,phone,path)==1){
                        Toast.makeText(ManageUserUpdateMesActivity.this, "更改成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ManageUserUpdateMesActivity.this, "更改成功", Toast.LENGTH_SHORT).show();
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