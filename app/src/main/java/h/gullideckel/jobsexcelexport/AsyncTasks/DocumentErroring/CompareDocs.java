package h.gullideckel.jobsexcelexport.AsyncTasks.DocumentErroring;

import java.util.ArrayList;
import java.util.List;

import h.gullideckel.jobsexcelexport.Objects.CompanyDocument;

public class CompareDocs
{
    private List<CompanyDocument> dbDocs;
    private CompanyDocument dbDocError = null;

    private StringBuilder b;

    public CompareDocs(List<CompanyDocument> dbDocs)
    {
        this.dbDocs = dbDocs;
    }

    public CompanyDocument Compare(CompanyDocument csvDoc)
    {
        b = new StringBuilder();

        if(!csvDoc.isCorrect())
            b.append("This document is wrong and can not be uploaded");

        for(CompanyDocument dbDoc : dbDocs)
        {
            csvDoc.setId(dbDoc.getId());
            if(!dbDoc.equals(csvDoc))
            {
                AddressDuplicated(csvDoc, dbDoc);
                NameDuplicated(csvDoc, dbDoc);
                ContactDuplicated(csvDoc, dbDoc);
            }
            csvDoc.setId("");
        }


        csvDoc.setErrorMessage(b.toString());
        if(dbDocError != null)
            dbDocError.setErrorMessage("Database Document");



        return dbDocError;
    }

    private void AddressDuplicated(CompanyDocument csvDoc, CompanyDocument dbDoc)
    {
        if(csvDoc.getAddress().getAddress().toLowerCase().equals(dbDoc.getAddress().getAddress().toLowerCase()))
        {
            csvDoc.setMayCorrect(false);
            b.append("Same Address ");
            dbDocError = dbDoc;
        }
    }

    private void NameDuplicated(CompanyDocument csvDoc, CompanyDocument dbDoc)
    {
        if(csvDoc.getName().toLowerCase().equals(dbDoc.getName().toLowerCase()))
        {
            csvDoc.setMayCorrect(false);
            b.append("Same Company Name ");
            dbDocError = dbDoc;

        }
    }

    private void ContactDuplicated(CompanyDocument csvDoc, CompanyDocument dbDoc)
    {
        if(!csvDoc.getContact().getPhoneNumber().isEmpty() && !dbDoc.getContact().getPhoneNumber().isEmpty())
            for (String csvPh : csvDoc.getContact().getPhoneNumber())
                for(String dbPh : dbDoc.getContact().getPhoneNumber())
                    if(dbPh.equals(csvPh))
                    {
                        csvDoc.setMayCorrect(false);
                        b.append("Same Phone number ");
                        dbDocError = dbDoc;
                    }

        if(!csvDoc.getContact().getEmail().isEmpty() && !dbDoc.getContact().getEmail().isEmpty())
            for(String csvEm : csvDoc.getContact().getEmail())
                for(String dbEm : dbDoc.getContact().getEmail())
                    if(csvEm.equals(dbEm))
                    {
                        csvDoc.setMayCorrect(false);
                        b.append("Same Email ");
                        dbDocError = dbDoc;
                    }

        if(!csvDoc.getContact().getEmail().isEmpty() && !dbDoc.getContact().getEmail().isEmpty())
            if(csvDoc.getContact().getEmail().equals(dbDoc.getContact().getEmail()))
            {
                csvDoc.setMayCorrect(false);
                b.append("Same Website ");
                dbDocError = dbDoc;
            }

    }








}
