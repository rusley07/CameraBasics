package com.oriellyschool.android.camerabasics;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private static final int TAKE_PICTURE_REQUEST_B = 100;

    private ImageView mCameraImageView;
    private Bitmap mCameraBitmap;
    private Button mSaveImageButton;

    private OnClickListener mCaptureImageButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startImageCapture();
        }
    };

    private OnClickListener mSaveImageButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            /*File saveFile = openFileForImage();
            if (saveFile != null) {
                saveImageToFile(saveFile);
            } else {
                Toast.makeText(MainActivity.this, "Unable to open file " +
                                "for saving image.",
                        Toast.LENGTH_LONG).show();
            }*/
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCameraImageView = (ImageView) findViewById(R.id.camera_image_view);

        findViewById(R.id.camera_image_button).setOnClickListener(mCaptureImageButtonClickListener);

        mSaveImageButton = (Button) findViewById(R.id.save_image_button);
        //mSaveImageButton.setOnClickListener(mSaveImageButtonClickListener);
        mSaveImageButton.setEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == TAKE_PICTURE_REQUEST_B) {
            if(resultCode == RESULT_OK) {
                //Result the previous bitmap
                if(mCameraBitmap != null) {
                    mCameraBitmap.recycle();
                    mCameraBitmap = null;
                }
                Bundle extras = data.getExtras();
                mCameraBitmap = (Bitmap) extras.get("data");
                mCameraImageView.setImageBitmap(mCameraBitmap);
                mSaveImageButton.setEnabled(true);
            } else {
                mCameraBitmap = null;
                mSaveImageButton.setEnabled(false);
            }
        }
    }

    private void startImageCapture() {
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), TAKE_PICTURE_REQUEST_B);
    }

    /*private File openFileForImage() {
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            File imageDirectory = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "com.oriellyschool.android.camerabasics");
            if (!imageDirectory.exists() && !imageDirectory.mkdirs()) {
                imageDirectory = null;
            } else {
                SimpleDateFormat dateFormat = new SimpleFormat("yyyy_mm_dd_hh_mm",
                        Locale.getDefault());
                return new File(imageDirectory.getPath() +
                        File.separator + "image_" + dateFormat.format(new Date()) + ".png");
            }
        }
        return null;
    }*/

    /*private void saveImageToFile(File file) {
        if(mCameraBitmap != null) {
            FileOutputStream outStream = null;
            try {
                outStream = new FileOutputStream(file);
                if (!mCameraBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)) {
                    Toast.makeText(MainActivity.this, "Unable to save image to file.",
                            Toast.LENGTH_LONG).show();
                }
                outStream.close();
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Unable to save image to file.",
                        Toast.LENGTH_LONG).show();
            }
        }
    }*/
}
