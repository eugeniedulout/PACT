package com.example.testmenu;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class FragmentController {



    public static void swapFragment(Fragment dest, int containerId, Context context) {
        FragmentTransaction fr = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        fr.replace(containerId,dest);
        fr.addToBackStack(null).commit();
    }

    public static void swapFragmentInMainContainer(Fragment dest, Context context) {
        swapFragment(dest, R.id.container, context);
    }

}
