package com.example.ahmed.notification;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    static TextView txt_text;
    static EditText edit_fileName, edit_textToSave;

    ReadWriteFile readWriteFile;
    static String file_name_with_extension="test.txt";
    static String message="start initial append";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_text = (TextView) findViewById(R.id.txt_text);
        edit_fileName = (EditText) findViewById(R.id.edit_fileName);
        edit_textToSave = (EditText) findViewById(R.id.edit_textToSave);

        readWriteFile = new ReadWriteFile(this);
        //readWriteFile.append(file_name_with_extension, "\n"+message);



        //test file
        String file_name= "test.txt";
        int frames = 4 ;
        int f_keypoints_size = 17*3;
        //test case
        float [] test_array= new float [ frames* f_keypoints_size];

        for (int i = 0; i < frames; i++) {
            Arrays.fill(test_array, i * f_keypoints_size, (i+1) *f_keypoints_size, (float) 1.1 * (i+1) );
        }
        System.out.println(Arrays.toString(test_array));

        //actual writing
        try {

            readWriteFile.keypoints_to_file(test_array, frames , file_name, f_keypoints_size);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void click_read(View view) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
            } else {
                read(file_name_with_extension);
            }
        } else {
            read(file_name_with_extension);
        }
    }

    public void click_write(View view) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 20);
            } else {
                append(file_name_with_extension, message);
            }
        } else {
            append(file_name_with_extension, message);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 10:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    read(file_name_with_extension);
                break;

            case 20:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    append(file_name_with_extension, message);
                break;
        }
    }

    private void read(String file_name_with_extension) {
        readWriteFile.read(file_name_with_extension);
    }

    private void append(String file_name_with_extension, String message) {
        readWriteFile.append(file_name_with_extension, message);

    }

    private void write(String file_name_with_extension) {
        readWriteFile.write(file_name_with_extension);

    }
}
