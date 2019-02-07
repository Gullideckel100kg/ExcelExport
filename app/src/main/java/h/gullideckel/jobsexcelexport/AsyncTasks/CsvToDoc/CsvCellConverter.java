package h.gullideckel.jobsexcelexport.AsyncTasks.CsvToDoc;

import android.location.Address;
import android.location.Geocoder;

import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;
import java.util.List;

import h.gullideckel.jobsexcelexport.Objects.CompanyAddress;
import h.gullideckel.jobsexcelexport.Objects.CompanyContact;
import h.gullideckel.jobsexcelexport.Objects.CompanyDocument;
import h.gullideckel.jobsexcelexport.Objects.CompanyExtras;
import h.gullideckel.jobsexcelexport.Objects.CompanyJobs;

public class CsvCellConverter
{
    private CompanyDocument doc;
    private CompanyAddress address;
    private CompanyContact contact;
    private CompanyExtras extras;
    private CompanyJobs jobsList;

    public CsvCellConverter(CompanyDocument doc)
    {
        this.doc = doc;
        address = new CompanyAddress();
        contact = new CompanyContact();
        extras = new CompanyExtras();
        jobsList = new CompanyJobs();
    }

    public CompanyDocument CompleteDocument()
    {
        ContainContact();
        doc.setAddress(address);
        doc.setContact(contact);
        doc.setExtras(extras);
        doc.setJobs(jobsList);
        return doc;
    }

    private void ContainContact()
    {
        if(contact.getEmail().size() == 0 && contact.getPhoneNumber().size() == 0 && contact.getWebsite().isEmpty())
        {
            contact.setWebsite("No info in phone, email and website. There has to be one contact info");
            doc.setCorrect(false);
        }
        if(contact.isOnlineRecruitment() && contact.getWebsite().isEmpty())
        {
            contact.setWebsite("If Online recruitment is true there has to be a website");
            doc.setCorrect(false);
        }
    }

    public void SetCellName(String[] name)
    {
        if(name.length > 0 || !name[0].isEmpty())
        {
            doc.setName(name[0]);
        }
        else
        {
            doc.setCorrect(false);
            doc.setName("Empty field");
        }
    }

    public void SetCellLocation(String[] loc, Geocoder geo)
    {

        if(loc.length > 0 && !loc[0].isEmpty())
        {
            try
            {
                List<Address> addresses = geo.getFromLocationName(loc[0], 1);
                if(addresses.size() == 1)
                {
                    address.setAddress(addresses.get(0).getAddressLine(0));
                    address.setCountry(addresses.get(0).getCountryName());
                    address.setPostalCode(addresses.get(0).getPostalCode());
                    address.setLatitude(addresses.get(0).getLatitude());
                    address.setLongitude(addresses.get(0).getLongitude());
                }
                else
                {
                    doc.setCorrect(false);
                    address.setErrorMessage("Couldn't find the address\n Check with copy and paste in google maps\n(there is more than one result the first has to be the correct one)");
                }

            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            doc.setCorrect(false);
            address.setErrorMessage("Empty field");
        }
    }

    //TODO:List all possible company types and inform if a new one shows up
    public void SetCellType(String[] types)
    {
        if(types.length > 0 || !types[0].isEmpty())
        {
            for(String type : types)
                doc.addType(type);
        }
        else
        {
            doc.setCorrect(false);
            doc.addType(new String("Empty field"));
        }
    }

    //TODO:If time it is possible to check all the endings of a website(.com .de .info...)
    public void SetCellWebsite(String[] website)
    {
        if(website.length > 0 && !website[0].isEmpty())
        {
            if(website[0].contains("."))
            {
                contact.setWebsite(website[0]);
            }
            else
            {
                contact.setWebsite("Invalid website address: " + website[0]);
                doc.setCorrect(false);
            }
        }
    }

    //TODO: Check all country codes and compare it with the address country
    public void SetCellPhone(String[] phone)
    {
        if(phone.length > 0)
        {
            for(String p : phone)
            {
                if(!p.isEmpty())
                {
                    if(p.charAt(0) == '+')
                        p.substring(1);

                    if (p.matches("[0-9]+"))
                    {
                        //Just the Country Niue can have less than 8 digits in the phone number
                        if(p.substring(0,2) == "683" && p.length() > 6)
                            contact.addPhoneNumber(p);
                        else if(p.length() > 7)
                        {
                            contact.addPhoneNumber(p);
                        }
                        else
                        {
                            doc.setCorrect(false);
                            contact.addPhoneNumber("Phone number is to short: " + p);
                        }
                    }
                    else
                    {
                        doc.setCorrect(false);
                        contact.addPhoneNumber("Phone number invalid. It just can contain digits(Plus at first character possible): " + p);
                    }
                }
            }
        }
    }

    public void SetCellEmail(String[] email)
    {
        if(email.length > 0)
        {
            for(String e : email)
            {
                if(!e.isEmpty())
                {
                    if(EmailValidator.getInstance().isValid(e))
                        contact.addEmail(e);
                    else
                    {
                        contact.addEmail("Invalid email: " + e);
                        doc.setCorrect(false);
                    }
                }
            }
        }
    }

    public void SetCellJob(String[] jobs)
    {
        if(jobsList.getCompanyJobs().size() == 0)
            SetJobsList(jobs.length);

        if(jobs.length > 0)
        {
            for(int i = 0; i < jobs.length; i++)
            {
                if(!jobs[i].isEmpty())
                {
                    if(i < jobsList.getCompanyJobs().size())
                        jobsList.getCompanyJobs().get(i).setJobTitle(jobs[i]);
                    else
                    {
                        doc.setCorrect(false);
                        jobsList.getCompanyJobs().get(0).setJobTitle("There are more entries than in the 'Start' or 'End' fields. Check in Excel. " + jobs[0]);
                    }
                }
                else
                {
                    doc.setCorrect(false);
                    jobsList.getCompanyJobs().get(i).setJobTitle("Empty field");
                }
            }
        }
        else
        {
            doc.setCorrect(false);
            jobsList.getCompanyJobs().get(0).setJobTitle("EmptyField");
        }
    }

    public void SetCellStart(String[] starts)
    {
        if(jobsList.getCompanyJobs().size() == 0)
            SetJobsList(starts.length);

        if(starts.length > 0)
        {
            for(int i = 0; i < starts.length; i++)
            {
                if(!starts[i].isEmpty())
                {
                    if(i < jobsList.getCompanyJobs().size())
                        jobsList.getCompanyJobs().get(i).setStartDate(starts[i]);
                    else
                    {
                        doc.setCorrect(false);
                        jobsList.getCompanyJobs().get(0).setStartDate("There are more entries than in the 'Kind of word' or 'End' fields. Check in Excel. " + starts[0]);
                    }
                }
                else
                {
                    doc.setCorrect(false);
                    jobsList.getCompanyJobs().get(i).setStartDate("Empty field");
                }
            }
        }
        else
        {
            doc.setCorrect(false);
            jobsList.getCompanyJobs().get(0).setStartDate("EmptyField");
        }
    }

    public void SetCellEnd(String[] ends)
    {
        if(jobsList.getCompanyJobs().size() == 0)
            SetJobsList(ends.length);

        if(ends.length > 0)
        {
            for(int i = 0; i < ends.length; i++)
            {
                if(!ends[i].isEmpty())
                {
                    if(i < jobsList.getCompanyJobs().size())
                        jobsList.getCompanyJobs().get(i).setEndDate(ends[i]);
                    else
                    {
                        doc.setCorrect(false);
                        jobsList.getCompanyJobs().get(0).setEndDate("There are more entries than in the 'Kind of word' or 'Start' fields. Check in Excel. " + ends[0]);
                    }
                }
                else
                {
                    doc.setCorrect(false);
                    jobsList.getCompanyJobs().get(i).setEndDate("Empty field");
                }
            }
        }
        else
        {
            doc.setCorrect(false);
            jobsList.getCompanyJobs().get(0).setEndDate("EmptyField");
        }
    }

    public void SetCellNotes(String[] notes)
    {
        if(notes.length > 0)
        {
            StringBuilder b = new StringBuilder();
            for(String note : notes)
            {
                if(notes[0] != note)
                    b.append("\n");

                if(!note.isEmpty())
                    b.append(note);
            }
            doc.setNotes(b.toString());
        }
    }

    public void SetCellFacilities(String[] facilities)
    {
        if(facilities.length > 0)
        {
            StringBuilder b = new StringBuilder();
            for(String note : facilities)
            {
                if(facilities[0] != note)
                    b.append("\n");

                if(!note.isEmpty())
                    b.append(note);
            }
            extras.setFacilities(b.toString());
        }
    }

    public void SetCellDescription(String[] desc)
    {
        if(desc.length > 0)
        {
            StringBuilder b = new StringBuilder();
            for(String note : desc)
            {
                if(desc[0] != note)
                    b.append("\n");

                if(!note.isEmpty())
                    b.append(note);
            }
            extras.setFacilities(b.toString());
        }
    }

    public void SetCellCourses(String[] courses)
    {
        if(courses.length > 0)
        {
            StringBuilder b = new StringBuilder();
            for(String note : courses)
            {
                if(courses[0] != note)
                    b.append("\n");

                if(!note.isEmpty())
                    b.append(note);
            }
            extras.setCourses(b.toString());
        }
    }

    public void SetCellOnlineRecruitment(String[] recruitment)
    {
        if(recruitment.length > 0)
        {
            if(recruitment[0].toLowerCase() == "true")
                contact.setOnlineRecruitment(true);
        }
    }

    private void SetJobsList(int l)
    {
        if(l > 0)
        {
            for(int i = 0; i < l; i++)
                jobsList.getCompanyJobs().add(new CompanyJobs.CompanyJob());
        }
        else
            jobsList.getCompanyJobs().add(new CompanyJobs.CompanyJob());
    }




}
