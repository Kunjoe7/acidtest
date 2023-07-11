package com.model;

public class ActestManager {

    private int actestManagerId;
    private String userName;
    private String password;
    private int actestPlaceId;
    private String actestPlaceName;
    private String name;
    private String sex;
    private String tel;

    public ActestManager() {

    }

    public ActestManager(String userName, String password,
                       String name, String sex, String tel) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.tel = tel;
    }



    public ActestManager(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }


    public int getActestManagerId() {
        return actestManagerId;
    }
    public void setActestManagerId(int actestManagerId) {
        this.actestManagerId = actestManagerId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int getActestPlaceId() {
        return actestPlaceId;
    }
    public void setActestPlaceId(int actestPlaceId) {
        this.actestPlaceId = actestPlaceId;
    }

    public String getActestPlaceName() {
        return actestPlaceName;
    }


    public void setActestPlaceName(String actestPlaceName) {
        this.actestPlaceName = actestPlaceName;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }


}
