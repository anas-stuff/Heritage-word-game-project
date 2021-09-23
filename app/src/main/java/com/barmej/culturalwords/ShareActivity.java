package com.barmej.culturalwords;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ShareActivity extends AppCompatActivity {

    private TextView shareTitleTextEdit;
    private ImageView imageView;
    private Button shareButton;

    private int mImageId, currentIndex;
    private String mShareTextTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        shareTitleTextEdit = findViewById(R.id.edit_text_share_title);
        shareButton = findViewById(R.id.button_share_question);
        imageView = findViewById(R.id.image_view_question);

        // Get image id from intent
        mImageId = getIntent().getIntExtra(Constants.IMAGE_ID_EXTRA, R.drawable.icon_1);
        // Get index from intent
        currentIndex = getIntent().getIntExtra(Constants.INDEX_IMAGE_EXTRA, 0);

        // Set image view
        imageView.setImageDrawable(ContextCompat.getDrawable(this, mImageId));

        // Button on click listener
        shareButton.setOnClickListener(listener -> share());

    }

    private void share(){
        // Get Share title
        mShareTextTitle = shareTitleTextEdit.getText().toString();
        if(mShareTextTitle.equals(""))
            mShareTextTitle = getResources().getString(R.string.share_title_default);

        //share image
        Uri imgUri = Uri.parse("android.resource://" + this.getClass().getPackage() + "/drawable/icon_" + (currentIndex + 1) + ".png");
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imgUri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, mShareTextTitle);
        startActivity(Intent.createChooser(shareIntent, "Share question use"));
    }
}