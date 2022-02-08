package com.example.patronus;

public class HospitalData {

    private String mHospitalName;

    private String mLatitude;
    private String mLongitude;




    public HospitalData(String HospitalName, String latitude,String longitude){
        mHospitalName=HospitalName;
        mLatitude=latitude;
        mLongitude=longitude;
    }

    public String getmHospitalName(){
        return mHospitalName;
    }
    public  String getmLatitude(){
        return mLatitude;
    }
    public String getmLongitude(){
        return mLongitude;
    }

}
