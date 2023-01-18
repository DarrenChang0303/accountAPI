package com.example.demo.entity;

public class User {
    private String userName;
    private String passWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public User(String userName,String passWord){
        this.userName = userName;
        this.passWord = passWord;
    }

    @Override
    public boolean equals(Object obj) {
//        super.equals(obj);
        if(obj==null){
            return false;
        }
        if(!(obj instanceof User)){
            return false;
        }
        User other = (User)obj;
        if(other.passWord==null||other.userName==null){
            return false;
        }
        return this.userName.equals(other.userName)&& this.passWord.equals(other.passWord);
    }
}
