package h.gullideckel.jobsexcelexport.ViewPagerFragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import h.gullideckel.jobsexcelexport.AsyncTasks.CreateTable.TaskCreateTable;
import h.gullideckel.jobsexcelexport.Interfaces.IExcelTable;
import h.gullideckel.jobsexcelexport.Objects.CompanyDocument;
import h.gullideckel.jobsexcelexport.R;



public class FragExcelTable extends Fragment implements IExcelTable
{
    private TableLayout tblCsv;
    private volatile boolean isView = false;

    private List<TableRow> rows;

    TaskCreateTable task;

    public static FragExcelTable newInstance()
    {
        FragExcelTable fragment = new FragExcelTable();

        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        tblCsv = (TableLayout) view.findViewById(R.id.tblExcelData);

        isView = true;
        if(task != null)
            task.SetView(isView);
    }

    public void RunTaskCreateTable(List<String[][]> data, Context context)
    {
        task = new TaskCreateTable(context, this);
        task.SetView(isView);
        if(task.getStatus() == AsyncTask.Status.RUNNING)
            task.cancel(true);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, data);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.frag_excel_table, container, false);
    }


    @Override
    public void onTableRows(List<TableRow> rows)
    {
        for(TableRow row:rows)
        {
            tblCsv.addView(row, new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }
}
