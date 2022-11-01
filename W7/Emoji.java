public class Emoji implements Comparable<Emoji> {
    private String name;
    private String emojiId;
    private long cnt;

    public Emoji() {

    }

    public Emoji(String name, String emojiId, long cnt) {
        setName(name);
        setEmojiId(emojiId);
        setCnt(cnt);
    }

    public String getName() {
        return name;
    }

    public String getEmojiId() {
        return emojiId;
    }

    public void setEmojiId(String emojiId) {
        this.emojiId = emojiId;
    }

    public long getCnt() {
        return cnt;
    }

    public void setCnt(long cnt) {
        this.cnt = cnt;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Emoji emoji) {
        if (this.cnt > emoji.cnt) {
            return -1;
        }
        else if (this.cnt < emoji.cnt) {
            return 1;
        }
        else {
            return this.name.compareTo(emoji.name);
        }
    }
}
