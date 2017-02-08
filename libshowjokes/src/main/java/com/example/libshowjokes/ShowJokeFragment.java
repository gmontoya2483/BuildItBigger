package com.example.libshowjokes;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;



public class ShowJokeFragment extends Fragment {


    private String mJoke;
    private TextView mTextJoke;
    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_show_joke, container, false);
        setLayoutViews();
        retrieveJoke();
        mTextJoke.setText(mJoke);


        return mRootView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    //Helper method to retrieve the Joke
    private void retrieveJoke(){

        Intent intent=getActivity().getIntent();
        if (intent !=null){
            mJoke=intent.getStringExtra(ShowJoke.JOKE_MESSAGE);

        }else{
            mJoke="No Joke";
        }

    }


    //Helper method to localize the views
    private void setLayoutViews(){
        mTextJoke = (TextView) mRootView.findViewById(R.id.textJoke);

    }







}