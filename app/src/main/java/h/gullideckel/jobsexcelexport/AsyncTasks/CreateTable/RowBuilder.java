package h.gullideckel.jobsexcelexport.AsyncTasks.CreateTable;

import android.content.Context;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RowBuilder
{
    private Context context;

    public RowBuilder(Context context)
    {
        this.context = context;
    }
    public List<TableRow> CreateTable(List<String[][]> csvTable)
    {
        int rowCount = 1;
        List<TableRow> rows = new ArrayList<>();
        for(String[][] csvRow : csvTable)
        {
            final TableRow row = new TableRow(context);
            row.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if(csvRow.length > 0 && csvTable.get(0) != csvRow)
            {
                row.addView(BuildTextCell(String.valueOf(rowCount), 8, false));
                rowCount++;
            }
            if(csvTable.get(0) == csvRow)
                row.addView(BuildTextCell("Nr.", 8, true));

            for(String[] csvCell : csvRow)
            {
                String entry = "";
                if(csvCell.length > 0 && csvCell.length > 1)
                {
                    StringBuilder bldEntry = new StringBuilder();
                    bldEntry.append(csvCell[0]);
                    for(int i = 1; i < csvCell.length; i++)
                    {
                        bldEntry.append("\n" + csvCell[i]);
                    }
                    entry = bldEntry.toString();
                }
                else if(csvCell.length > 0)
                    entry = csvCell[0];

                if(csvRow == csvTable.get(0))
                    row.addView(BuildTextCell(entry, 8, true));
                else
                    row.addView(BuildTextCell(entry, 8, false));
            }
            rows.add(row);
        }
        return rows;
    }

    private TextView BuildTextCell(String text, int padding, boolean isBold)
    {
        TextView cell = new TextView(context);
        if(isBold)
            cell.setTypeface(null, Typeface.BOLD);
        cell.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        cell.setPadding(padding, padding, padding, padding);
        cell.setText(text);
        return  cell;
    }
}
