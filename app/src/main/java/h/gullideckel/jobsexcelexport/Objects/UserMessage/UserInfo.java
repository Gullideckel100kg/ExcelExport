package h.gullideckel.jobsexcelexport.Objects.UserMessage;

import java.util.ArrayList;
import java.util.List;

public class UserInfo
{
    private String userId = "";
    private boolean isAd = true;
    private List<Message> messages;
    private String userName;

    public String getEmail()
    {
        return userName;
    }

    public void setEmail(String email)
    {
        this.userName = email;
    }

    public UserInfo()
    {
        messages = new ArrayList<>();
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public boolean isAd()
    {
        return isAd;
    }

    public void setAd(boolean ad)
    {
        isAd = ad;
    }

    public List<Message> getMessages()
    {
        return messages;
    }

    public void setMessages(List<Message> messages)
    {
        this.messages = messages;
    }
}

