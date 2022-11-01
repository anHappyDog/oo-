import java.util.ArrayList;

public class User {
    private long sensiNum;
    private String usrId;
    private ArrayList<Tweet> tweets;

    public User() {
        tweets = new ArrayList<>();
        sensiNum = 0;
    }

    public User(String usrId) {
        tweets = new ArrayList<>();
        setUsrId(usrId);
        sensiNum = 0;
    }

    public long getSensiNum() {
        return sensiNum;
    }

    public void setSensiNum(long sensiNum) {
        this.sensiNum = sensiNum;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public String getUsrId() {
        return usrId;
    }

    public ArrayList<Tweet> getTweets() {
        return tweets;
    }
}
