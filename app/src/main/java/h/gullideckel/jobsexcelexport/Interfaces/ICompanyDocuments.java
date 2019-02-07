package h.gullideckel.jobsexcelexport.Interfaces;

import java.util.List;

import h.gullideckel.jobsexcelexport.Objects.CompanyDocument;
import h.gullideckel.jobsexcelexport.Objects.DocsDbCsv;

public interface ICompanyDocuments
{
    void onRecieveDocuments(List<DocsDbCsv> docs);
}
