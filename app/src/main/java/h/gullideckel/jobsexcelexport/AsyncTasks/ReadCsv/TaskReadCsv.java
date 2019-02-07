package h.gullideckel.jobsexcelexport.AsyncTasks.ReadCsv;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import h.gullideckel.jobsexcelexport.Interfaces.ICsvData;
import h.gullideckel.jobsexcelexport.Statics.StaticMessages;

public class TaskReadCsv extends AsyncTask<File, String, List<String[][]>>
{
    private static final String TAG = "TaskReadCsv";
    private Context context;
    private ICsvData listener;

    public TaskReadCsv (Context context, ICsvData listener)
    {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected List<String[][]> doInBackground(File... file)
    {
        publishProgress("Reading " + file[0].getName() + "...");
        try
        {
            FileInputStream inputStream = new FileInputStream(file[0]);
            CSVFile csvFile = new CSVFile(inputStream, context);
            List<String[][]> scoreList = csvFile.Read();
            return scoreList;

        } catch (FileNotFoundException e)
        {
            StaticMessages.Toast("Couldn't open file!", context);
            Log.d(TAG, "Couldn't open file", e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<String[][]> strings)
    {
        super.onPostExecute(strings);
        listener.onRecieveData(strings);
    }

    @Override
    protected void onCancelled(List<String[][]> strings)
    {
        super.onCancelled(strings);
        StaticMessages.Toast("Reading csv file is canceld", context);
    }
}
