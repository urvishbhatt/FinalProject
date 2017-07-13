package com.udacity.gradle.builditbigger;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.annotation.StringDef;
import android.support.annotation.StyleRes;
import android.support.test.InstrumentationRegistry;

import android.support.test.rule.ActivityTestRule;
import android.test.AndroidTestCase;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidlib.JokeActivity;
import com.example.bhatt.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import com.udacity.gradle.builditbigger.MainActivity;

/**
 * Created by bhatt on 02-07-2017.
 */

@RunWith(JUnit4.class)
public class ExampleInstrumentedTest {


    private static final String LOG_TAG = "ExampleInstrumentedTest";

    private String result;

    Context context;




    @Rule
    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() throws Exception {


        context = rule.getActivity();

        MainActivity.EndpointsAsyncTask endpointsAsyncTask = new MainActivity.EndpointsAsyncTask();
        endpointsAsyncTask.execute(new android.support.v4.util.Pair<Context, String>(context,"null"));




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

        /*************************************************************************/



        /****************As per Udacity reviewer said********************/

        result = endpointsAsyncTask.get();

        assertNotNull(result);
        Log.e(LOG_TAG,"Joke is not null");

        /******************************************************************/

        for (int i = 0; arrayList.size() > i; i++) {

            String check = arrayList.get(i);

            if (check.equals(result)) {
                Log.e(LOG_TAG, "Test is complite");
            }

        }
    }

}
