import java.util.ArrayList;

public class User {
    //使用者，存储接受的消息和发送的消息的下标
    private String name;
    private ArrayList<String> recMessage;
    private ArrayList<String> sendMessage;

    public User() {
        recMessage = new ArrayList<>();
        sendMessage = new ArrayList<>();
    }

    public User(String name) {
        this.name = name;
        recMessage = new ArrayList<>();
        sendMessage = new ArrayList<>();
    }

    public ArrayList<String> getRecMessage() {
        return recMessage;
    }

    public ArrayList<String> getSendMessage() {
        return sendMessage;
    }

    public void setRecMessage(ArrayList<String> recMessage) {
        this.recMessage = recMessage;
    }

    public void setSendMessage(ArrayList<String> sendMessage) {
        this.sendMessage = sendMessage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
