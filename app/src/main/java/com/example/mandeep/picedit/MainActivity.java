package com.example.mandeep.picedit;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {


    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    ImageButton b1,b2;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        b1=(ImageButton)findViewById(R.id.imageButton2);
       // iv=(ImageView)findViewById(R.id.imageView);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });
    }




    ////////

    public void loadImagefromGallery(View view) {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, 1);
    }




    /////////
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK
                && null != data){
        Bitmap bp = (Bitmap) data.getExtras().get("data");

            Intent intent = new Intent(this, NewActivity.class);
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            bp.compress(Bitmap.CompressFormat.PNG, 50, bs);
            intent.putExtra("byteArray", bs.toByteArray());
            startActivity(intent);

         //iv.setImageBitmap(bp);
        }
        ///////



            if (requestCode == 1 && resultCode == RESULT_OK
                    && null != data) {


                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath= cursor.getString(columnIndex);
                cursor.close();
                Intent intent = new Intent(this, NewActivity.class);
                intent.putExtra("imagePath", picturePath);
                startActivity(intent);
/*
                Bitmap bp = (BitmapFactory.decodeFile(picturePath));
                iv.setImageBitmap(bp);
               // iv.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                Intent intent = new Intent(this, NewActivity.class);
                ByteArrayOutputStream bs1 = new ByteArrayOutputStream();
                bp.compress(Bitmap.CompressFormat.PNG, 50, bs1);
                intent.putExtra("byteArray", bs1.toByteArray());
                startActivity(intent);*/

            }



        //////
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

/////





    /////

}
