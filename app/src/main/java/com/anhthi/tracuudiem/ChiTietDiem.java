package com.anhthi.tracuudiem;

public class ChiTietDiem {
    private int hocki;
    private int stt;
    private String tenMon;
    private String donViHP;
    private String diem;
    private String ghiChu;

    public ChiTietDiem(int hocki,int stt, String tenMon, String dvhp, String diem) {
        this.hocki = hocki;
        this.stt = stt;
        this.tenMon = tenMon;
        this.donViHP = dvhp;
        this.diem = diem;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getDiem() {
        return diem;
    }

    public void setDiem(String diem) {
        this.diem = diem;
    }

    public String getDonViHP() {
        return donViHP;
    }

    public void setDonViHP(String donViHP) {
        this.donViHP = donViHP;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public int getHocki() {
        return hocki;
    }

    public void setHocki(int hocki) {
        this.hocki = hocki;
    }
}
