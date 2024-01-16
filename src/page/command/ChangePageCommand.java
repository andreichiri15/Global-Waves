package page.command;

import library.User;
import page.Page;

public class ChangePageCommand implements Command {
    private Page nextPage;
    private Page previousPage;
    private Page undonePage;
    private User user;

    public ChangePageCommand(final User user, final Page page) {
        this.user = user;
        this.previousPage = user.getCurrentPage();
        this.nextPage = page;
    }

    /**
     * Executes the command (changes the current page of the user)
     */
    public void execute() {
        user.setCurrentPage(nextPage);
    }

    /**
     * Undoes the command (changes the current page of the user to the previous one)
     */
    public void undo() {
        user.setCurrentPage(previousPage);
        undonePage = nextPage;
    }

    /**
     * Redoes the command (changes the current page of the user to the next one)
     */
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

    /**
     *
     * @return
     */
    public Page getUndonePage() {
        return undonePage;
    }

    /**
     *
     * @param undonePage
     */
    public void setUndonePage(final Page undonePage) {
        this.undonePage = undonePage;
    }

    /**
     *
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(final User user) {
        this.user = user;
    }
}
