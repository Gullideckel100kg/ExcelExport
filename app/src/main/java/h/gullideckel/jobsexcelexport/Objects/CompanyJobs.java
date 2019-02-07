package h.gullideckel.jobsexcelexport.Objects;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.List;

public class CompanyJobs
{
    private List<CompanyJob> companyJobs;

    public CompanyJobs()
    {
        companyJobs = new ArrayList<>();
    }

    public List<CompanyJob> getCompanyJobs()
    {
        return companyJobs;
    }

    public void setCompanyJobs(List<CompanyJob> companyJobs)
    {
        this.companyJobs = companyJobs;
    }

    public static class CompanyJob
    {
        private String jobTitle;

        private String startDate;
        private String endDate;
        private String notes = "";

        public String getJobTitle()
        {
            return jobTitle;
        }

        public void setJobTitle(String jobTitle)
        {
            this.jobTitle = jobTitle;
        }

        public String getStartDate()
        {
            return startDate;
        }

        public void setStartDate(String startDate)
        {
            this.startDate = startDate;
        }

        public String getEndDate()
        {
            return endDate;
        }

        public void setEndDate(String endDate)
        {
            this.endDate = endDate;
        }

        public String getNotes()
        {
            return notes;
        }

        public void setNotes(String notes)
        {
            this.notes = notes;
        }


        //TODO: Old stuff
        private boolean hourlyPaid = false;
        private boolean pieceWork = false;
        private boolean volunteering = false;


        private String startDay;
        private String startMonth;
        private String endDay;
        private String endMonth;
        private String addtionalInfo;

        @Exclude
        public boolean isHourlyPaid()
        {
            return hourlyPaid;
        }

        public void setHourlyPaid(boolean hourlyPaid)
        {
            this.hourlyPaid = hourlyPaid;
        }

        @Exclude
        public boolean isPieceWork()
        {
            return pieceWork;
        }

        public void setPieceWork(boolean pieceWork)
        {
            this.pieceWork = pieceWork;
        }

        @Exclude
        public boolean isVolunteering()
        {
            return volunteering;
        }

        public void setVolunteering(boolean volunteering)
        {
            this.volunteering = volunteering;
        }


        public String getStartDay()
        {
            return startDay;
        }

        public void setStartDay(String startDay)
        {
            this.startDay = startDay;
        }

        @Exclude
        public String getStartMonth()
        {
            return startMonth;
        }

        public void setStartMonth(String startMonth)
        {
            this.startMonth = startMonth;
        }

        @Exclude
        public String getEndDay()
        {
            return endDay;
        }

        public void setEndDay(String endDay)
        {
            this.endDay = endDay;
        }

        @Exclude
        public String getEndMonth()
        {
            return endMonth;
        }

        public void setEndMonth(String endMonth)
        {
            this.endMonth = endMonth;
        }

        public void setAddtionalInfo(String addtionalInfo)
        {
            this.addtionalInfo = addtionalInfo;
        }

        @Exclude
        public String getAddtionalInfo()
        {
            return addtionalInfo;
        }
    }
}
