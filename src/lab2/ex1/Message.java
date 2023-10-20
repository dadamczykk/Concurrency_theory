package lab2.ex1;

public class Message {
    int senderId;
    int messageId;
    String message;

    public Message(int senderId, int messageId, String message){
        this.senderId = senderId;
        this.messageId = messageId;
        this.message = message;
    }

    @Override
    public String toString() {
        return "senderId=" + senderId +
               ", messageId=" + messageId +
               ", message='" + message + '\'' +
               '}';
    }
}
