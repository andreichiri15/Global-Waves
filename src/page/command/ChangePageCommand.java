package page.command;

import library.User;
import page.Page;

import java.util.LinkedList;

public class ChangePageCommand implements Command {
    private Page nextPage;
    private Page previousPage;
    private Page undonePage;
    private User user;

    public ChangePageCommand(User user, Page page) {
        this.user = user;
        this.previousPage = user.getCurrentPage();
        this.nextPage = page;
    }
    public void execute() {
        user.setCurrentPage(nextPage);
    }

    public void undo() {
        user.setCurrentPage(previousPage);
        undonePage = nextPage;
    }

    public void redo() {
        if (undonePage != null) {
            user.setCurrentPage(undonePage);
        }
    }

    /**
     *
     * @return
     */
    public Page getNextPage() {
        return nextPage;
    }

    /**
     *
     * @param nextPage
     */
    public void setNextPage(final Page nextPage) {
        this.nextPage = nextPage;
    }

    /**
     *
     * @return
     */
    public Page getPreviousPage() {
        return previousPage;
    }

    /**
     *
     * @param previousPage
     */
    public void setPreviousPage(final Page previousPage) {
        this.previousPage = previousPage;
    }

    public Page getUndonePage() {
        return undonePage;
    }

    public void setUndonePage(Page undonePage) {
        this.undonePage = undonePage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
