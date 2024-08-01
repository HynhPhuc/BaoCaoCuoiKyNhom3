package presentation;

import java.sql.SQLException;
import java.util.List;

import command.AddSachGiaoKhoaCommand;
import command.AddSachThamKhaoCommand;
import command.Command;
import command.RemoveSachGiaoKhoaCommand;
import command.RemoveSachThamKhaoCommand;
import command.UpdateSachGiaoKhoaCommand;
import command.UpdateSachThamKhaoCommand;
import model.LibrarySachGiaoKhoa;
import model.LibrarySachThamKhao;
import model.LibraryServiceImpl;

public class LibraryController {
    private LibraryServiceImpl serviceImpl;
    private Command command;

    public LibraryController() throws SQLException // xử lý ngoại lệ xảy ra khi có vấn đề liên quan đến cơ sở dữ liệu
    {
        // khai báo và khởi tạo đối tượng tham chiếu
        serviceImpl = new LibraryServiceImpl();
    }

    public LibraryServiceImpl getServiceImpl() {
        return serviceImpl;
    }

    public void addSachGiaoKhoa(LibrarySachGiaoKhoa sach) {
        command = new AddSachGiaoKhoaCommand(serviceImpl, sach);
        command.execute();
    }

    public void addSachThamKhao(LibrarySachThamKhao sach) {
        command = new AddSachThamKhaoCommand(serviceImpl, sach);
        command.execute();
    }

    public void updateSachGiaoKhoa(LibrarySachGiaoKhoa sach) {
        command = new UpdateSachGiaoKhoaCommand(serviceImpl, sach);
        command.execute();
    }

    public void updateSachThamKhao(LibrarySachThamKhao sach) {
        command = new UpdateSachThamKhaoCommand(serviceImpl, sach);
        command.execute();
    }

    public void removeSachGiaoKhoa(String maSach) {
        command = new RemoveSachGiaoKhoaCommand(serviceImpl, maSach);
        command.execute();
    }

    public void removeSachThamKhao(String maSach) {
        command = new RemoveSachThamKhaoCommand(serviceImpl, maSach);
        command.execute();
    }

    public double tinhTrungBinhDonGiaSachGiaoKhoa() {
        return serviceImpl.tinhTrungBinhDonGiaSachGiaoKhoa();
    }

    public double tinhTrungBinhDonGiaSachThamKhao() {
        return serviceImpl.tinhTrungBinhDonGiaSachThamKhao();
    }

    public List<LibrarySachGiaoKhoa> getAllSachGiaoKhoa() {
        return serviceImpl.getAllSachGiaoKhoa();
    }

    public List<LibrarySachThamKhao> getAllSachThamKhao() {
        return serviceImpl.getAllSachThamKhao();
    }

}
