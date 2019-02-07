package h.gullideckel.jobsexcelexport.Objects.CompanyObjectOld;

import android.graphics.Bitmap;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.List;

public class CompanyBenefits
{
    private List<CompanyBenefit> companyBenefits;
    private List<CompanyBenefit> companyBenefitsSaved;
    private String features;

    public String getFeatures()
    {
        return features;
    }

    public void setFeatures(String features)
    {
        this.features = features;
    }

    public void setCompanyBenefitsSaved(List<CompanyBenefit> companyBenefitsSaved)
    {
        this.companyBenefitsSaved = companyBenefitsSaved;
    }

    public List<CompanyBenefit> getCompanyBenefitsSaved()

    {
        return companyBenefitsSaved;
    }

    public CompanyBenefits()
    {
        companyBenefits = new ArrayList<>();
        companyBenefitsSaved = new ArrayList<>();
    }

    public List<CompanyBenefit> getCompanyBenefits()
    {
        return companyBenefits;
    }

    public void setCompanyBenefits(List<CompanyBenefit> companyBenefits)
    {
        this.companyBenefits = companyBenefits;
    }

    public static class CompanyBenefit
    {
        private Bitmap benefitLogo;
        private String benefit;
        private boolean isSelected;

        public void setSelected(boolean selected)
        {
            isSelected = selected;
        }


        public boolean isSelected()
        {
            return isSelected;
        }

        @Exclude
        public Bitmap getBenefitLogo()
        {
            return benefitLogo;
        }

        public void setBenefitLogo(Bitmap benefitLogo)
        {
            this.benefitLogo = benefitLogo;
        }

        public String getBenefit()
        {
            return benefit;
        }

        public void setBenefit(String benefit)
        {
            this.benefit = benefit;
        }
    }
}
