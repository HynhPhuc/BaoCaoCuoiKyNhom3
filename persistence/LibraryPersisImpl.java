package persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.DatabaseConnection;
import model.LibrarySachGiaoKhoa;
import model.LibrarySachThamKhao;

import java.util.Date;

public class LibraryPersisImpl implements LibraryPersistence {
    private Connection connection;

    public LibraryPersisImpl() throws SQLException// xử lý ngoại lệ xảy ra khi có vấn đề liên quan đến cơ sở dữ liệu
    {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void addSachGK(LibrarySachGiaoKhoa sach) {
        String sql = "INSERT INTO SachGiaoKhoa (MaSach, NgayNhap, DonGia, SoLuong, NhaXuatBan, TinhTrang) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preSaveSGK = connection.prepareStatement(sql)) {
            preSaveSGK.setString(1, sach.getMaSach());
            preSaveSGK.setDate(2, new java.sql.Date(sach.getNgayNhap().getTime()));
            preSaveSGK.setDouble(3, sach.getDonGia());
            preSaveSGK.setInt(4, sach.getSoLuong());
            preSaveSGK.setString(5, sach.getNhaXuatBan());
            preSaveSGK.setString(6, sach.getTinhTrang());
            preSaveSGK.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addSachTK(LibrarySachThamKhao sach) {
        String sql = "INSERT INTO SachThamKhao (MaSach, NgayNhap, DonGia, SoLuong, NhaXuatBan, Thue) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preSaveSTK = connection.prepareStatement(sql)) {
            preSaveSTK.setString(1, sach.getMaSach());
            preSaveSTK.setDate(2, new java.sql.Date(sach.getNgayNhap().getTime()));
            preSaveSTK.setDouble(3, sach.getDonGia());
            preSaveSTK.setInt(4, sach.getSoLuong());
            preSaveSTK.setString(5, sach.getNhaXuatBan());
            preSaveSTK.setDouble(6, sach.getThue());
            preSaveSTK.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateSachGK(LibrarySachGiaoKhoa sach) {
        String sql = "UPDATE SachGiaoKhoa SET NgayNhap = ?, DonGia = ?, SoLuong = ?, NhaXuatBan = ?, TinhTrang = ? WHERE MaSach = ?";
        try (PreparedStatement preUpdateSGK = connection.prepareStatement(sql)) {
            preUpdateSGK.setString(1, sach.getMaSach());
            preUpdateSGK.setDate(2, new java.sql.Date(sach.getNgayNhap().getTime()));
            preUpdateSGK.setDouble(3, sach.getDonGia());
            preUpdateSGK.setInt(4, sach.getSoLuong());
            preUpdateSGK.setString(5, sach.getNhaXuatBan());
            preUpdateSGK.setString(6, sach.getTinhTrang());
            preUpdateSGK.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateSachTK(LibrarySachThamKhao sach) {
        String sql = "UPDATE SachThamKhao SET NgayNhap = ?, DonGia = ?, SoLuong = ?, NhaXuatBan = ?, Thue = ? WHERE MaSach = ?";
        try (PreparedStatement preUpdateSTK = connection.prepareStatement(sql)) {
            preUpdateSTK.setString(1, sach.getMaSach());
            preUpdateSTK.setDate(2, new java.sql.Date(sach.getNgayNhap().getTime()));
            preUpdateSTK.setDouble(3, sach.getDonGia());
            preUpdateSTK.setInt(4, sach.getSoLuong());
            preUpdateSTK.setString(5, sach.getNhaXuatBan());
            preUpdateSTK.setDouble(6, sach.getThue());
            preUpdateSTK.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteSachGK(String maSach) {
        String sql = "DELETE FROM SachGiaoKhoa WHERE MaSach = ?";
        try (PreparedStatement preDeleteSGK = connection.prepareStatement(sql)) {
            preDeleteSGK.setString(1, maSach);
            preDeleteSGK.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteSachTK(String maSach) {
        String sql = "DELETE FROM SachThamKhao WHERE MaSach = ?";
        try (PreparedStatement preDeleteSTK = connection.prepareStatement(sql)) {
            preDeleteSTK.setString(1, maSach);
            preDeleteSTK.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public LibrarySachGiaoKhoa findSachGiaoKhoaByMa(String maSach) {
        try {
            String query = "SELECT * FROM SachGiaoKhoa WHERE maSach = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, maSach);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Date ngayNhap = resultSet.getDate("ngayNhap");
                double donGia = resultSet.getDouble("donGia");
                int soLuong = resultSet.getInt("soLuong");
                String nhaXuatBan = resultSet.getString("nhaXuatBan");
                String tinhTrang = resultSet.getString("tinhTrang");
                return new LibrarySachGiaoKhoa(maSach, ngayNhap, donGia, soLuong, nhaXuatBan, tinhTrang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LibrarySachThamKhao findSachThamKhaoByMa(String maSach) {
        try {
            String query = "SELECT * FROM SachThamKhao WHERE maSach = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, maSach);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Date ngayNhap = resultSet.getDate("ngayNhap");
                double donGia = resultSet.getDouble("donGia");
                int soLuong = resultSet.getInt("soLuong");
                String nhaXuatBan = resultSet.getString("nhaXuatBan");
                double thue = resultSet.getDouble("thue");

                return new LibrarySachThamKhao(maSach, ngayNhap, donGia, soLuong, nhaXuatBan, thue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LibrarySachGiaoKhoa exportSachGiaoKhoaByNXB(String nhaXuatBan) {
        try {
            String query = "SELECT * FROM SachGiaoKhoa WHERE NhaXuatBan = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nhaXuatBan);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String maSach = resultSet.getString("maSach");
                Date ngayNhap = resultSet.getDate("ngayNhap");
                double donGia = resultSet.getDouble("donGia");
                int soLuong = resultSet.getInt("soLuong");
                String tinhTrang = resultSet.getString("tinhTrang");
                return new LibrarySachGiaoKhoa(maSach, ngayNhap, donGia, soLuong, nhaXuatBan, tinhTrang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LibrarySachThamKhao exportSachThamKhaoByNXB(String nhaXuatBan) {
        try {
            String query = "SELECT * FROM SachThamKhao WHERE NhaXuatBan = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nhaXuatBan);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String maSach = resultSet.getString("maSach");
                Date ngayNhap = resultSet.getDate("ngayNhap");
                double donGia = resultSet.getDouble("donGia");
                int soLuong = resultSet.getInt("soLuong");
                double thue = resultSet.getDouble("thue");

                return new LibrarySachThamKhao(maSach, ngayNhap, donGia, soLuong, nhaXuatBan, thue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<LibrarySachGiaoKhoa> getAllSachGiaoKhoa() {
        List<LibrarySachGiaoKhoa> sachGiaoKhoaList = new ArrayList<>();
        try {
            String query = "SELECT * FROM SachGiaoKhoa";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String maSach = resultSet.getString("maSach");
                Date ngayNhap = resultSet.getDate("ngayNhap");
                double donGia = resultSet.getDouble("donGia");
                int soLuong = resultSet.getInt("soLuong");
                String nhaXuatBan = resultSet.getString("nhaXuatBan");
                String tinhTrang = resultSet.getString("tinhTrang");

                LibrarySachGiaoKhoa sach = new LibrarySachGiaoKhoa(maSach, ngayNhap, donGia, soLuong, nhaXuatBan,
                        tinhTrang);
                sachGiaoKhoaList.add(sach);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sachGiaoKhoaList;
    }

    @Override
    public List<LibrarySachThamKhao> getAllSachThamKhao() {
        List<LibrarySachThamKhao> sachThamKhaoList = new ArrayList<>();
        try {
            String query = "SELECT * FROM SachThamKhao";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String maSach = resultSet.getString("maSach");
                Date ngayNhap = resultSet.getDate("ngayNhap");
                double donGia = resultSet.getDouble("donGia");
                int soLuong = resultSet.getInt("soLuong");
                String nhaXuatBan = resultSet.getString("nhaXuatBan");
                double thue = resultSet.getDouble("thue");

                LibrarySachThamKhao sach = new LibrarySachThamKhao(maSach, ngayNhap, donGia, soLuong, nhaXuatBan, thue);
                sachThamKhaoList.add(sach);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sachThamKhaoList;
    }

    @Override
    public double tinhTrungBinhDonGiaSachGiaoKhoa() {
        String sql = "SELECT AVG(donGia) AS avgDonGia FROM SachGiaoKhoa";

        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getDouble("avgDonGia");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    @Override
    public double tinhTrungBinhDonGiaSachThamKhao() {
        String sql = "SELECT AVG(donGia) AS avgDonGia FROM SachThamKhao";

        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getDouble("avgDonGia");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
    }

}
