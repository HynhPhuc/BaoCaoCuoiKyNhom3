package command;

import model.LibrarySachThamKhao;
import model.LibraryService;

public class UpdateSachThamKhaoCommand extends Command {
    private LibrarySachThamKhao sach;

    public UpdateSachThamKhaoCommand(LibraryService service, LibrarySachThamKhao sach) {
        this.service = service;
        this.sach = sach;
    }

    @Override
    public void execute() {
        service.updateSachTK(sach);
    }
}
