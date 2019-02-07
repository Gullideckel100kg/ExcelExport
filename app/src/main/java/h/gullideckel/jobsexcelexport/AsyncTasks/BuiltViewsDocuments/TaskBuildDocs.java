package h.gullideckel.jobsexcelexport.AsyncTasks.BuiltViewsDocuments;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import h.gullideckel.jobsexcelexport.Interfaces.IBuildDocs;
import h.gullideckel.jobsexcelexport.Objects.CompanyDocument;
import h.gullideckel.jobsexcelexport.Objects.DocsDbCsv;
import h.gullideckel.jobsexcelexport.Objects.ViewsDocs;
import h.gullideckel.jobsexcelexport.R;

public class TaskBuildDocs extends AsyncTask<List<DocsDbCsv>, Void, List<ViewsDocs>>
{
    private LayoutInflater inflater;
    private Context context;
    private IBuildDocs listener;

    private boolean isView = false;

    public TaskBuildDocs(LayoutInflater inflater, Context context, IBuildDocs listener)
    {
        this.context = context;
        this.inflater = inflater;
        this.listener = listener;
    }

    public void SetView(boolean isView)
    {
        this.isView = isView;
    }

    @Override
    protected List<ViewsDocs> doInBackground(List<DocsDbCsv>... lists)
    {
        DocumentView docView = new DocumentView(inflater, context);
        List<ViewsDocs> views = new ArrayList<>();


        for(DocsDbCsv doc : lists[0])
        {

            View v2 = null;
            View v = (View) inflater.inflate (R.layout.frag_document_viewer, null);
            docView.Create(v, doc.getDocCsv());

            if(doc.getDocDb() != null)
            {
                v2 = (View) inflater.inflate (R.layout.frag_document_viewer, null);
                docView.Create(v2, doc.getDocDb());
            }
            views.add(new ViewsDocs(v, v2, doc));
        }

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

        return views;
    }

    @Override
    protected void onPostExecute(List<ViewsDocs> views)
    {
        super.onPostExecute(views);
        listener.onRecieveViews(views);

    }
}
