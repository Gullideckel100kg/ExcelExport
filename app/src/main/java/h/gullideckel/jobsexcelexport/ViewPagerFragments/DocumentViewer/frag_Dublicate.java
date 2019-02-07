package h.gullideckel.jobsexcelexport.ViewPagerFragments.DocumentViewer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import h.gullideckel.jobsexcelexport.R;


public class frag_Dublicate extends Fragment
{



    public static frag_Dublicate newInstance(String param1, String param2)
    {
        frag_Dublicate fragment = new frag_Dublicate();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag__dublicate, container, false);
    }


}
