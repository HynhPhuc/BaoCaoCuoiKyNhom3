package model;

import java.util.Date;

public class LibrarySachGiaoKhoa extends LibrarySach {
    private String tinhTrang;

    public LibrarySachGiaoKhoa(String maSach, Date ngayNhap, double donGia, int soLuong, String nhaXuatBan,
            String tinhTrang) {
        super(maSach, ngayNhap, donGia, soLuong, nhaXuatBan);
        this.tinhTrang = tinhTrang;
    }

    @Override
    public double tinhThanhTien() {
        if ("moi".equalsIgnoreCase(tinhTrang)) {
            return soLuong * donGia;
        } else {
            return soLuong * donGia * 0.5;
        }
    }

    // Getters and Setters
    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

}
