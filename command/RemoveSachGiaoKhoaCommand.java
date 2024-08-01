package command;

import model.LibraryService;

public class RemoveSachGiaoKhoaCommand extends Command {
    private String maSach;

    public RemoveSachGiaoKhoaCommand(LibraryService service, String maSach) {
        this.service = service;
        this.maSach = maSach;
    }

    @Override
    public void execute() {
        service.deleteSachGK(maSach);
    }
}
