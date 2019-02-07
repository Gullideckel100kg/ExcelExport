package h.gullideckel.jobsexcelexport.AsyncTasks.BuiltViewsDocuments;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import h.gullideckel.jobsexcelexport.Objects.CompanyDocument;
import h.gullideckel.jobsexcelexport.Objects.CompanyJobs;
import h.gullideckel.jobsexcelexport.R;

public class DocumentView
{
    private TextView txtErrorMessage;
    private TextView txtName;
    private TextView txtType;
    private TextView txtAddressLine;
    private TextView txtCountry;
    private TextView txtPostalCode;
    private TextView txtLatLng;
    private TextView txtPhoneHead;
    private LinearLayout linPhone;
    private TextView txtEmailHead;
    private LinearLayout linEmail;
    private TextView txtWebsiteHead;
    private TextView txtWebsite;
    private TextView txtRecruitment;
    private LinearLayout linJobs;
    private TextView txtFacilitiesHead;
    private TextView txtFacilites;
    private TextView txtCoursesHead;
    private TextView txtCourses;
    private TextView txtDescriptionHead;
    private TextView txtDescription;

    private void InitViews(View v)
    {
        txtErrorMessage = (TextView) v.findViewById(R.id.txtErrorMessage);
        txtName = (TextView) v.findViewById(R.id.txtName);
        txtType =(TextView) v.findViewById(R.id.txtType);
        txtAddressLine = (TextView) v.findViewById(R.id.txtAddressLine);
        txtCountry = (TextView) v.findViewById(R.id.txtCountry);
        txtPostalCode = (TextView) v.findViewById(R.id.txtPostalCode);
        txtLatLng = (TextView) v.findViewById(R.id.txtLatLng);
        txtPhoneHead = (TextView) v.findViewById(R.id.txtPhoneHead);
        linPhone = (LinearLayout) v.findViewById(R.id.linPhones);
        txtEmailHead = (TextView) v.findViewById(R.id.txtEmailHead);
        linEmail = (LinearLayout) v.findViewById(R.id.linEmails);
        txtWebsiteHead = (TextView) v.findViewById(R.id.txtWebsiteHead);
        txtWebsite = (TextView) v.findViewById(R.id.txtWebsite);
        txtRecruitment = (TextView) v.findViewById(R.id.txtOnlineRecruitmant);
        linJobs = (LinearLayout) v.findViewById(R.id.linJobs);
        txtFacilitiesHead = (TextView) v.findViewById(R.id.txtFacilitiesHead);
        txtFacilites = (TextView) v.findViewById(R.id.txtFacilites);
        txtCoursesHead = (TextView) v.findViewById(R.id.txtCoursesHead);
        txtCourses = (TextView) v.findViewById(R.id.txtCourses);
        txtDescriptionHead = (TextView) v.findViewById(R.id.txtDescriptionHead);
        txtDescription = (TextView) v.findViewById(R.id.txtDescription);
    }

    private LayoutInflater inflater;
    private Context context;

    public DocumentView(LayoutInflater inflater, Context context)
    {
        this.inflater = inflater;
        this.context = context;
    }


    public void Create(View v, CompanyDocument doc)
    {
        InitViews(v);

        JobView jobView = new JobView(inflater);

        //Set Error Message if one Exisits
        if(doc.getErrorMessage().isEmpty())
            txtErrorMessage.setVisibility(View.GONE);
        else
        {
            txtErrorMessage.setText(doc.getErrorMessage());
            txtErrorMessage.setVisibility(View.VISIBLE);
            txtErrorMessage.setTextColor(Color.RED);
        }

        //Company Name
        txtName.setText(doc.getName());

        //Company Type
        if(doc.getTypes() != null && doc.getTypes().size() > 0)
            txtType.setText(BuildType(doc.getTypes()));
        else if(doc.getType() != null)
            txtType.setText(doc.getType());

        //Location
        txtAddressLine.setText(doc.getAddress().getAddress());
        txtCountry.setText(doc.getAddress().getCountry());
        txtPostalCode.setText(doc.getAddress().getPostalCode());
        txtLatLng.setText(String.valueOf(doc.getAddress().getLatitude()) + " / " + String.valueOf(doc.getAddress().getLongitude()));

        //Phone numbers
        if(doc.getContact().getPhoneNumber().size() > 0)
            for(String phone : doc.getContact().getPhoneNumber())
                linPhone.addView(BuildTextView(phone, 8));
        else
            txtPhoneHead.setVisibility(View.GONE);

        //Emails
        if(doc.getContact().getEmail().size() > 0)
            for(String email : doc.getContact().getEmail())
                linEmail.addView(BuildTextView(email, 8));
        else
            txtEmailHead.setVisibility(View.GONE);

        //Website and Online Recruitment
        if(!doc.getContact().getWebsite().isEmpty())
        {
            txtWebsite.setText(doc.getContact().getWebsite());
            if(doc.getContact().isOnlineRecruitment())
                txtRecruitment.setText("Online Recruitment");
            else
                txtRecruitment.setVisibility(View.GONE);
        }
        else
        {
            txtWebsiteHead.setVisibility(View.GONE);
            txtWebsite.setVisibility(View.GONE);
            txtRecruitment.setVisibility((View.GONE));
        }

        //Job
        for(CompanyJobs.CompanyJob job : doc.getJobs().getCompanyJobs())
            linJobs.addView(jobView.BuildJobView(job));

        //Faceilites
        if(doc.getExtras() != null && !doc.getExtras().getFacilities().isEmpty())
            txtFacilites.setText(doc.getExtras().getFacilities());
        else
        {
            txtFacilitiesHead.setVisibility(View.GONE);
            txtFacilites.setVisibility(View.GONE);
        }

        //Courses
        if(doc.getExtras() != null && !doc.getExtras().getCourses().isEmpty())
            txtCourses.setText(doc.getExtras().getCourses());
        else
        {
            txtCourses.setVisibility(View.GONE);
            txtCoursesHead.setVisibility(View.GONE);
        }

        //Description
        if(doc.getExtras() != null && !doc.getExtras().getDescription().isEmpty())
            txtDescription.setText(doc.getExtras().getDescription());
        else
        {
            txtDescription.setVisibility(View.GONE);
            txtDescriptionHead.setVisibility(View.GONE);
        }
    }

    private TextView BuildTextView(String txt, int pad)
    {
        TextView txtView = new TextView(context);
        txtView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtView.setText(txt);
        txtView.setPadding(pad,pad,pad,pad);
        return  txtView;
    }

    private String BuildType(List<String> types)
    {
        StringBuilder b = new StringBuilder();

        b.append(types.get(0));
        if(types.size() > 1)
        {
            for(int i = 1; i < types.size(); i++)
            {
                b.append(" + " + types.get(i));
            }
        }
        return  b.toString();
    }


}
