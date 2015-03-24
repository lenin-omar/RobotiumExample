package com.example.lofm.personexample;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

/**
 * Created by Android1 on 3/8/2015.
 */
public class PersonRow implements Parcelable{

    /**
     * Constructor
     * @param in
     */
    public PersonRow(Parcel in) {
        String[] data = new String[7];
        this.perFirstName = data[0];
        this.perLastName = data[1];
        this.perGender = data[2];
        try{
            this.perHeight = new BigDecimal(data[3]);
        }catch(Exception e){
            e.printStackTrace();
            this.perHeight = BigDecimal.ZERO;
        }
        try{
            this.perWeight = new BigDecimal(data[4]);
        }catch(Exception e){
            e.printStackTrace();
            this.perWeight = BigDecimal.ZERO;
        }
        this.perCountry = data[5];
        this.perState = data[6];
        readFromParcel(in);
    }

    /**
     * Constructor
     */
    public PersonRow() {
        this.perFirstName = "";
        this.perLastName = "";
        this.perGender = "";
        this.perHeight = BigDecimal.ZERO;
        this.perWeight = BigDecimal.ZERO;
        this.perCountry = "";
        this.perState = "";
    }

    /**
     *
     */
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public PersonRow createFromParcel(Parcel in) {
            return new PersonRow(in);
        }

        @Override
        public PersonRow[] newArray(int size) {
            return new PersonRow[size];
        }
    };

    /**
     * Fields
     */
    private String perFirstName;
    private String perLastName;
    private String perGender;
    private BigDecimal perHeight;
    private BigDecimal perWeight;
    private String perCountry;
    private String perState;

    public String getPerFirstName() {
        return this.perFirstName;
    }
    public void setPerFirstName(String perFirstName) {
        this.perFirstName = perFirstName;
    }
    public String getPerLastName() {
        return perLastName;
    }
    public void setPerLastName(String perLastName) {
        this.perLastName = perLastName;
    }
    public String getPerGender() {
        return perGender;
    }
    public void setPerGender(String perGender) {
        this.perGender = perGender;
    }
    public BigDecimal getPerHeight() {
        return perHeight;
    }
    public void setPerHeight(BigDecimal perHeight) {
        this.perHeight = perHeight;
        this.perHeight.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
    public BigDecimal getPerWeight() {
        return perWeight;
    }
    public void setPerWeight(BigDecimal perWeight) {
        this.perWeight = perWeight;
        this.perWeight.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
    public String getPerCountry() {
        return perCountry;
    }
    public void setPerCountry(String perCountry) {
        this.perCountry = perCountry;
    }
    public String getPerState() {
        return perState;
    }
    public void setPerState(String perState) {
        this.perState = perState;
    }

    /**
     *
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     *
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        /*dest.writeStringArray(new String[] {
                dest.perFirstName,
                dest.perLastName,
                dest.perGender,
                dest.perHeight.toString(),
                dest.perWeight.toString(),
                dest.perCountry,
                dest.perState
        });*/
        dest.writeString(perFirstName);
        dest.writeString(perLastName);
        dest.writeString(perGender);
        dest.writeDouble(perHeight.doubleValue());
        dest.writeDouble(perWeight.doubleValue());
        dest.writeString(perCountry);
        dest.writeString(perState);
    }

    /**
     *
     * @param in
     */
    private void readFromParcel(Parcel in) {
        perFirstName = in.readString();
        perLastName = in.readString();
        perGender = in.readString();
        perHeight = new BigDecimal(in.readDouble());
        perHeight.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        perWeight = new BigDecimal(in.readDouble());
        perWeight.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        perCountry = in.readString();
        perState = in.readString();
    }
}
