package com.hnb.hello.model;


// represent a simple chat message
// from user: chat user name
// message: content of the message
// isSeft: this is an incoming message or outgoing message
public class Message
{
    private String fromName, message;
    private boolean isSelf;

    public Message()
    {
    }

    public Message(String fromName, String message, boolean isSelf)
    {
        this.fromName = fromName;
        this.message = message;
        this.isSelf = isSelf;
    }

    public String getFromName()
    {
        return fromName;
    }

    public void setFromName(String fromName)
    {
        this.fromName = fromName;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public boolean isSelf()
    {
        return isSelf;
    }

    public void setSelf(boolean isSelf)
    {
        this.isSelf = isSelf;
    }

}
