package com.model;


public class ActestPlace {
    private int actestPlaceId;
    private String actestPlaceName;
    private String detail;


    public ActestPlace() {
    }

    public ActestPlace(String actestPlaceName, String detail) {
        this.actestPlaceName = actestPlaceName;
        this.detail = detail;
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
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }


}