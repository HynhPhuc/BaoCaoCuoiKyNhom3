package persistence;

import java.util.List;

import model.LibrarySachGiaoKhoa;
import model.LibrarySachThamKhao;

public interface LibraryPersistence {

    void addSachGK(LibrarySachGiaoKhoa sach);

    void addSachTK(LibrarySachThamKhao sach);

    void updateSachGK(LibrarySachGiaoKhoa sach);

    void updateSachTK(LibrarySachThamKhao sach);

    void deleteSachGK(String maSach);

    void deleteSachTK(String maSach);

    LibrarySachGiaoKhoa findSachGiaoKhoaByMa(String maSach);

    LibrarySachThamKhao findSachThamKhaoByMa(String maSach);

    LibrarySachGiaoKhoa exportSachGiaoKhoaByNXB(String nhaXuatBan);

    LibrarySachThamKhao exportSachThamKhaoByNXB(String nhaXuatBan);

    double tinhTrungBinhDonGiaSachGiaoKhoa();

    double tinhTrungBinhDonGiaSachThamKhao();

    List<LibrarySachGiaoKhoa> getAllSachGiaoKhoa();

    List<LibrarySachThamKhao> getAllSachThamKhao();

}
