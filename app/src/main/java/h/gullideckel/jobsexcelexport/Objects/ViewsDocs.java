package h.gullideckel.jobsexcelexport.Objects;

import android.view.View;

public class ViewsDocs
{
    private View viewCsv;
    private View viewDb;
    private DocsDbCsv docs;

    public ViewsDocs(View viewCsv, View viewDb, DocsDbCsv docs)
    {
        this.viewCsv = viewCsv;
        this.viewDb = viewDb;
        this.docs = docs;
    }

    public DocsDbCsv getDocs()
    {

        return docs;
    }

    public void setDocs(DocsDbCsv docs)
    {
        this.docs = docs;
    }

    public ViewsDocs(View viewCsv, View viewDb)
    {
        this.viewCsv = viewCsv;
        this.viewDb = viewDb;
    }

    public View getViewCsv()
    {
        return viewCsv;
    }

    public void setViewCsv(View viewCsv)
    {
        this.viewCsv = viewCsv;
    }

    public View getViewDb()
    {
        return viewDb;
    }

    public void setViewDb(View viewDb)
    {
        this.viewDb = viewDb;
    }
}
