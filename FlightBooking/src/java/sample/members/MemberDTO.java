/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.members;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author phamx
 */
public class MemberDTO implements Serializable {

    private int memberID;
    private String memberNumber;
    private String firstname;
    private String lastname;
    private LocalDate birthday;
    private String gender;
    private String email;
    private String phoneno;
    private String nationality;
    private String numberID;
    private String password;

    public MemberDTO() {
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNumberID() {
        return numberID;
    }

    public void setNumberID(String numberID) {
        this.numberID = numberID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
