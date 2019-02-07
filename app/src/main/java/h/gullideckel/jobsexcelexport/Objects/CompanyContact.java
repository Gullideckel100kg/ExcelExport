package h.gullideckel.jobsexcelexport.Objects;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.List;

public class CompanyContact
{
    private List<String> phoneNumber = new ArrayList<>();
    private List<String> email = new ArrayList<>();
    private String website = "";
    private boolean onlineRecruitment = false;
    private String errorMessagePhone;
    private String errorMessageWebsite;
    private String errorMessageEmail;
    private String errorMessageOnlineRecruitment;

    @Exclude
    public String getErrorMessagePhone()
    {
        return errorMessagePhone;
    }

    public void setErrorMessagePhone(String errorMessagePhone)
    {
        this.errorMessagePhone = errorMessagePhone;
    }

    @Exclude
    public String getErrorMessageWebsite()
    {
        return errorMessageWebsite;
    }

    public void setErrorMessageWebsite(String errorMessageWebsite)
    {
        this.errorMessageWebsite = errorMessageWebsite;
    }

    @Exclude
    public String getErrorMessageEmail()
    {
        return errorMessageEmail;
    }

    public void setErrorMessageEmail(String errorMessageEmail)
    {
        this.errorMessageEmail = errorMessageEmail;
    }

    @Exclude
    public String getErrorMessageOnlineRecruitment()
    {
        return errorMessageOnlineRecruitment;
    }

    public void setErrorMessageOnlineRecruitment(String errorMessageOnlineRecruitment)
    {
        this.errorMessageOnlineRecruitment = errorMessageOnlineRecruitment;
    }

    public List<String> getPhoneNumber()
    {
        return phoneNumber;
    }

    public List<String> getEmail()
    {
        return email;
    }

    public String getWebsite()
    {
        return website;
    }

    public boolean isOnlineRecruitment()
    {
        return onlineRecruitment;
    }

    public void setPhoneNumber(List<String> phoneNumber) { this.phoneNumber = phoneNumber; }

    public void addPhoneNumber(String phone) { phoneNumber.add(phone); }

    public void addEmail(String e) {email.add(e);}

    public void setEmail(List<String> email)
    {
        this.email = email;
    }

    public void setWebsite(String website)
    {
        this.website = website;
    }

    public void setOnlineRecruitment(boolean onlineRecruitment) { this.onlineRecruitment = onlineRecruitment; }
}
