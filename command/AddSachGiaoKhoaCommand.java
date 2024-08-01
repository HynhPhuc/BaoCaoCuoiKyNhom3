package command;

import model.LibrarySachGiaoKhoa;
import model.LibraryService;

public class AddSachGiaoKhoaCommand extends Command {
    private LibrarySachGiaoKhoa sach;

    public AddSachGiaoKhoaCommand(LibraryService service, LibrarySachGiaoKhoa sach) {
        this.service = service;
        this.sach = sach;
    }

    @Override
    public void execute() {
        service.addSachGK(sach);
    }
}
