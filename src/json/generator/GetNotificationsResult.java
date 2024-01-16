package json.generator;

import commands.InputCommands;

import library.user.helper.notifications.Notification;
import java.util.ArrayList;

public class GetNotificationsResult extends Result {
    private String user;
    private ArrayList<Notification> notifications;

    public GetNotificationsResult(final InputCommands inputCommand,
                                  final ArrayList<Notification> notifications) {
        this.command = inputCommand.getCommand();
        this.timestamp = inputCommand.getTimestamp();
        this.user = inputCommand.getUsername();
        this.notifications = notifications;
    }

    /**
     *
     * @return
     */
    public String getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(final String user) {
        this.user = user;
    }

    /**
     *
     * @return
     */
    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    /**
     *
     * @param notifications
     */
    public void setNotifications(final ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }
}
