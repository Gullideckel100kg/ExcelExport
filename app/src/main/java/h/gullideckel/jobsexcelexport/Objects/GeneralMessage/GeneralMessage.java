package h.gullideckel.jobsexcelexport.Objects.GeneralMessage;

import java.util.ArrayList;
import java.util.List;

public class GeneralMessage
{
    private String title;
    private String message;
    private List<String> userNotShown = new ArrayList<>();
    private int position;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public List<String> getUserNotShown()
    {
        return userNotShown;
    }

    public void setUserNotShown(List<String> userNotShown)
    {
        this.userNotShown = userNotShown;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }
}
