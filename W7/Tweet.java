import java.util.ArrayList;

public class Tweet {
    private int[] createdTime;
    private String usrId;
    private String fulText;
    private String twId;
    private long retCnt;
    private long favoCnt;
    private long replyCount;
    private boolean posSensitiveEditable;
    private String lang;
    private ArrayList<Emoji> emojis;

    public Tweet() {
        emojis = new ArrayList<>();
        createdTime = new int[3];
    }

    public ArrayList<Emoji> getEmojis() {
        return emojis;
    }

    public boolean isPosSensitiveEditable() {
        return posSensitiveEditable;
    }

    public int[] getCreatedTime() {
        return createdTime;
    }

    public long getFavoCnt() {
        return favoCnt;
    }

    public long getReplyCount() {
        return replyCount;
    }

    public long getRetCnt() {
        return retCnt;
    }

    public String getFulText() {
        return fulText;
    }

    public String getLang() {
        return lang;
    }

    public String getTwId() {
        return twId;
    }

    public String getUsrId() {
        return usrId;
    }

    public void setCreatedTime(int[] createdTime) {
        for (int i = 0; i < 3;++i) {
            this.createdTime[i] = createdTime[i];
        }
    }

    public void setEmojis(ArrayList<Emoji> emojis) {
        this.emojis = emojis;
    }

    public void setFavoCnt(long favoCnt) {
        this.favoCnt = favoCnt;
    }

    public void setFulText(String fulText) {
        this.fulText = fulText;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setPosSensitiveEditable(boolean posSensitiveEditable) {
        this.posSensitiveEditable = posSensitiveEditable;
    }

    public void setReplyCount(long replyCount) {
        this.replyCount = replyCount;
    }

    public void setRetCnt(long retCnt) {
        this.retCnt = retCnt;
    }

    public void setTwId(String twId) {
        this.twId = twId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }
}
