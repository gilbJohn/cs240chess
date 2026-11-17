package websocket.messages;

public class ErrorMessage extends ServerMessage{

    private final String error;

    public ErrorMessage(ServerMessageType type, String error) {
        super(ServerMessageType.ERROR);
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
