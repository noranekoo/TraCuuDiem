package com.anhthi.tracuudiem;

public class SinhVien {
    private String id;
    private String name;
    private String birthDay;
    private String homeTown;
    private String className;
    private String updateDate;
    public SinhVien(String id,String name, String birth,String home,String classname,String update){
        this.id = id;
        this.name = name;
        this.birthDay = birth;
        this.homeTown = home;
        this.className = classname;
        this.updateDate = update;
    }
    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
