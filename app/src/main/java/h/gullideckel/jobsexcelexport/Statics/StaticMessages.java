package h.gullideckel.jobsexcelexport.Statics;

import android.content.Context;
import android.widget.Toast;

public class StaticMessages
{
    public static void Toast(String message, Context context)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
