package h.gullideckel.jobsexcelexport.AsyncTasks.ReadCsv;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import h.gullideckel.jobsexcelexport.Statics.StaticMessages;

public class CSVFile
{
    private static final String TAG = "CSVFile";

    private InputStream inputStream;
    private Context context;

    public CSVFile(InputStream inputStream, Context context)
    {
        this.inputStream = inputStream;
        this.context = context;
    }

    //Read csv line by line and seperates cells by ';'
    public List<String[][]> Read()
    {
        List<String[][]> resultList = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try
        {
            String csvLine;
            List<String> cellValues = new ArrayList<>();

            while ((csvLine = reader.readLine()) != null)
            {
                String csvCell;
                String[] row1 = csvLine.split(";");
                List<String[]> row = new ArrayList<>();

                if(row1.length>0 && row1[row1.length - 1].charAt(0) == '\"')
                {
                    for(int i = 0; i < row1.length - 1; i++)
                    {
                        row.add(new String[] { row1[i] });
                    }
                    BindCells(row1, reader, row);
                }
                else
                {
                    for(int i = 0; i < row1.length; i++)
                    {
                        row.add(new String[] { row1[i] });
                    }
                }

                if(row.size() > 0)
                    resultList.add(row.toArray(new String[row.size() - 1][]));
                else
                    resultList.add(new String[0][]);
            }
        }
        catch (IOException ex)
        {
            StaticMessages.Toast("Error in reading CSV file", context);
            Log.e(TAG, "Error in reading CSV file: ", ex);
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally
        {
            try
            {
                inputStream.close();
            }
            catch (IOException e)
            {
                Log.e(TAG, "\"Error while closing input stream ", e );
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
        return resultList;
    }

    //Bind cells with more than one value
    //After every value is a line break but the cell is bind with '"'
    private void BindCells(String[] row, BufferedReader reader, List<String[]> rowList) throws IOException
    {

        List<String> cellValues = new ArrayList<>();
        String csvLine;

        cellValues.add(row[row.length - 1].substring(1));

        while(!(csvLine = reader.readLine()).contains("\""))
        {
            cellValues.add(csvLine);
        }

        String[] row2 = csvLine.split(";");


        if(row2.length > 0)
            cellValues.add(row2[0].substring(0, row2[0].length() - 1));

        rowList.add(cellValues.toArray(new String[cellValues.size() - 1]));

        if (row2.length > 0 && row2[row2.length - 1].charAt(0) == '\"')
        {
            BindCells(row2, reader, rowList);
        }
        else
        {
            if(row2.length > 1)
            {
                for(int i = 1; i < row2.length; i++)
                    rowList.add(new String[] { row2[i] });
            }
        }
    }


}
