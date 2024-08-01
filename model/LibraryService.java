package model;

import java.util.List;

public interface LibraryService {

    void addSachGK(LibrarySachGiaoKhoa sach);

    void addSachTK(LibrarySachThamKhao sach);

    void updateSachGK(LibrarySachGiaoKhoa sach);

    void updateSachTK(LibrarySachThamKhao sach);

    void deleteSachGK(String maSach);

    void deleteSachTK(String maSach);

    LibrarySachGiaoKhoa timSachGiaoKhoaTheoMa(String maSach);

    LibrarySachThamKhao timSachThamKhaoTheoMa(String maSach);

    LibrarySachGiaoKhoa xuatRaGiaoKhoaTheoNXB(String nhaXuatBan);

    LibrarySachThamKhao xuatRaThamKhaoTheoNXB(String nhaXuatBan);

    double tinhTrungBinhDonGiaSachGiaoKhoa();

    double tinhTrungBinhDonGiaSachThamKhao();

    List<LibrarySachGiaoKhoa> getAllSachGiaoKhoa();

    List<LibrarySachThamKhao> getAllSachThamKhao();
}
