package h.gullideckel.jobsexcelexport.Objects;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.List;

import h.gullideckel.jobsexcelexport.Objects.CompanyObjectOld.CompanyBenefits;

public class CompanyDocument
{
    private String name;
    private String type;
    private List<String> types;
    private CompanyAddress address;
    private CompanyContact contact;
    private CompanyJobs jobs;
    private CompanyExtras extras;
    private String notes = "";
    private String errorMessage = "";
    private String features;
    private boolean isCorrect = true;
    private boolean isMayCorrect = true;
    private String id;

    @Exclude
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    @Exclude
    public boolean isMayCorrect()
    {
        return isMayCorrect;
    }

    public void setMayCorrect(boolean mayCorrect)
    {
        isMayCorrect = mayCorrect;
    }

    @Exclude
    public String getErrorMessage()
    {
        return errorMessage;
    }

    @Exclude
    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public CompanyDocument()
    {
        types = new ArrayList<>();
    }

    @Exclude
    public boolean isCorrect()
    {
        return isCorrect;
    }

    public void setCorrect(boolean correct)
    {
        isCorrect = correct;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Exclude
    public String getType()
    {
        return type;
    }

    public void addType(String type)
    {
        types.add(type);
    }

    public List<String> getTypes()
    {
        return types;
    }

    public void setTypes(List<String> types)
    {
        this.types = types;
    }

    public CompanyAddress getAddress()
    {
        return address;
    }

    public void setAddress(CompanyAddress address)
    {
        this.address = address;
    }

    public CompanyContact getContact()
    {
        return contact;
    }

    public void setContact(CompanyContact contact)
    {
        this.contact = contact;
    }

    public CompanyJobs getJobs()
    {
        return jobs;
    }

    public void setJobs(CompanyJobs jobs)
    {
        this.jobs = jobs;
    }

    public CompanyExtras getExtras()
    {
        return extras;
    }

    public void setExtras(CompanyExtras extras)
    {
        this.extras = extras;
    }



    //TODO: Old stuff
    private List<CompanyBenefits.CompanyBenefit> benefits;

    @Exclude
    public List<CompanyBenefits.CompanyBenefit> getBenefits()
    {
        return benefits;
    }

    public void setBenefits(List<CompanyBenefits.CompanyBenefit> benefits)
    {
        this.benefits = benefits;
    }

    @Exclude
    public String getFeatures()
    {
        return features;
    }

    public void setFeatures(String features)
    {
        this.features = features;
    }

}
