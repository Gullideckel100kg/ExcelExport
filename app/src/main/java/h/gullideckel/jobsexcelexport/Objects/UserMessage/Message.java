package h.gullideckel.jobsexcelexport.Objects.UserMessage;

public class Message
{
    private String title;
    private String message;
    private boolean isHidden;
    private int position = 0;



    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public boolean isHidden()
    {
        return isHidden;
    }

    public void setHidden(boolean hidden)
    {
        isHidden = hidden;
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
