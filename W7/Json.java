public class Json {
    private int[] dltime;
    private Tweet tweet;

    public Json() {
        dltime = new int[3];
    }

    public Json(int[] dltime,Tweet tweet) {
        this.dltime = new int[3];
        setDltime(dltime);
        setTweet(tweet);
    }

    public int[] getDltime() {
        return dltime;
    }

    public void setDltime(int[] dltime) {
        for (int i = 0;i < 3;++i) {
            this.dltime[i] = dltime[i];
        }
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }
}
