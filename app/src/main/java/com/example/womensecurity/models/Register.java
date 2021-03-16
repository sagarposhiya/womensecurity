package com.example.womensecurity.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Register {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "firstName")
    String firstName;

    @ColumnInfo(name = "lastName")
    String lastName;

    @ColumnInfo(name = "address")
    String address;

    @ColumnInfo(name = "mobile")
    String mobile;

    @ColumnInfo(name = "email")
    String email;

    @ColumnInfo(name = "emer1")
    String emer1;

    @ColumnInfo(name = "emer2")
    String emer2;

    @ColumnInfo(name = "emer3")
    String emer3;

    @ColumnInfo(name = "date")
    String date;

//    @ColumnInfo(name = "password")
//    String password;

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setAddress(String address) { this.address = address; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public void setEmail(String email) { this.email = email; }
    public void setEmer1(String emer1) { this.emer1 = emer1; }
    public void setEmer2(String emer2) { this.emer2 = emer2; }
    public void setEmer3(String emer3) { this.emer3 = emer3; }
    public void setDate(String date) {
        this.date = date;
    }
//    public void setPassword(String password) { this.password = password; }

    public Register(){
        firstName= "";
        lastName= "";
        address= "";
        mobile= "";
        email= "";
        emer1= "";
        emer2= "";
        emer3= "";
         date = "";
        //password= "";
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getAddress() { return address; }
    public String getMobile() { return mobile; }
    public String getEmail() { return email; }
    public String getEmer1() { return emer1; }
    public String getEmer2() { return emer2; }
    public String getEmer3() { return emer3; }
    public String getDate() {
        return date;
    }
}
