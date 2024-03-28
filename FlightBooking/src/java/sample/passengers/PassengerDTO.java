/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.passengers;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author phamx
 */
public class PassengerDTO implements Serializable {

    private int passengerID;
    private String firstname;
    private String lastname;
    private LocalDate birthday;
    private String email;
    private String phoneno;
    private int memberID;

    public PassengerDTO() {
    }

    public int getPassengerID() {
        return passengerID;
    }

    public void setPassengerID(int passengerID) {
        this.passengerID = passengerID;
    }

    public String getFirstname() {
        return firstname.toUpperCase();
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname.toUpperCase();
    }

    public String getLastname() {
        return lastname.toUpperCase();
    }

    public void setLastname(String lastname) {
        this.lastname = lastname.toUpperCase();
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

}
