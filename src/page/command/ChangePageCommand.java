package page.command;

import library.User;
import page.Page;

public class ChangePageCommand implements Command {
    private Page nextPage;
    private Page previousPage;
    private User user;


    public ChangePageCommand(User user, Page page) {
        this.user = user;
        this.previousPage = user.getCurrentPage();
        this.nextPage = page;
    }
    public void execute() {
        previousPage = nextPage;
        user.setCurrentPage(nextPage);
    }

    public void undo() {
        nextPage = previousPage;
        previousPage = user.getCurrentPage();
        user.setCurrentPage(nextPage);
    }

    public void redo() {

    }
}
