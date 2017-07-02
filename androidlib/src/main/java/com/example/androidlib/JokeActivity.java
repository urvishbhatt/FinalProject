package com.example.androidlib;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;



public class JokeActivity extends AppCompatActivity {



    private TextView textView;
    private ImageView imageView;
    private Random random;
    private int imageid;
    private String joketext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        textView = (TextView)findViewById(R.id.joketextview);
        imageView = (ImageView)findViewById(R.id.jokeimage);




        if (savedInstanceState == null){

            joketext = getIntent().getStringExtra("joke");

            textView.setText(joketext);

            random = new Random();

            int randomnumber = random.nextInt(4);


            switch (randomnumber){
                case 0 :
                    imageid = R.drawable.clown1;
                    break;
                case 1 :
                    imageid = R.drawable.clown2;
                    break;
                case 2 :
                    imageid = R.drawable.clown3;
                    break;
                case 3 :
                    imageid = R.drawable.clown4;
                    break;
            }

            imageView.setImageResource(imageid);

        }else {

            imageid = savedInstanceState.getInt("imageid");
            joketext = savedInstanceState.getString("joketext");

            textView.setText(joketext);
            imageView.setImageResource(imageid);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("imageid",imageid);
        outState.putString("joketext",joketext);
    }
}
