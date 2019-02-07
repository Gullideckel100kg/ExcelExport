package h.gullideckel.jobsexcelexport.AsyncTasks.DocumentErroring;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import h.gullideckel.jobsexcelexport.Interfaces.ICompanyDocuments;
import h.gullideckel.jobsexcelexport.Objects.CompanyDocument;
import h.gullideckel.jobsexcelexport.Objects.DocsDbCsv;

public class TaskCompareCsvDb extends AsyncTask<List<CompanyDocument>, Void, List<DocsDbCsv>>
{
    private volatile List<CompanyDocument> csvDocs;
    private volatile boolean isCanceled = false;
    private ICompanyDocuments listener;

    public TaskCompareCsvDb(ICompanyDocuments listener)
    {
        this.listener = listener;
    }

    public void SetDocCsv(List<CompanyDocument> csvDocs)
    {
        this.csvDocs = csvDocs;
    }

    public void SetCanceled(boolean isCanceled)
    {
        this.isCanceled = isCanceled;
    }

    @Override
    protected List<DocsDbCsv> doInBackground(List<CompanyDocument>... lists)
    {

        while(csvDocs == null)
        {
            try
            {
                Thread.sleep(100);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            if(isCanceled)
                return null;
        }


        CompareDocs compareDocs = new CompareDocs(lists[0]);
        List<DocsDbCsv> docsDbCsv = new ArrayList<>();
        List<CompanyDocument> dbDocs = new ArrayList<>();

        for(CompanyDocument csvDoc : csvDocs)
        {
            docsDbCsv.add(new DocsDbCsv(compareDocs.Compare(csvDoc), csvDoc));
        }


        return docsDbCsv;
    }

    @Override
    protected void onPostExecute(List<DocsDbCsv> companyDocuments)
    {
        super.onPostExecute(companyDocuments);
        listener.onRecieveDocuments(companyDocuments);

    }
}
