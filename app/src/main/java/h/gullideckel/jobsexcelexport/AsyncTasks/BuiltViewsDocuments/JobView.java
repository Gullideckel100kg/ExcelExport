package h.gullideckel.jobsexcelexport.AsyncTasks.BuiltViewsDocuments;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import h.gullideckel.jobsexcelexport.Objects.CompanyJobs;
import h.gullideckel.jobsexcelexport.R;

public class JobView
{
    private TextView txtJobTitle;
    private TextView txtStart;
    private TextView txtEnd;
    private TextView txtNotesHead;
    private TextView txtNotes;

    private LayoutInflater inflater;

    public JobView(LayoutInflater inflater)
    {
        this.inflater = inflater;
    }

    private void InitJobView(View vJob)
    {
        txtJobTitle = (TextView) vJob.findViewById(R.id.txtJob);
        txtStart = (TextView) vJob.findViewById(R.id.txtStart);
        txtEnd = (TextView) vJob.findViewById(R.id.txtEnd);
        txtNotesHead = (TextView) vJob.findViewById(R.id.txtNotesHead);
        txtNotes = (TextView) vJob.findViewById(R.id.txtNotes);
    }

    public View BuildJobView(CompanyJobs.CompanyJob job)
    {
        View v = inflater.inflate(R.layout.view_job, null);

        InitJobView(v);

        txtJobTitle.setText(job.getJobTitle());
        txtStart.setText(job.getStartDate());
        txtEnd.setText(job.getEndDate());
        if(!job.getNotes().isEmpty())
            txtNotes.setText(job.getNotes());
        else
        {
            txtNotesHead.setVisibility(View.GONE);
            txtNotes.setVisibility(View.GONE);
        }
        return  v;
    }
}
