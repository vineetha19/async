package com.example.admin.asyncex;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import static java.lang.System.load;


public class AsyncEx1 extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_ex1);
        imageView = findViewById(R.id.imageView);
        //imageView = (ImageView) findViewById(R.id.image);
        //Picasso.with(this)
        // .load("https://wallpaperbrowse.com/media/images/soap-bubble-1958650_960_720.jpg")
        //.placeholder(R.drawable.placeholder)
        // .resize(400,400)
        // .into(imageView);
        String url = "https://images.freeimages.com/images/large-previews/2fe/butterfly-1390152.jpg";
        new DownloadImage().execute(url);


    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(AsyncEx1.this);
            mProgressDialog.setTitle("Download Image Tutorial");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... url) {

            String imageURL = url[0];

            Bitmap bitmap = null;
            try {

                InputStream input = new URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);
                File storagePath = Environment.getExternalStorageDirectory();
                OutputStream outputStream = new FileOutputStream(new File(storagePath, "nature.jpg"));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }


        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
            mProgressDialog.dismiss();
        }

    }
}