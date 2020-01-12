package com.example.reconcile.repository;

public class Friend {
    private String name;
    private String statu;
    private int userid;
    private String email;
    public Friend (int userid, String name ,String email, String statu){
        this.name = name;
        this.statu = statu;
        this.email = email;
        this.userid = userid;
    }
    public String getName(){
        return name;
    }
    public void setNmae(String newname){
        this.name = newname;
    }
    public String getStatu(){
        return statu;
    }
    public void setStatu(String newstatu){
        this.name = newstatu;
    }
    public int getUserid(){
        return userid;
    }
    public void setUserid(int id){
        this.userid = id;
    }
    public String getUseremail(){
        return email;
    }
    public void setUseremail(String email){
        this.email = email;
    }
}
