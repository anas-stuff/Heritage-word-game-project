package com.barmej.culturalwords;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_WRITE_EXTERNAL_STORAGE = 123;
    private ImageButton changeQuestionButton, showAnswerButton, shareQuestionButton, changeLangButton;
    private ImageView imageView;

    // Get Images
    private final ArrayList<Integer> images = new ArrayList<Integer>();
    int currentIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a primary values
        shareQuestionButton = findViewById(R.id.button_share_question);
        changeLangButton = findViewById(R.id.button_change_language);
        changeQuestionButton = findViewById(R.id.button_change_question);
        showAnswerButton = findViewById(R.id.button_open_answer);
        imageView = findViewById(R.id.image_view_question);

        // Get images
        for(int i = 0; i < getResources().getStringArray(R.array.answers).length; i++)
            images.add(this.getResources().getIdentifier("icon_" + (i + 1), "drawable", this.getPackageName()));

        // Set Random image
        setRandomImage();
        

    }

    private void setRandomImage(){
        currentIndex = (int)(Math.random() * images.size()); // Get Random number between 0 and images size - 1
        // Set image
        imageView.setImageDrawable(getDrawable(images.get(currentIndex)));
    }
    /**
     * يجب عليك كتابة الكود الذي يقوم بمشاركة الصورة في هذه الدالة
     */
    private void shareImage() {
        // كود مشاركة الصورة هنا
    }

    /**
     *  هذه الدالة تقوم بطلب صلاحية الكتابة على ال external storage حتى يمكن حفظ ملف الصورة
     * <p>
     * وفي حال الحصول على الصلاحية تقوم باستدعاء دالة shareImage لمشاركة الصورة
     **/
    private void checkPermissionAndShare() {
        // insertImage في النسخ من آندرويد 6 إلى آندرويد 9 يجب طلب الصلاحية لكي نتمكن من استخدام الدالة
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // هنا لا يوجد صلاحية للتطبيق ويجب علينا طلب الصلاحية منه عن طريك الكود التالي
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // بسبب عدم منح المستخدم الصلاحية للتطبيق فمن الأفضل شرح فائدتها له عن طريق عرض رسالة تشرح ذلك
                // هنا نقوم بإنشاء AlertDialog لعرض رسالة تشرح للمستخدم فائدة منح الصلاحية
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.permission_title)
                        .setMessage(R.string.permission_explanation)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // requestPermissions عند الضغط على زر منح نقوم بطلب الصلاحية عن طريق الدالة
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        PERMISSIONS_WRITE_EXTERNAL_STORAGE);
                            }
                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //  عند الضغط على زر منع نقوم بإخفاء الرسالة وكأن شيء لم يكن
                                dialogInterface.dismiss();
                            }
                        }).create();

                // نقوم بإظهار الرسالة بعد إنشاء alertDialog
                alertDialog.show();

            } else {
                // لا داعي لشرح فائدة الصلاحية للمستخدم ويمكننا طلب الصلاحية منه
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            }

        } else {
            // الصلاحية ممنوحه مسبقا لذلك يمكننا مشاركة الصورة
            shareImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // يتم استدعاء هذه الدالة بعد اختيار المستخدم احد الخيارين من رسالة طلب الصلاحية
        if (requestCode == PERMISSIONS_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // تم منح الصلاحية من قبل المستخدم لذلك يمكننا مشاركة الصورة الآن
                shareImage();
            } else {
                // لم يتم منح الصلاحية من المستخدم لذلك لن نقوم بمشاركة الصورة، طبعا يمكننا تنبيه المستخدم بأنه لن يتم مشاركة الصورة لسبب عدم منح الصلاحية للتطبيق
                Toast.makeText(MainActivity.this, R.string.permission_explanation, Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

}
