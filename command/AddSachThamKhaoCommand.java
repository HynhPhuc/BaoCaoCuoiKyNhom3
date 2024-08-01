package command;

import model.LibrarySachThamKhao;
import model.LibraryService;

public class AddSachThamKhaoCommand extends Command {
    private LibrarySachThamKhao sach;

    public AddSachThamKhaoCommand(LibraryService service, LibrarySachThamKhao sach) {
        this.service = service;
        this.sach = sach;
    }

    @Override
    public void execute() {
        service.addSachTK(sach);
    }
}
