package model;

import java.util.Date;

public abstract class LibrarySach {
    protected String maSach;
    protected Date ngayNhap;
    protected double donGia;
    protected int soLuong;
    protected String nhaXuatBan;

    public LibrarySach(String maSach, Date ngayNhap, double donGia, int soLuong, String nhaXuatBan) {
        this.maSach = maSach;
        this.ngayNhap = ngayNhap;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.nhaXuatBan = nhaXuatBan;
    }

    // Getters and Setters
    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setNhaXuatBan(String nhaXuatBan) {
        this.nhaXuatBan = nhaXuatBan;
    }

    public String getMaSach() {
        return maSach;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public double getDonGia() {
        return donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public String getNhaXuatBan() {
        return nhaXuatBan;
    }

    public abstract double tinhThanhTien();
}
