package com.example.testmenu.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.testmenu.MainActivity;
import com.example.testmenu.R;

public class TestPlan extends AppCompatActivity {

    private Python py;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_plan);

        if(!Python.isStarted()) {
            Python.start(new AndroidPlatform(getApplicationContext()));
        }

        py  = Python.getInstance();
        execPyton execPyton = new execPyton();
        execPyton.execute();
    }
    private class execPyton extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            PyObject pyobj  = py.getModule("test");

            PyObject pbj = pyobj.callAttr("main");

            Log.e("heyyy", pbj.toString());

            return null;
        }

    }
}