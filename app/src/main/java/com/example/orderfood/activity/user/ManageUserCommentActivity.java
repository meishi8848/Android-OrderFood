package com.example.orderfood.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.orderfood.MainActivity;
import com.example.orderfood.R;
import com.example.orderfood.activity.boss.ManageBossAddFoodActivity;
import com.example.orderfood.activity.user.listen.StartListen;
import com.example.orderfood.dao.CommentDao;
import com.example.orderfood.until.FileImgUntil;
import com.example.orderfood.until.Tools;

public class ManageUserCommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_user_comment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar=findViewById(R.id.user_comment_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        EditText reviewText=findViewById(R.id.user_comment_reviewText);
        ImageView star1=findViewById(R.id.user_comment_review_star1);
        ImageView star2=findViewById(R.id.user_comment_review_star2);
        ImageView star3=findViewById(R.id.user_comment_review_star3);
        ImageView star4=findViewById(R.id.user_comment_review_star4);
        ImageView star5=findViewById(R.id.user_comment_review_star5);
        TextView satisfy=findViewById(R.id.user_comment_satisfy);

        star1.setOnClickListener(new StartListen(ManageUserCommentActivity.this));
        star2.setOnClickListener(new StartListen(ManageUserCommentActivity.this));
        star3.setOnClickListener(new StartListen(ManageUserCommentActivity.this));
        star4.setOnClickListener(new StartListen(ManageUserCommentActivity.this));
        star5.setOnClickListener(new StartListen(ManageUserCommentActivity.this));

        //实现拍照功能，以及获取相册功能

        ImageView img=findViewById(R.id.user_comment_picture);
        Button takePhoto=findViewById(R.id.user_comment_takePhoto);
        Button photoGallery=findViewById(R.id.user_comment_photoGallery);
        Button releaseReview=findViewById(R.id.user_comment_release);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakePhotos();
            }
        });
        photoGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectFromPhotoGallery();
            }
        });

        String account= Tools.getOnAccount(this);
        Intent intent=getIntent();
        String bossId=intent.getStringExtra("bossId");
//        if(bossId==null||bossId.isEmpty()){
//            bossId="root";
//        }
        releaseReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=reviewText.getText().toString();
                if(text.isEmpty()){
                    Toast.makeText(ManageUserCommentActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                }else{

                    String FoodImg= FileImgUntil.getImgName();//获取食物的图片名称
                    String path = FileImgUntil.getImgName();//获取图片的存储路径
                    Drawable drawable=img.getDrawable();
                    if(drawable==null){
                        path="";
                    }else{
                        Bitmap bitmap=((BitmapDrawable)drawable).getBitmap();
                        FileImgUntil.saveSystemImgToPath(bitmap,path);
                    }

                    String score=satisfy.getText().toString();
                    String con[]={"非常差","比较差","一般","比较满意","非常满意"};//代表5个满意程度
                    int sc=1;
                    for(int i=0;i<con.length;i++){
                        if(con[i].equals(score)){
                            sc=i+1;
                            break;
                        }
                    }

                    if(CommentDao.InsertComment(account,bossId,text,String.valueOf(sc),path)){
                        Toast.makeText(ManageUserCommentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ManageUserCommentActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                    }

//                    FileImgUntil.saveImageBitmapToFileImg(uri, ManageUserCommentActivity.this, path);//保存图片

                }
            }
        });

        cameraLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result->{
            if(result.getResultCode()==Activity.RESULT_OK){
                Intent data=result.getData();
                if(data!=null){
                    Bundle extras=data.getExtras();
                    if(extras!=null&&extras.containsKey("data")){
                        //拍照获得照片
                        Bitmap imageBitmap=(Bitmap) extras.get("data");
                        img.setImageBitmap(imageBitmap);
                    }else{
                        //相册选择照片
                        Uri selectedImageuri=data.getData();
                        img.setImageURI(selectedImageuri);
                    }
                }
            }
        });

    }

    private ActivityResultLauncher<Intent> cameraLauncher;

    private void TakePhotos(){
        Intent pic=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(pic);
    }

    private void SelectFromPhotoGallery(){
        Intent pic=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        cameraLauncher.launch(pic);
    }

}