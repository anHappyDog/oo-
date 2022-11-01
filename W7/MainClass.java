import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {
    public static final String JSON_CHECK = "\\{\"object_type\":\"TweetRaw\"," +
            "\"download_datetime\":" +
            "\"(?<dlyear>\\d{4})-(?<dlmonth>\\d{2})-(?<dlday>\\d{2}) \\d{2}:\\d{2}\"," +
            "\"raw_value\":\\{\"created_at\":\"(?<week>Mon|Tue|Wed|Thu|Fri|Sat|Sun) " +
            "(?<month>Dec|Nov|Oct|Sep|Aug|Jul|Jun|May|Apr|Mar|Feb|Jan) " +
            "(?<day>\\d{2}) \\d{2}:\\d{2}:\\d{2} " +
            "(?<year>\\d{4})\",\"id\":(?<Id>\\d+),\"full_text\":" +
            "((\"(?<fullText>[^\"]+)\")|(null)),\"user_id\":(?<usrId>\\d+)," +
            "\"retweet_count\":(?<retweetCnt>\\d+),\"favorite_count\"" +
            ":(?<favoriteCnt>\\d+)," +
            "\"reply_count\":(?<replyCnt>\\d+),\"possibly_sensitive_editable\"" +
            ":(?<senSedit>true|false)," +
            "\"lang\":\"(?<lang>[^\"]+)\",\"emojis\":\\[(?<rawEmojis>[\\S\\s]*)]}}";
    public static final String EMOJI = "\\{\"name\":\"(?<name>[^\"]+)\"," +
            "\"emoji_id\":(?<emojiId>\\d+),\"count\":(?<cnt>\\d+)}";
    public static final String STAOTIME = "(?<strYear>\\d{4})-(?<strMonth>\\d{2})-" +
            "(?<strDay>\\d{2})~" +
            "(?<stoYear>\\d{4})-(?<stoMonth>\\d{2})-(?<stoDay>\\d{2})";
    public static final String[] STRTYPE = {"strYear", "strMonth", "strDay"};
    public static final String[] STOTYPE = {"stoYear", "stoMonth", "stoDay"};
    public static final Scanner INPUT = new Scanner(System.in);
    public static final String[] RELSTIME_TYPE = {"year", "month", "day"};
    public static final String[] DLTIME_TYPE = {"dlyear", "dlmonth", "dlday"};

    public static void main(String[] args) {
        ArrayList<Json> jsons = new ArrayList<>();
        ArrayList<Tweet> tweets = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        readRawJsons(jsons, tweets, users);
        processIntr(jsons, tweets, users);
    }

    private static void processIntr(ArrayList<Json> jsons,
                                    ArrayList<Tweet> tweets,
                                    ArrayList<User> users) {
        String op;
        while (INPUT.hasNext()) {
            op = INPUT.next();
            switch (op) {
                case "Qdate":
                    qDate(users);
                    break;
                case "Qemoji":
                    qEmoji(tweets);
                    break;
                case "Qcount":
                    qCount(jsons);
                    break;
                case "Qtext":
                    qText(tweets);
                    break;
                case "Qsensitive":
                    qSensitive(users);
                    break;
                case "Qlang":
                    qLang(tweets);
                    break;
                default:
                    break;
            }

        }
    }

    private static int isTimeIncluded(int[] strTime, int[] stoTime, int[] time) {
        if (time[0] < strTime[0] || time[0] > stoTime[0]) {
            return 0;
        }
        if (strTime[0] == stoTime[0]) {
            if (time[1] < strTime[1] || time[1] > stoTime[1]) {
                return 0;
            }
            if (strTime[1] == stoTime[1]) {
                if (time[2] < strTime[2] || time[2] > stoTime[2]) {
                    return 0;
                }
                return 1;
            } else {
                if (strTime[1] == time[1]) {
                    if (strTime[2] > time[2]) {
                        return 0;
                    }
                    return 1;
                }
                if (stoTime[1] == time[1]) {
                    if (stoTime[2] < time[2]) {
                        return 0;
                    }
                    return 1;
                }
                return 1;
            }
        } else {
            if (time[0] == strTime[0]) {
                if (time[1] < strTime[1]) {
                    return 0;
                }
                if (time[1] == strTime[1]) {
                    if (time[2] < strTime[2]) {
                        return 0;
                    }
                    return 1;
                }
                return 1;
            }
            if (time[0] == stoTime[0]) {
                if (time[1] > stoTime[1]) {
                    return 0;
                }
                if (time[1] == stoTime[1]) {
                    if (time[2] > stoTime[2]) {
                        return 0;
                    }
                    return 1;
                }
                return 1;
            }
            return 1;
        }
    }

    private static void qLang(ArrayList<Tweet> tweets) {
        String id = INPUT.next();
        Tweet tmpTweet = searchTweetId(tweets, id);
        if (tmpTweet != null) {
            System.out.println(tmpTweet.getLang());
        }
    }

    private static void qSensitive(ArrayList<User> users) {
        String id = INPUT.next();
        User tmpUser = searchUserId(users, id);
        if (tmpUser != null) {
            System.out.println(tmpUser.getSensiNum());
        }
    }

    private static void qText(ArrayList<Tweet> tweets) {
        String id = INPUT.next();
        String str;
        Tweet tmpTweet = searchTweetId(tweets, id);
        if (tmpTweet != null) {
            str = tmpTweet.getFulText();
            if (str != null) {
                System.out.println(tmpTweet.getFulText());
            }
            else {
                System.out.println("None");
            }
        }
    }

    private static void qCount(ArrayList<Json> jsons) {
        String date = INPUT.next();
        int[] strTime = new int[3];
        int[] stoTime = new int[3];
        int cnt = 0;
        Pattern pattern = Pattern.compile(STAOTIME);
        Matcher matcher = pattern.matcher(date);
        if (matcher.find()) {
            for (int i = 0; i < 3; ++i) {
                strTime[i] = Integer.parseInt(matcher.group(STRTYPE[i]));
                stoTime[i] = Integer.parseInt(matcher.group(STOTYPE[i]));
            }
            for (Json it : jsons) {
                if (isTimeIncluded(strTime, stoTime, it.getDltime()) == 1) {
                    ++cnt;
                }
            }
            System.out.println(cnt);
        }
    }

    private static void qEmoji(ArrayList<Tweet> tweets) {
        String twId = INPUT.next();
        Tweet tmpTweet = searchTweetId(tweets, twId);
        if (tmpTweet != null) {
            ArrayList<Emoji> emo = tmpTweet.getEmojis();
            if (emo.size() == 0) {
                System.out.println("None");
            }
            else {
                for (Emoji it : tmpTweet.getEmojis()) {
                    System.out.print(it.getName() + " ");
                }
                System.out.print("\n");
            }

        }
    }

    private static void qDate(ArrayList<User> users) {
        String usrId = INPUT.next();
        String date = INPUT.next();
        User tmpUser = searchUserId(users, usrId);
        int[] strTime = new int[3];
        int[] stoTime = new int[3];
        int retCnt = 0;
        int favoCnt = 0;
        int replyCnt = 0;
        int cnt = 0;
        if (tmpUser != null) {
            ArrayList<Tweet> tmpTweets = tmpUser.getTweets();
            Pattern pattern = Pattern.compile(STAOTIME);
            Matcher matcher = pattern.matcher(date);
            if (matcher.find()) {
                for (int i = 0; i < 3; ++i) {
                    strTime[i] = Integer.parseInt(matcher.group(STRTYPE[i]));
                    stoTime[i] = Integer.parseInt(matcher.group(STOTYPE[i]));
                }
                for (Tweet it : tmpTweets) {
                    if (isTimeIncluded(strTime, stoTime, it.getCreatedTime()) == 1) {
                        retCnt += it.getRetCnt();
                        favoCnt += it.getFavoCnt();
                        replyCnt += it.getReplyCount();
                        ++cnt;
                    }
                }
            }

        }
        System.out.println(cnt + " " + retCnt + " " + favoCnt + " " + replyCnt);

    }

    private static void readRawJsons(ArrayList<Json> jsons,
                                     ArrayList<Tweet> tweets,
                                     ArrayList<User> users) {
        String tmpStr;
        Matcher matcher;

        Pattern pattern = Pattern.compile(JSON_CHECK);
        while (!(tmpStr = INPUT.nextLine()).equals("END_OF_MESSAGE")) {
            matcher = pattern.matcher(tmpStr);
            if (matcher.find()) {
                jsons.add(createJson(matcher, tweets, users));
            }
        }

    }

    private static void addTmpTweet(Tweet tmptweet, Matcher matcher) {
        String tmpstr;
        if ((tmpstr = matcher.group("fullText")) != null) {
            tmptweet.setFulText(tmpstr);
        } else {
            tmptweet.setFulText(null);
        }
        if ((tmpstr = matcher.group("usrId")) != null) {
            tmptweet.setUsrId(tmpstr);
        }
        if ((tmpstr = matcher.group("Id")) != null) {
            tmptweet.setTwId(tmpstr);
        }
        if ((tmpstr = matcher.group("retweetCnt")) != null) {
            tmptweet.setRetCnt(Integer.parseInt(tmpstr));
        }
        if ((tmpstr = matcher.group("favoriteCnt")) != null) {
            tmptweet.setFavoCnt(Integer.parseInt(tmpstr));
        }
        if ((tmpstr = matcher.group("replyCnt")) != null) {
            tmptweet.setReplyCount(Integer.parseInt(tmpstr));
        }
        if ((tmpstr = matcher.group("senSedit")) != null) {
            tmptweet.setPosSensitiveEditable(tmpstr.equals("true"));
        }
        if ((tmpstr = matcher.group("lang")) != null) {
            tmptweet.setLang(tmpstr);
        }
    }

    private static void switchForCreate(Matcher matcher, int[] tmpdltime) {
        String tmpstr;
        for (int i = 0; i < 3; ++i) {
            if ((tmpstr = matcher.group(RELSTIME_TYPE[i])) != null) {
                if (i == 1) {
                    switch (tmpstr) {
                        case "Jan":
                            tmpdltime[i] = 1;
                            break;
                        case "Feb":
                            tmpdltime[i] = 2;
                            break;
                        case "Mar":
                            tmpdltime[i] = 3;
                            break;
                        case "Apr":
                            tmpdltime[i] = 4;
                            break;
                        case "May":
                            tmpdltime[i] = 5;
                            break;
                        case "Jun":
                            tmpdltime[i] = 6;
                            break;
                        case "Jul":
                            tmpdltime[i] = 7;
                            break;
                        case "Aug":
                            tmpdltime[i] = 8;
                            break;
                        case "Sep":
                            tmpdltime[i] = 9;
                            break;
                        case "Oct":
                            tmpdltime[i] = 10;
                            break;
                        case "Nov":
                            tmpdltime[i] = 11;
                            break;
                        case "Dec":
                            tmpdltime[i] = 12;
                            break;
                        default:
                            break;
                    }
                } else {
                    tmpdltime[i] = Integer.parseInt(tmpstr);
                }
            }

        }
    }

    private static Json createJson(Matcher matcher,
                                   ArrayList<Tweet> tweets,
                                   ArrayList<User> users) {
        Json tmpJson = new Json();
        Tweet tmptweet = new Tweet();
        int[] tmpdltime = new int[3];
        String tmpstr;
        for (int i = 0; i < 3; ++i) {
            if ((tmpstr = matcher.group(DLTIME_TYPE[i])) != null) {
                tmpdltime[i] = Integer.parseInt(tmpstr);
            }
        }
        tmpJson.setDltime(tmpdltime);
        addTmpTweet(tmptweet, matcher);
        switchForCreate(matcher, tmpdltime);
        tmptweet.setCreatedTime(tmpdltime);
        if ((tmpstr = matcher.group("rawEmojis")) != null) {
            tmptweet.setEmojis(createEmojis(tmpstr));
        }
        tmpJson.setTweet(tmptweet);
        tweets.add(tmptweet);
        User tmpUser;
        if ((tmpUser = searchUserId(users, tmptweet.getUsrId())) != null) {
            tmpUser.getTweets().add(tmptweet);
            if (tmptweet.isPosSensitiveEditable()) {
                tmpUser.setSensiNum(tmpUser.getSensiNum() + 1);
            }
        } else {
            tmpUser = new User(tmptweet.getUsrId());
            tmpUser.getTweets().add(tmptweet);
            if (tmptweet.isPosSensitiveEditable()) {
                tmpUser.setSensiNum(tmpUser.getSensiNum() + 1);
            }
            users.add(tmpUser);
        }
        return tmpJson;
    }

    private static User searchUserId(ArrayList<User> users, String id) {
        for (User it : users) {
            if (it.getUsrId().equals(id)) {
                return it;
            }
        }
        return null;
    }

    private static Tweet searchTweetId(ArrayList<Tweet> tweets, String id) {
        for (Tweet it : tweets) {
            if (it.getTwId().equals(id)) {
                return it;
            }
        }
        return null;
    }

    private static ArrayList<Emoji> createEmojis(String tmpstr) {
        Emoji emoji;
        ArrayList<Emoji> tmpEmo = new ArrayList<>();
        Pattern pattern = Pattern.compile(EMOJI);
        Matcher matcher = pattern.matcher(tmpstr);
        while (matcher.find()) {
            emoji = new Emoji();
            emoji.setEmojiId(matcher.group("emojiId"));
            emoji.setName(matcher.group("name"));
            emoji.setCnt(Integer.parseInt(matcher.group("cnt")));
            tmpEmo.add(emoji);
        }
        Collections.sort(tmpEmo);
        return tmpEmo;
    }

}
