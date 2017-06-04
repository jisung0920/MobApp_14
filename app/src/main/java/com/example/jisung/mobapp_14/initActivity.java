package com.example.jisung.mobapp_14;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class initActivity extends AppCompatActivity {

    String SERVER_IP = "172.17.67.117";
    int SERVER_PORT = 200;
    String msg = "";
    EditText e1;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        e1 = (EditText)findViewById(R.id.etmsg);
        b1 =(Button)findViewById(R.id.next);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(initActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onClick(View v){
        myThread.start();
    }

    Handler myhandler = new Handler();
    Thread myThread = new Thread(){
        @Override
        public void run() {//여기에 클라이언트
            super.run();
            try {
                Socket aSocket = new Socket(SERVER_IP, SERVER_PORT);

                ObjectOutputStream outstream = new ObjectOutputStream(aSocket.getOutputStream());
                outstream.writeObject(msg);
                outstream.flush();

                ObjectInputStream instream = new ObjectInputStream(aSocket.getInputStream());
                final Object obj = instream.readObject();
                myhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(initActivity.this, ""+(String)obj, Toast.LENGTH_SHORT).show();
                    }
                });
                aSocket.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };
}
