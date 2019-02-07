package h.gullideckel.jobsexcelexport.AsyncTasks.CreateTable;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TableRow;

import java.util.List;

import h.gullideckel.jobsexcelexport.Interfaces.IExcelTable;
import h.gullideckel.jobsexcelexport.Statics.StaticMessages;

public class TaskCreateTable extends AsyncTask<List<String[][]>, String, List<TableRow>>
{
    private Context context;
    private IExcelTable listener;

    private boolean isView = false;

    public void SetView(boolean isView)
    {
        this.isView = isView;
    }

    public TaskCreateTable(Context context, IExcelTable listener)
    {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected List<TableRow> doInBackground(List<String[][]>... lists)
    {
        publishProgress("Building rows for table view");
        RowBuilder rowBuilder = new RowBuilder(context);
        List<TableRow> rows = rowBuilder.CreateTable(lists[0]);

        while(!isView)
        {
            try
            {
                Thread.sleep(100);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        return rows;
    }


    @Override
    protected void onCancelled(List<TableRow> tableRows)
    {
        super.onCancelled(tableRows);
        StaticMessages.Toast("The build of the Excel Table is canceled", context);
    }

    @Override
    protected void onPostExecute(List<TableRow> tableRows)
    {
        super.onPostExecute(tableRows);
        if(tableRows != null)
            listener.onTableRows(tableRows);
    }
}
