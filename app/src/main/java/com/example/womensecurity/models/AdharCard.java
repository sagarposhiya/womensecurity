package com.example.womensecurity.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class AdharCard implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "uuid")
    private String uuid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "dateOfBirth")
    private String dateOfBirth;

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "careOf")
    private String careOf;

    @ColumnInfo(name = "district")
    private String district;

    @ColumnInfo(name = "landmark")
    private String landmark;

    @ColumnInfo(name = "house")
    private String house;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "pinCode")
    private String pinCode;

    @ColumnInfo(name = "postOffice")
    private String postOffice;

    @ColumnInfo(name = "state")
    private String state;

    @ColumnInfo(name = "street")
    private String street;

    @ColumnInfo(name = "subDistrict")
    private String subDistrict;

    @ColumnInfo(name = "vtc")
    private String vtc;

//    @ColumnInfo(name = "image")
//    private Bitmap image;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "mobile")
    private String mobile;

    @ColumnInfo(name = "signature")
    private String signature;


    public void setId(int id) {
        this.id = id;
    }

    public void setUuid(String aUuid){ name = aUuid; }
    public void setName(String aName){ name = aName; }
    public void setDateOfBirth(String aDateOfBirth){
        dateOfBirth = aDateOfBirth;
    }
    public void setGender(String aGender){
        gender = aGender;
    }
    public void setCareOf(String aCareOf){
        careOf = aCareOf;
    }
    public void setDistrict(String aDistrict){
        district = aDistrict;
    }
    public void setLandmark(String aLandmark){
        landmark = aLandmark;
    }
    public void setHouse(String aHouse){
        house = aHouse;
    }
    public void setLocation(String aLocation){ location = aLocation; }
    public void setPinCode(String aPinCode){
        pinCode = aPinCode;
    }
    public void setPostOffice(String aPostOffice){
        postOffice = aPostOffice;
    }
    public void setState(String aState){
        state = aState;
    }
    public void setStreet(String aStreet){
        street = aStreet;
    }
    public void setSubDistrict(String aSubDistrict){
        subDistrict = aSubDistrict;
    }
    public void setVtc(String aVtc){
        vtc = aVtc;
    }
    //    public void setImage(Bitmap aImage){
//        image = aImage;
//    }
    public void setEmail(String aEmail){
        email = aEmail;
    }
    public void setMobile(String aMobile){
        mobile = aMobile;
    }
    public void setSignature(String aSignature){
        signature = aSignature;
    }

    public AdharCard(){
        uuid = "";
        name = "";
        dateOfBirth = "";
        gender = "";
        careOf = "";
        district = "";
        landmark = "";
        house = "";
        location = "";
        pinCode = "";
        postOffice = "";
        state = "";
        street = "";
        subDistrict = "";
        vtc = "";
        email = "";
        mobile = "";
        signature = "";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getCareOf() {
        return careOf;
    }

    public String getDistrict() {
        return district;
    }

    public String getLandmark() {
        return landmark;
    }

    public String getHouse() {
        return house;
    }

    public String getLocation() {
        return location;
    }

    public String getPinCode() {
        return pinCode;
    }

    public String getPostOffice() {
        return postOffice;
    }

    public String getState() {
        return state;
    }

    public String getStreet() {
        return street;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public String getVtc() {
        return vtc;
    }

//    public Bitmap getImage() {
//        return image;
//    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getSignature() {
        return signature;
    }
}
