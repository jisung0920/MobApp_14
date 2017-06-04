package com.example.jisung.mobapp_14;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    TextView t1;
    EditText e1;
    String urlstr ="https://www.google.co.kr";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = (TextView)findViewById(R.id.t1);
        e1 = (EditText)findViewById(R.id.e1);
        Button b1 = (Button)findViewById(R.id.next);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });

    }

    Handler handler = new Handler();
    Thread thread = new Thread() {
        @Override
        public void run() {
            try {
                URL url = new URL(urlstr);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    final String data = readData(urlConnection.getInputStream());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            t1.setText(data);
                        }
                    });
                    urlConnection.disconnect();
                }
            } catch (Exception e) {

            }
        }
    };

    String readData(InputStream is) {
        String data = "";
        Scanner s = new Scanner(is);
        while (s.hasNext())
            data += s.nextLine() + "\n";
        s.close();
        return data;
    }


    public void onClick(View v) {
       // urlstr = e1.getText().toString();
        thread.start();
    }
}
