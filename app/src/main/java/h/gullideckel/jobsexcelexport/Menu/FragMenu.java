package h.gullideckel.jobsexcelexport.Menu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import h.gullideckel.jobsexcelexport.R;


public class FragMenu extends Fragment
{
    public static FragMenu newInstance()
    {
        FragMenu fragment = new FragMenu();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.frag_menu, container, false);

        Button btnMessage = (Button) v.findViewById(R.id.btnMenuMessage);

        btnMessage.setOnClickListener(Message);

        return v;
    }

    private View.OnClickListener Message = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            OpenFragment();
        }
    };

    private void OpenFragment()
    {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.frmMenuFrag, new FragGeneralMessages());
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
