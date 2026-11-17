package websocket.messages;

public class NotificationMessage extends ServerMessage {

    private String notification = "";

    public NotificationMessage(ServerMessageType type, String error) {
        super(ServerMessageType.NOTIFICATION);
        this.notification = notification;
    }

    public String getError() {
        return notification;
    }
}
