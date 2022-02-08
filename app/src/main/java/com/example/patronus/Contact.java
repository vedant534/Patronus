package com.example.patronus;

public class Contact {
    private String mName,mNumber;
    long mId;

    public Contact(String Name,String Number)
    {
        mName = Name;
        mNumber = Number;
    }

    public String getName()
    {
        return mName;
    }

    public String getNumber()
    {
        return mNumber;
    }

}
