package com.example.picfriendstest.Styletransfer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.picfriendstest.Main.MainActivity;
import com.example.picfriendstest.R;

public class DownloadActivity extends AppCompatActivity {

    private static final String TAG = "DownloadActivity";


    private Context mContext = DownloadActivity.this;

    //widgets
    private ProgressBar mProgressBar;
    private ImageView mDownloadImage;
    private Button btn_download;

    //vars
    private int imageCount = 0 ;
    private String imgUrl;
    private Bitmap bitmap;
    private Intent intent;
    private String mAppend = "file:/";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_download);

        mProgressBar = (ProgressBar) findViewById(R.id.downloadProgressBar);
        mProgressBar.setVisibility(View.GONE);

        mDownloadImage = (ImageView) findViewById(R.id.DownloadImage);

        btn_download = (Button) findViewById(R.id.buttonDownload);

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: button download selected");

                Toast.makeText(DownloadActivity.this, "Download Successful",Toast.LENGTH_SHORT).show();

            }
        });


        TextView Finish = (TextView) findViewById(R.id.tvFinish);
        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
            }
        });

        ImageView BackArrow = (ImageView) findViewById(R.id.ivBackArrowForDownload);
        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(mContext, StyleTransferActivity.class);
              startActivity(intent);
            }
        });

    }

}
