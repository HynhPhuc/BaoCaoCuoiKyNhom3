package command;

import model.LibrarySachGiaoKhoa;
import model.LibraryService;

public class UpdateSachGiaoKhoaCommand extends Command {
    private LibrarySachGiaoKhoa sach;

    public UpdateSachGiaoKhoaCommand(LibraryService service, LibrarySachGiaoKhoa sach) {
        this.service = service;
        this.sach = sach;
    }

    @Override
    public void execute() {
        service.updateSachGK(sach);
    }
}
