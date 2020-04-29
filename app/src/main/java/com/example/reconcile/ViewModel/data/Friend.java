package com.example.reconcile.ViewModel.data;

public class Friend {
    private String userName;
    private String userStatus;
    private String userEmail;
    public Friend (String userName ,String userEmail, String userStatus){
        this.userName = userName;
        this.userStatus = userStatus;
        this.userEmail = userEmail;

    }
    public Friend(){

    }
    public String getuserName(){
        return userName;
    }
    public void setuserName(String newname){
        this.userName = newname;
    }
    public String getuserStatus(){
        return userStatus;
    }
    public void setuserStatus(String newstatu){
        this.userName = newstatu;
    }

    public String getUseremail(){
        return userEmail;
    }
    public void setUseremail(String email){
        this.userEmail = email;
    }
}
