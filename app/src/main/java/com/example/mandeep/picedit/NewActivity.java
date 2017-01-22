package com.example.mandeep.picedit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class NewActivity extends AppCompatActivity {

    final int CROP_PIC_REQUEST_CODE = 1;
    String picturePath;
    Intent intent = getIntent();
    Bitmap b,undo;
    ImageButton b1;
    ImageView previewThumbnail;
    private static final String TAG = NewActivity.class.getSimpleName();
    String filename="SMSV";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        b1=(ImageButton)findViewById(R.id.imageButton3);
        previewThumbnail=(ImageView)findViewById(R.id.imageView2);

        if(getIntent().hasExtra("byteArray")) {

            b = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
            undo=b;
            previewThumbnail.setImageBitmap(b);
        }


        if(getIntent().hasExtra("imagePath")) {


            picturePath = getIntent().getStringExtra("imagePath");
            b=BitmapFactory.decodeFile(picturePath);
            undo=b;
            previewThumbnail.setImageBitmap(b);

        }





    }

    public void blackandwhite(android.view.View view) {
        Bitmap bm = ((BitmapDrawable) previewThumbnail.getDrawable()).getBitmap();


        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);

        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                colorMatrix);

        b = bm.copy(
                Bitmap.Config.ARGB_8888, true);

        Paint paint = new Paint();
        paint.setColorFilter(colorMatrixFilter);

        Canvas canvas = new Canvas(b);
        canvas.drawBitmap(b, 0, 0, paint);


        ImageView previewThumbnail = new ImageView(this);
        previewThumbnail = (ImageView) findViewById(R.id.imageView2);
        previewThumbnail.setImageBitmap(b);

    }


    public void Rotate(View view) {
     // previewThumbnail.setRotation(previewThumbnail.getRotation() + 90);

        Matrix m = new Matrix();
        m.setRotate(90, (float) b.getWidth() / 2, (float) b.getHeight() / 2);
        Bitmap b2 = Bitmap.createBitmap(
                b, 0, 0, b.getWidth(), b.getHeight(), m, true);
        b=b2;
        previewThumbnail.setImageBitmap(b);
    }

    public void imageCrop(View view) {
        Bitmap srcBmp=b;

        if (srcBmp.getWidth() >= srcBmp.getHeight()){

  b = Bitmap.createBitmap(
     srcBmp,
     srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
     0,
     srcBmp.getHeight(),
     srcBmp.getHeight()
     );

}else{

  b = Bitmap.createBitmap(
     srcBmp,
     0,
     srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
     srcBmp.getWidth(),
     srcBmp.getWidth()
     );

}

        previewThumbnail.setImageBitmap(b);
        }


    public void flipside(View view) {
        Matrix matrixMirror = new Matrix();
        matrixMirror.preScale(-1.0f, 1.0f);
        Bitmap bitmapMaster = Bitmap.createBitmap(
                b,
                0,
                0,
                b.getWidth(),
                b.getHeight(),
                matrixMirror,
                false);
        b=bitmapMaster;
        previewThumbnail.setImageBitmap(b);
    }

    public void flipupdown(View view) {

        Matrix matrixMirror = new Matrix();
        matrixMirror.preScale(1.0f, -1.0f);
        Bitmap bitmapMaster = Bitmap.createBitmap(
                b,
                0,
                0,
                b.getWidth(),
                b.getHeight(),
                matrixMirror,
                false);
        b=bitmapMaster;
        previewThumbnail.setImageBitmap(b);
    }

    public void Undo(View view) {
        b=undo;
        previewThumbnail.setImageBitmap(b);

    }

    public void Saveimage(View view) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            b.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        AlertDialog alertDialog = new AlertDialog.Builder(
                NewActivity.this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Alert");

        // Setting Dialog Message
        alertDialog.setMessage("Your image has been saved");

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                Toast.makeText(getApplicationContext(), "Thank You For Using Our App", Toast.LENGTH_SHORT).show();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }



}
