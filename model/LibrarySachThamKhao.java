package model;

import java.util.Date;

public class LibrarySachThamKhao extends LibrarySach {
    private double thue;

    public LibrarySachThamKhao(String maSach, Date ngayNhap, double donGia, int soLuong, String nhaXuatBan,
            double thue) {
        super(maSach, ngayNhap, donGia, soLuong, nhaXuatBan);
        this.thue = thue;
    }

    @Override
    public double tinhThanhTien() {
        return soLuong * donGia + thue;
    }

    // Getters and Setters
    public double getThue() {
        return thue;
    }

    public void setThue(double thue) {
        this.thue = thue;
    }

}
