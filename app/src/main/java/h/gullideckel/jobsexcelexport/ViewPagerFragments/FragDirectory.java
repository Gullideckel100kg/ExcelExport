package h.gullideckel.jobsexcelexport.ViewPagerFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;


import java.io.File;
import java.util.ArrayList;

import h.gullideckel.jobsexcelexport.AsyncTasks.ReadCsv.TaskReadCsv;
import h.gullideckel.jobsexcelexport.Interfaces.ICompanyDocuments;
import h.gullideckel.jobsexcelexport.Interfaces.ICsvData;
import h.gullideckel.jobsexcelexport.R;
import h.gullideckel.jobsexcelexport.Statics.StaticMessages;


public class FragDirectory extends Fragment
{
    private final static String TAG = "FragDirectory";

    private String[] filePathStrings;
    private String[] fileNameStrings;
    private File[] listFile;

    private File file;

    private Button btnUpDirectory;
    private CheckBox chkSavePath;
    private TextView txtInfo;

    private ArrayList<String> pathHistory;
    private String lastDirectory;
    private int count = 0;

    private ListView internalStorage;

    private ICsvData listener;
    private TaskReadCsv taskReadCsv;

    public static FragDirectory newInstance(ICsvData listener, TaskReadCsv taskReadCsv)
    {
        FragDirectory fragDirectory = new FragDirectory();
        fragDirectory.listener = listener;
        fragDirectory.taskReadCsv = taskReadCsv;
        return  fragDirectory;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        internalStorage = (ListView) view.findViewById(R.id.lstInternalStorage);
        btnUpDirectory = (Button) view.findViewById(R.id.btnUpdirectory);
        txtInfo = (TextView) view.findViewById(R.id.txtDirectoryInfo);

        chkSavePath = (CheckBox) view.findViewById(R.id.chkSavePath);

        OpenSdCard();

        internalStorage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                lastDirectory = pathHistory.get(count);
                if(lastDirectory.equals(parent.getItemAtPosition(position)))
                {
                    if(chkSavePath.isChecked())
                    {
                        SharedPreferences sharedPref =  getActivity().getSharedPreferences(getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(getString(R.string.saved_directory_key), lastDirectory);
                        editor.commit();
                    }
                    if(lastDirectory.length() > 4 && lastDirectory.toLowerCase().endsWith(".csv"))
                        readExcelData(lastDirectory);
                    else
                        StaticMessages.Toast("Please select a 'csv' file", getContext());
                }
                else
                {
                    count++;
                    pathHistory.add(count, (String) parent.getItemAtPosition(position));
                    CheckInternalStorage();
                }
            }
        });


        btnUpDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(count == 0)
                {
                    Log.d(TAG, "btnUpDirectory: You have reached the highest level diretory");
                }
                else
                {
                    pathHistory.remove(count);
                    count--;
                    CheckInternalStorage();
                    Log.d(TAG, "btnUpDirectory: " + pathHistory.get(count));
                }
            }
        });
    }

    private void readExcelData(String filePath)
    {
        if(!filePath.isEmpty() && filePath.toLowerCase().endsWith(".csv"))
        {
            File file = new File(filePath);
            taskReadCsv = new TaskReadCsv(getContext(), listener);
            if(taskReadCsv.getStatus() == AsyncTask.Status.RUNNING)
                taskReadCsv.cancel(true);
            taskReadCsv.execute(file);
        }
    }

    private void OpenSdCard()
    {
        count = 0;
        pathHistory = new ArrayList<String>();
        pathHistory.add(count, System.getenv("EXTERNAL_STORAGE"));
        Log.d(TAG, "btnSdCard: " + pathHistory.get(count));
        CheckInternalStorage();
    }

    private void CheckInternalStorage()
    {
        try
        {
            if(Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED))
            {
                txtInfo.setText("No SD Card found");
            }
            else
            {
                file = new File(pathHistory.get(count));
            }
            listFile = file.listFiles();

            filePathStrings = new String[listFile.length];
            fileNameStrings = new String[listFile.length];

            for(int i = 0; i < listFile.length; i++)
            {
                //get the path of the file
                filePathStrings[i] = listFile[i].getAbsolutePath();

                //get the name of the file
                fileNameStrings[i] = listFile[i].getName();
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, filePathStrings);
            internalStorage.setAdapter(adapter);

        }catch (NullPointerException e)
        {
            Log.e(TAG, "CheckInternalStorage: " + e.getMessage() );
            StaticMessages.Toast("Click again for opening file", getContext());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.frag_directory, container, false);
    }

}
