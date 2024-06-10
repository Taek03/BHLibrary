package com.example.bhlibrary;

public class UserAccount {
    public UserAccount(){}

    public String getIdToken() {return idToken;}

    public void setIdToken(String idToken){
        this.idToken = idToken;
    }

    private String idToken;
    public String getEmailId() {return emailId;}

    public void setEmailId(String emailId){
        this.emailId = emailId;
    }

    private String emailId;

    public String getPassword() {return password;}

    public void setPassword(String password){
        this.password = password;
    }

    private String password;

    public String getName() {return Name;}

    public void setName(String Name){
        this.Name = Name;
    }

    private String Name;

    public String getBirth() {return Birth;}

    public void setBirth(String Birth){
        this.Birth = Birth;
    }

    private String Birth;

    public String getPhone() {return Phone;}

    public void setPhone(String Phone){
        this.Phone = Phone;
    }

    private String Phone;
}