package json.generator;

import commands.InputCommands;

import library.user.helper.notifications.Notification;
import java.util.ArrayList;

public class GetNotificationsResult extends Result {
    private String user;
    private ArrayList<Notification> notifications;

    public GetNotificationsResult(InputCommands inputCommand, ArrayList<Notification> notifications) {
        this.command = inputCommand.getCommand();
        this.timestamp = inputCommand.getTimestamp();
        this.user = inputCommand.getUsername();
        this.notifications = notifications;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }
}
