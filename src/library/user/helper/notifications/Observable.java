package library.user.helper.notifications;

public interface Observable {
    /**
     * method that adds an observer to the list of subscribers of the observable
     * @param observer the observer to be added
     */
    void addSubscriber(Observer observer);

    /**
     *  method that removes an observer from the list of subscribers of the observable
     * @param observer the observer to be removed
     */
    void removeSubscriber(Observer observer);

    /**
     * method that notifies all the subscribers of the observable
     * @param name the name of the notification
     * @param description the description of the notification
     */
    void notifySubscribers(String name, String description);
}
