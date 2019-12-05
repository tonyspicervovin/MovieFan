package com.tony.moviefan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener {



    private String TAG = "MAIN_ACTIVITY";

    private Button mShowWeather;
    private EditText mGetCity;






    public MainActivity() {
        //empty constructer
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment mainFragment = MainFragment.newInstance();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, mainFragment);
        ft.commit();
        //displaying main fragment initially











    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
