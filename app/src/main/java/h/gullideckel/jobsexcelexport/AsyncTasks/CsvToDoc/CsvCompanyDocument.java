package h.gullideckel.jobsexcelexport.AsyncTasks.CsvToDoc;

import android.content.Context;
import android.location.Geocoder;

import java.util.ArrayList;
import java.util.List;

import h.gullideckel.jobsexcelexport.Objects.CompanyDocument;
import h.gullideckel.jobsexcelexport.R;

public class CsvCompanyDocument
{
    private int name = -1, location = -1, type = -1, website = -1, phone = -1, email = -1,
                job = -1, start = -1, end = -1, notes = -1, facilities = -1, coureses = -1, description = -1, recruitment = -1;

    private Geocoder geo;
    private Context context;

    public CsvCompanyDocument(Context context)
    {
        this.context = context;
        geo = new Geocoder(context);
    }

    public List<CompanyDocument> CreateObject(List<String[][]> scoreList)
    {
        List<CompanyDocument> docs = new ArrayList<>();
        if(scoreList.size() > 1)
        {
            for(String[][] row : scoreList)
            {
                CsvCellConverter converter = new CsvCellConverter(new CompanyDocument());

                for(int c = 0; c < row.length ; c++)
                {
                    if(row == scoreList.get(0) && row[c].length > 0)
                        IndexColumns(c, row[c][0]);
                    else if(row[c].length > 0)
                        InitCells(c, row[c], converter);
                }

                if(row != scoreList.get(0) && row.length > 0)
                    docs.add(converter.CompleteDocument());
//                process.onProcessNumber(scoreList.indexOf(row) +  1, scoreList.size());
            }
        }
        return  docs;
    }

    private void InitCells(int i, String[] cell, CsvCellConverter converter)
    {
        if(i == name)
            converter.SetCellName(cell);
        if(i == location)
            converter.SetCellLocation(cell, geo);
        if(i == type)
            converter.SetCellType(cell);
        if(i == website)
            converter.SetCellWebsite(cell);
        if(i == phone)
            converter.SetCellPhone(cell);
        if(i == email)
            converter.SetCellEmail(cell);
        if(i == job)
            converter.SetCellJob(cell);
        if(i == start)
            converter.SetCellStart(cell);
        if(i == end)
            converter.SetCellEnd(cell);
        if(i == notes)
            converter.SetCellNotes(cell);
        if(i == facilities)
            converter.SetCellFacilities(cell);
        if(i == coureses)
            converter.SetCellCourses(cell);
        if(i == description)
            converter.SetCellDescription(cell);
        if(i == recruitment)
            converter.SetCellOnlineRecruitment(cell);
    }

    private void IndexColumns(int i, String nme)
    {
        if(nme.toLowerCase().equals(context.getString(R.string.com_name).toLowerCase()))
            name = i;
        else if (nme.toLowerCase().equals(context.getString(R.string.com_location).toLowerCase()))
            location = i;
        else if (nme.toLowerCase().equals(context.getString(R.string.com_type).toLowerCase()))
            type = i;
        else if (nme.toLowerCase().equals(context.getString(R.string.com_website).toLowerCase()))
            website = i;
        else if (nme.toLowerCase().equals(context.getString(R.string.com_phone).toLowerCase()))
            phone = i;
        else if (nme.toLowerCase().equals(context.getString(R.string.com_email).toLowerCase()))
            email = i;
        else if (nme.toLowerCase().equals(context.getString(R.string.com_kind).toLowerCase()))
            job = i;
        else if (nme.toLowerCase().equals(context.getString(R.string.com_start).toLowerCase()))
            start = i;
        else if (nme.toLowerCase().equals(context.getString(R.string.com_end).toLowerCase()))
            end = i;
        else if (nme.toLowerCase().equals(context.getString(R.string.com_notes).toLowerCase()))
            notes = i;
        else if (nme.toLowerCase().equals(context.getString(R.string.com_facilites).toLowerCase()))
            facilities = i;
        else if (nme.toLowerCase().equals(context.getString(R.string.com_desc).toLowerCase()))
            description = i;
        else if (nme.toLowerCase().equals(context.getString(R.string.com_courses).toLowerCase()))
            coureses = i;
        else if (nme.toLowerCase().equals(context.getString(R.string.com_recruitment).toLowerCase()))
            recruitment = i;
    }
}
