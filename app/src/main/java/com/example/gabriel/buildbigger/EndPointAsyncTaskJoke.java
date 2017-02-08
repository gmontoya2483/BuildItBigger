package com.example.gabriel.buildbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.gabriel.myapplication.backend.myApi.MyApi;
import com.example.libshowjokes.ShowJoke;
import com.example.libshowjokes.ShowJokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by montoya on 07.02.2017.
 */

public class EndPointAsyncTaskJoke extends AsyncTask <Void,Void,String> {

    private Context mContext;
    private MyApi myApiService = null;


    public EndPointAsyncTaskJoke(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(Void... voids) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    //.setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setRootUrl(mContext.getString(R.string.host_GCE))
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }



        try {
            return myApiService.sayJoke().execute().getData();
        } catch (IOException e) {
           return mContext.getString(R.string.error_no_retrieved_joke);
        }

    }


    @Override
    protected void onPostExecute(String joke) {
        Intent intent=new Intent(mContext, ShowJokeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(ShowJoke.JOKE_MESSAGE,joke);

        mContext.startActivity(intent);
    }
}
