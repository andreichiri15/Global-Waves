package library.user.helper.notifications;

public interface Observer {
    /**
     * method that updates the observer
     * @param name the name of the notification
     * @param description the description of the notification
     */
    void update(String name, String description);
}
