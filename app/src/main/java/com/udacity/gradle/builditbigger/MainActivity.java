package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.androidlib.JokeActivity;
import com.example.bhatt.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.Random;


public class MainActivity extends AppCompatActivity {


    private ProgressBar progressBar;
    private String joke;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar)findViewById(R.id.jokeprogressbar1);

        progressBar.setVisibility(View.INVISIBLE);

    }

    public void tellJoke(View view) {

        intent = new Intent(this, JokeActivity.class);

        progressBar.setVisibility(View.VISIBLE);

        new EndpointsAsyncTask().execute(new Pair<Context, String>(this,"null"));


    }


    class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {

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

            progressBar.setVisibility(View.GONE);

            joke = result;

            intent.putExtra("joke",joke);

            startActivity(intent);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
