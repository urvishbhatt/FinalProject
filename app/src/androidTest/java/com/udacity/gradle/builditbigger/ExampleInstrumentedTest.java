package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.test.InstrumentationRegistry;

import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidlib.JokeActivity;
import com.example.bhatt.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by bhatt on 02-07-2017.
 */

@RunWith(JUnit4.class)
public class ExampleInstrumentedTest {


    private static final String LOG_TAG = "ExampleInstrumentedTest";

    @Test
    public void useAppContext() throws Exception {

//      Context of the app under test.

        Context appContext = InstrumentationRegistry.getTargetContext();


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

            }

        }

        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask();
        endpointsAsyncTask.execute(new Pair<Context, String>(appContext, "null"));


        String result = null;

        try {
            result = endpointsAsyncTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result == null) {
            Log.e(LOG_TAG, "string is null");
        } else {
            Log.e(LOG_TAG, "string is not null : " + result);
        }

        ActivityTestRule<JokeActivity> activityRule
                = new ActivityTestRule<>(
                JokeActivity.class,
                true,     // initialTouchMode
                false);   // launchActivity. False to customize the intent

        Intent intent = new Intent(appContext, JokeActivity.class);
        intent.putExtra("joke", result);

        activityRule.launchActivity(intent);

        /********************* Check Joke ***************************************/

        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("A snail walks into a bar and the barman tells him there's a strict policy about having snails in the bar and so kicks him out. A year later the same snail re-enters the bar and asks the barman \"What did you do that for?\" ");
        arrayList.add("Mother: \"Did you enjoy your first day at school?\" \n" +
                "Girl: \"First day? Do you mean I have to go back tomorrow? ");
        arrayList.add("A: Hey, man! Please call me a taxi. \n" +
                "B: Yes, sir. You are a taxi. ");
        arrayList.add("A: Why are you crying? \n" +
                "B: The elephant is dead. \n" +
                "A: Was he your pet? \n" +
                "B: No, but I'm the one who must dig his grave. ");
        arrayList.add("A teenage girl had been talking on the phone for about half an hour, and then she hung up.\n" +
                "\"Wow!,\" said her father, \"That was short. You usually talk for two hours. What happened?\"\n" +
                "\n" +
                "\"Wrong number,\" replied the girl.\n" +
                "\n");

        for (int i = 0; arrayList.size() > i; i++) {

            String check = arrayList.get(i);

            if (check.equals(result)) {
                Log.e(LOG_TAG, "Test is complite");
            }


        }
    }
}
