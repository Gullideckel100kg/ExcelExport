package h.gullideckel.jobsexcelexport.Objects;

import java.util.List;

public class DocsDbCsv
{
    private CompanyDocument docDb;
    private CompanyDocument docCsv;

    public CompanyDocument getDocCsv()
    {
        return docCsv;
    }

    public void setDocCsv(CompanyDocument docCsv)
    {
        this.docCsv = docCsv;
    }

    public DocsDbCsv(CompanyDocument docDb, CompanyDocument docCsv)
    {

        this.docDb = docDb;
        this.docCsv = docCsv;
    }

    public CompanyDocument getDocDb()
    {

        return docDb;
    }

    public void setDocDb(CompanyDocument docDb)
    {
        this.docDb = docDb;
    }
}
