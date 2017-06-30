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



import com.example.bhatt.myapplication.backend.myApi.MyApi;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;



import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.example.MyClass;

public class JokeActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;

    private TextView textView;
    private ProgressBar progressBar;
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
        progressBar = (ProgressBar)findViewById(R.id.jokeprogressbar);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        //java lib
        /*
        MyClass myClass = new MyClass();
        joketext = myClass.getJoke();
        textView.setText(joketext);*/


        if (savedInstanceState == null){


            new EndpointsAsyncTask().execute(new Pair<Context, String>(this,"null"));

        }else {

            imageid = savedInstanceState.getInt("imageid");
            joketext = savedInstanceState.getString("joketext");

            imageView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            textView.setText(joketext);
            imageView.setImageResource(imageid);
        }
    }

    class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String>{

        private MyApi myApiService = null;
        private Context context;


        @Override
        protected String doInBackground(Pair<Context, String>... params) {

            if (myApiService == null){

                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)

                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver

                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });

                // end options for devappserver

                myApiService = builder.build();
            }

            context = params[0].first;



            try {
                return myApiService.sayHi().execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }


        @Override
        protected void onPostExecute(String result) {


            joketext = result;

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

            updateUI();
        }

    }

    protected void updateUI(){
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(imageid);
        textView.setText(joketext);
        progressBar.setVisibility(View.GONE);

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("imageid",imageid);
        outState.putString("joketext",joketext);
    }
}
