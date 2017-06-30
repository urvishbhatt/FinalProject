package com.example.androidlib;

import android.content.Context;
import android.os.AsyncTask;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.util.Pair;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.example.androidlib.JokeActivity;
import com.example.bhatt.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;


import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final String LOG_TAG = "ExampleInstrumentedTest";

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.androidlib.test", appContext.getPackageName());

         class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {

             private MyApi myApiService = null;
             private Context context;


             @Override
             protected String doInBackground(Pair<Context, String>... params) {

                 if (myApiService == null) {

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
                 Toast.makeText(context, result, Toast.LENGTH_LONG).show();
             }

         }

        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask();
        endpointsAsyncTask.execute(new Pair<Context, String>(appContext,"null"));


        String result = null;

        try {
            result = endpointsAsyncTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result == null){
            Log.e(LOG_TAG,"test is fail");
        }else {
            Log.e(LOG_TAG,"test is complete");
        }

    }
}
