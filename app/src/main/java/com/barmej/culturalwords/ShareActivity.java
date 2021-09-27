package com.barmej.culturalwords;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ShareActivity extends AppCompatActivity {

    private TextView shareTitleTextEdit;

    private int mImageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set lang
        Functions.getAndSetLang(this);

        setContentView(R.layout.activity_share);

        shareTitleTextEdit = findViewById(R.id.edit_text_share_title);
        Button shareButton = findViewById(R.id.button_share_question);
        ImageView imageView = findViewById(R.id.image_view_question);

        // Get image id from intent
        mImageId = getIntent().getIntExtra(Constants.IMAGE_ID_EXTRA, R.drawable.icon_1);

        // Set image view
        imageView.setImageDrawable(ContextCompat.getDrawable(this, mImageId));

        // Button on click listener
        shareButton.setOnClickListener(listener -> share());

    }

    private void share(){
        // Get Share title
        String mShareTextTitle = shareTitleTextEdit.getText().toString();
        if(mShareTextTitle.equals(""))
            mShareTextTitle = getResources().getString(R.string.share_title_default);


        //share image
        int resourceId = mImageId;
        Resources resources = getResources();
        Uri imageUri = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(resourceId))
                .appendPath(resources.getResourceTypeName(resourceId))
                .appendPath(resources.getResourceEntryName(resourceId))
                .build();

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, mShareTextTitle);
        startActivity(Intent.createChooser(shareIntent, "Share question use"));
    }
}