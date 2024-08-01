package model;

import java.sql.SQLException;
import java.util.List;
import observer.Publisher;
import persistence.LibraryPersisImpl;
import persistence.LibraryPersistence;

public class LibraryServiceImpl extends Publisher implements LibraryService {
    private LibraryPersistence persistence;

    public LibraryServiceImpl() throws SQLException // xử lý ngoại lệ xảy ra khi có vấn đề liên quan đến cơ sở dữ liệu
    {
        persistence = new LibraryPersisImpl();// khai báo và khởi tạo đối tượng tham chiếu
    }

    @Override
    public List<LibrarySachGiaoKhoa> getAllSachGiaoKhoa() {
        return persistence.getAllSachGiaoKhoa();
    }

    @Override
    public List<LibrarySachThamKhao> getAllSachThamKhao() {
        return persistence.getAllSachThamKhao();
    }

    @Override
    public void addSachGK(LibrarySachGiaoKhoa sach) {
        persistence.addSachGK(sach);
        notifySubscribers();
    }

    @Override
    public void addSachTK(LibrarySachThamKhao sach) {
        persistence.addSachTK(sach);
        notifySubscribers();
    }

    @Override
    public void updateSachGK(LibrarySachGiaoKhoa sach) {
        persistence.updateSachGK(sach);
        notifySubscribers();
    }

    @Override
    public void updateSachTK(LibrarySachThamKhao sach) {
        persistence.updateSachTK(sach);
        notifySubscribers();
    }

    @Override
    public void deleteSachGK(String maSach) {
        persistence.deleteSachGK(maSach);
        notifySubscribers();
    }

    @Override
    public void deleteSachTK(String maSach) {
        persistence.deleteSachTK(maSach);
        notifySubscribers();
    }

    @Override
    public LibrarySachGiaoKhoa timSachGiaoKhoaTheoMa(String maSach) {
        return persistence.findSachGiaoKhoaByMa(maSach);
    }

    @Override
    public LibrarySachThamKhao timSachThamKhaoTheoMa(String maSach) {
        return persistence.findSachThamKhaoByMa(maSach);
    }

    @Override
    public LibrarySachGiaoKhoa xuatRaGiaoKhoaTheoNXB(String nhaXuatBan) {
        return persistence.exportSachGiaoKhoaByNXB(nhaXuatBan);
    }

    @Override
    public LibrarySachThamKhao xuatRaThamKhaoTheoNXB(String nhaXuatBan) {
        return persistence.exportSachThamKhaoByNXB(nhaXuatBan);
    }

    @Override
    public double tinhTrungBinhDonGiaSachGiaoKhoa() {
        return persistence.tinhTrungBinhDonGiaSachGiaoKhoa();
    }

    @Override
    public double tinhTrungBinhDonGiaSachThamKhao() {
        return persistence.tinhTrungBinhDonGiaSachThamKhao();
    }

}
