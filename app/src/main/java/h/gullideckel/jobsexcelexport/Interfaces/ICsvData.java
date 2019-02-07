package h.gullideckel.jobsexcelexport.Interfaces;

import java.util.List;

public interface ICsvData
{
    void onRecieveData(List<String[][]> data);
}
