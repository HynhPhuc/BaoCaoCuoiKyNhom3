package command;

import model.LibraryService;

public class RemoveSachThamKhaoCommand extends Command {
    private String maSach;

    public RemoveSachThamKhaoCommand(LibraryService service, String maSach) {
        this.service = service;
        this.maSach = maSach;
    }

    @Override
    public void execute() {
        service.deleteSachTK(maSach);
    }
}
