package com.example.ahmed.notification;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by AHMED on 20/05/2018.
 */

public class ReadWriteFile {
    Context context;

    public ReadWriteFile(Context context) {
        this.context = context;
    }

    public void read(String file_name_with_extension) {
        File root = Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/ahmedFile");
        File file = new File(dir, file_name_with_extension);
        String msg;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuffer stringBuffer = new StringBuffer();

            while ((msg = bufferedReader.readLine()) != null) {
                stringBuffer.append(msg + "\n");
            }

            MainActivity.txt_text.setText(stringBuffer.toString());
        } catch (FileNotFoundException e1) {
            Toast.makeText(context, "file not found", Toast.LENGTH_SHORT).show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    public void write(String file_name_with_extension, String message) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File root = Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath() + "/ahmedFile");
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(dir, file_name_with_extension);
            //String msg = MainActivity.edit_textToSave.getText().toString();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(message.getBytes());
                fileOutputStream.close();
                Toast.makeText(context, "message saved", Toast.LENGTH_SHORT).show();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else {
            Toast.makeText(context, "sd not allowed", Toast.LENGTH_SHORT).show();
        }
    }
    public void append(String file_name_with_extension, String message) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File root = Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath() + "/ahmedFile");
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(dir, file_name_with_extension);
            //String msg = MainActivity.edit_textToSave.getText().toString();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                fileOutputStream.write(message.getBytes());
                fileOutputStream.close();
                Toast.makeText(context, "message saved", Toast.LENGTH_SHORT).show();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else {
            Toast.makeText(context, "sd not allowed", Toast.LENGTH_SHORT).show();
        }
    }




// write to file and append if file already exists
    public  void keypoints_to_file(float[] keypoints, int number_of_frames, String file_name, int frame_size) throws IOException {


        for (int j = 0; j < number_of_frames; j++) {
            //TODO WHERE TO START THE LOOP
            for (int i = frame_size * j; i < frame_size * (j+1); i++) {

                // accessing each element of array
                append(file_name,String.valueOf(keypoints[i]));

                if((i+1) % frame_size != 0){
                    append(file_name,",");
                }
                else{
                    append(file_name,"\n");
                }

            }

        }



    }


    // write to file and empty before writing if file already exists
    public  void keypoints_to_file_empty(float[] keypoints, int number_of_frames, String file_name, int frame_size) throws IOException {


        for (int j = 0; j < number_of_frames; j++) {
            //TODO WHERE TO START THE LOOP
            for (int i = frame_size * j; i < frame_size * (j+1); i++) {

                // accessing each element of array
                write(file_name,String.valueOf(keypoints[i]));

                if((i+1) % frame_size != 0){
                    write(file_name,",");
                }
                else{
                    write(file_name,"\n");
                }

            }

        }



    }


}
