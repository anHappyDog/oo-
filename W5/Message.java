
public class Message {
    private String message;
    private int []data;
    private String giver;
    private String receiver;

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGiver() {
        return giver;
    }

    public void setGiver(String giver) {
        this.giver = giver;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Message() {

    }

    public Message(String message,int []data,String giver, String receiver) {
        this.message = message;
        this.data = new int[3];
        this.giver = giver;
        this.receiver = receiver;
        System.arraycopy(data, 0, (this.data), 0, 3);
    }
}
