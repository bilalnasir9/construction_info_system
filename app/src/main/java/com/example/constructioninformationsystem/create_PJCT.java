package com.example.constructioninformationsystem;

public class create_PJCT {
    String projecttype,location,owner;
    create_PJCT(String type,String location,String owner){
       this.projecttype=type;
       this.location=location;
       this.owner=owner;
    }

    public String getLocation() {
        return location;
    }

    public String getOwner() {
        return owner;
    }
    public String getProjecttype() {
        return projecttype;
    }




}
