import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {
    public static final Scanner INPUT = new Scanner(System.in);
    public static final String ALLVALID = "\\d+/\\d+/\\d+([ a-zA-Z0-9]|[-@!?.:,\"])+;";
    public static final String TOPERSON = "\\d+/\\d+/\\d+-[a-zA-Z0-9]+" +
            "@[a-zA-Z0-9]+ :\"([a-zA-Z0-9]|[?!,.]| )+\";";
    public static final String TOGROUP_MEM = "\\d+/\\d+/\\d+-[a-zA-Z0-9]+" +
            ":\"([a-zA-Z0-9]|[?!,.]| )*@[a-zA-Z0-9]+" +
            " ([a-zA-Z0-9]|[?!,.]| )*\";";
    public static final String TOGROUP = "\\d+/\\d+/\\d+-[a-zA-Z0-9]+" +
            ":\"([a-zA-Z0-9]|[?!,.]| )+\";";
    public static final String FIND_GIVER = "-(?<giver>[a-zA-Z0-9]+)[@:]";
    public static final String FIND_RECEICER = "@(?<receiver>[a-zA-Z0-9]+) ";
    public static final String GIVER_REGXNAME = "giver";
    public static final String RECEIVER_REGXNAME = "receiver";
    public static final String QDATE = "(?<year>\\d{0,4})/(?<month>\\d{0,2})/(?<day>\\d{0,2})";
    public static final String END_MASSAGE = "END_OF_MESSAGE";

    public static void main(String[] args) {
        String op;
        ArrayList<Time> times = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        processRawMessage(times,users);
        while (INPUT.hasNext()) {
            op = INPUT.next();
            switch (op) {
                case "qdate":
                    searchMessageByTime(times);
                    break;
                case "qsend":
                    searchMessageByGiver(users,times);
                    break;
                case "qrecv":
                    searchMessageByReceiver(users,times);
                    break;
                default:
                    break;
            }
        }



    }

    public static void searchMessageByTime(ArrayList<Time> times) {
        int []data = new int[3];
        int []isRec = new int[3];
        String str;
        String time = INPUT.next();
        Pattern pattern = Pattern.compile(QDATE);
        Matcher matcher = pattern.matcher(time);
        if (matcher.find()) {
            if (!(str = matcher.group("year")).equals("")) {
                data[0] =  Integer.parseInt(str);
                isRec[0] = 1;
            }
            if (!(str = matcher.group("month")).equals("")) {
                data[1] =  Integer.parseInt(str);
                isRec[1] = 1;
            }
            if (!(str = matcher.group("day")).equals("")) {
                data[2] = Integer.parseInt(str);
                isRec[2] = 1;
            }
        }
        ArrayList<Time> tmpTime = findTimeFromList(times,data,isRec);
        for (Time it : tmpTime) {
            for (String itstr : it.getTimeOfMessage()) {
                System.out.println(itstr);
            }
        }
    }

    public static void searchMessageByReceiver(ArrayList<User> users,ArrayList<Time> times) {
        String name = INPUT.next();
        if (!name.equals("-v")) {
            name = name.substring(1,name.length() - 1);
            User tmpUser;
            if ((tmpUser = findUserFromList(users,name)) != null) {
                for (String it: tmpUser.getRecMessage()) {
                    System.out.println(it);
                }
            }
        }
        else {
            String partName = INPUT.next();
            partName = partName.substring(1,partName.length() - 1);
            ArrayList<String> tmp = findUserMesFromTimeByPartName(times,
                    partName,FIND_RECEICER,RECEIVER_REGXNAME);
            for (String it : tmp) {
                System.out.println(it);
            }
        }

    }

    private static ArrayList<User> findUserFromListByPartName(ArrayList<User> users,
                                                              String partName) {
        ArrayList<User> tmp = new ArrayList<>();
        for (User it : users) {
            if (it.getName().contains(partName)) {
                tmp.add(it);
            }
        }
        return tmp;
    }

    public static ArrayList<String>  findUserMesFromTimeByPartName(ArrayList<Time> times,
                                                                   String partName,
                                                                   String patt,
                                                                   String regixName) {
        Pattern pattern = Pattern.compile(patt);
        Matcher matcher;
        String name;
        ArrayList<String> tmp = new ArrayList<>();
        for (Time it : times) {
            for (String itstr : it.getTimeOfMessage()) {
                matcher = pattern.matcher(itstr);
                if (matcher.find()) {
                    name = matcher.group(regixName);
                    if (name.contains(partName)) {
                        tmp.add(itstr);
                    }
                }
            }
        }
        return tmp;
    }

    public static void searchMessageByGiver(ArrayList<User> users,ArrayList<Time> times) {
        String name = INPUT.next();
        if (name.equals("-v")) {
            String partName = INPUT.next();
            partName = partName.substring(1,partName.length() - 1);
            ArrayList<String> tmp = findUserMesFromTimeByPartName(times,
                    partName,FIND_GIVER,GIVER_REGXNAME);
            for (String it : tmp) {
                System.out.println(it);
            }
        }
        else {
            User tmpUser;
            name = name.substring(1,name.length() - 1);
            if ((tmpUser = findUserFromList(users,name)) != null) {
                for (String it: tmpUser.getSendMessage()) {
                    System.out.println(it);
                }
            }
        }

    }

    public static void processRawMessage(ArrayList<Time> times,
                                                      ArrayList<User> users
    ) {
        String tmpString;
        while (!(tmpString = INPUT.nextLine()).equals(END_MASSAGE)) {
            getValidMessageFromAMessage(times,users,tmpString);
        }
    }

    private static User findUserFromList(ArrayList<User> users,String name) {
        for (User it : users) {
            if (it.getName().equals(name)) {
                return it;
            }
        }
        return null;
    } //是否String可以直接比较

    private static void findUserFromMessage(ArrayList<User> givers,
                                            String message,String findPatern,String mod) {
        User tmpUser;
        String name;
        Pattern pattern = Pattern.compile(findPatern);
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            name = matcher.group(mod);
            if ((tmpUser = findUserFromList(givers, name)) != null) {
                if (mod.equals(GIVER_REGXNAME)) {
                    tmpUser.getSendMessage().add(message);
                }
                else {
                    tmpUser.getRecMessage().add(message);
                }
            }
            else {
                tmpUser = new User(name);
                if (mod.equals(GIVER_REGXNAME)) {
                    tmpUser.getSendMessage().add(message);
                }
                else {
                    tmpUser.getRecMessage().add(message);
                }
                givers.add(tmpUser);
            }
        }
    }

    private static Time findWholeTimeFromList(ArrayList<Time> times,int []data) {
        for (Time it : times) {
            if (it.getYear() == data[0] && it.getMonth() == data[1] && it.getDay() == data[2]) {
                return it;
            }
        }
        return null;
    }

    private static void findTimeFromMessage(ArrayList<Time> times,String message) {
        int []data = new int[3];
        Pattern pattern = Pattern.compile(QDATE);
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            data[0] =  Integer.parseInt(matcher.group("year"));
            data[1] =  Integer.parseInt(matcher.group("month"));
            data[2] =  Integer.parseInt(matcher.group("day"));
        }
        Time tmpTile;
        if ((tmpTile = findWholeTimeFromList(times,data)) != null) {
            tmpTile.getTimeOfMessage().add(message);
        }
        else {
            tmpTile = new Time(data[0],data[1],data[2]);
            tmpTile.getTimeOfMessage().add(message);
            times.add(tmpTile);
        }
    }

    private static ArrayList<Time> findTimeFromList(ArrayList<Time> times,int []data,int []isRec) {
        ArrayList<Time> tmp = new ArrayList<>();
        for (Time it : times) {
            if (isRec[0] == 1 && data[0] != it.getYear() ||
                        isRec[1] == 1 && data[1] != it.getMonth() ||
                        isRec[2] == 1 && data[2] != it.getDay()) {
                continue;
            }
            tmp.add(it);
        }
        return tmp;
    }

    private static void getValidMessageFromAMessage(ArrayList<Time> times,
                                                       ArrayList<User> users,
                                                       String rawStr) {
        Pattern pattenZero = Pattern.compile(ALLVALID);
        Pattern patternOne = Pattern.compile(TOPERSON);
        Pattern patternTwo = Pattern.compile(TOGROUP_MEM);
        Pattern patternThreee = Pattern.compile(TOGROUP);
        Matcher matcherZero = pattenZero.matcher(rawStr);
        String str;
        String tmpStr;
        while (matcherZero.find()) {
            str = matcherZero.group();
            Matcher matcherOne = patternOne.matcher(str);
            Matcher matcherTwo = patternTwo.matcher(str);
            Matcher matchThree = patternThreee.matcher(str);
            if (matcherOne.find()) {
                tmpStr = matcherOne.group();
                findTimeFromMessage(times,tmpStr);
                findUserFromMessage(users,tmpStr,FIND_GIVER,GIVER_REGXNAME);
                findUserFromMessage(users,tmpStr,FIND_RECEICER,RECEIVER_REGXNAME);
            }
            else if (matcherTwo.find()) {
                tmpStr = matcherTwo.group();
                findTimeFromMessage(times,tmpStr);
                findUserFromMessage(users,tmpStr,FIND_GIVER,GIVER_REGXNAME);
                findUserFromMessage(users,tmpStr,FIND_RECEICER,RECEIVER_REGXNAME);
            }
            else if (matchThree.find()) {
                tmpStr = matchThree.group();
                findTimeFromMessage(times,tmpStr);
                findUserFromMessage(users,tmpStr,FIND_GIVER,GIVER_REGXNAME);
            }
        }

    }
}
