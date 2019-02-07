package h.gullideckel.jobsexcelexport.AsyncTasks.CsvToDoc;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import h.gullideckel.jobsexcelexport.AsyncTasks.DocumentErroring.TaskCompareCsvDb;
import h.gullideckel.jobsexcelexport.Interfaces.ICompanyDocuments;
import h.gullideckel.jobsexcelexport.Objects.CompanyDocument;

public class TaskCsvToDoc extends AsyncTask<List<String[][]>, Integer, List<CompanyDocument>>
{
    private Context context;
    private TaskCompareCsvDb task;

    public TaskCsvToDoc(Context context, TaskCompareCsvDb task)
    {
        this.context = context;
        this.task = task;
    }

    @Override
    protected List<CompanyDocument> doInBackground(List<String[][]>... strings)
    {
        CsvCompanyDocument csvDoc = new CsvCompanyDocument(context);
        return csvDoc.CreateObject(strings[0]);
    }

    @Override
    protected void onPostExecute(List<CompanyDocument> companyDocuments)
    {
        super.onPostExecute(companyDocuments);
        task.SetDocCsv(companyDocuments);
    }
}
