import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {
    public static final Scanner INPUT = new Scanner(System.in);
    public static final String VALID_MESSAGE = "(?<year>\\d{0,4})/(?<month>\\d{0,2})/" +
            "(?<day>\\d{0,2})" +
            "-(?<giver>[a-zA-Z0-9]+)((@*)(?<receiver1>[a-zA-Z0-9]*)( *)):\"" +
            "([a-zA-Z0-9]|[?!,.]| )*((@*)(?<receiver2>[a-zA-Z0-9]*)( *))([a-zA-Z0-9]|[?!,.]| )*\";";
    public static final String INSTRUCTION_COR = "((?<opcode>((qdate)|(qsend)|(qrecv))) )" +
            "((?<year>\\d{0,4})/(?<month>\\d{0,2})/(?<day>\\d{0,2}) *)*" +
            "((?<isv>(-v)) )*((?<opcode2>((-ssr)|(-ssq)|(-pre)|(-pos))) )*" +
            "(\"(?<partStr>[a-zA-Z0-9]+)\" *)*" +
            "((?<isc>-c) )*" +
            "(\"(?<badwords>([\\S\\s]+))\")*";

    public static final String[] DATE = {"year", "month", "day"};
    public static final String[] OBJECTNAME = {"giver", "receiver"};
    public static final String END_MASSAGE = "END_OF_MESSAGE";

    public static void main(String[] args) throws FileNotFoundException {
        String op;
        ArrayList<Message> allmessages = new ArrayList<>();
        processRawMessage(allmessages);
        Pattern pattern = Pattern.compile(INSTRUCTION_COR);
        Matcher matcher;
        while (INPUT.hasNextLine()) {
            op = INPUT.nextLine();
            matcher = pattern.matcher(op);
            if (matcher.find()) {
                if (checkInstruction(op, matcher) == 1) {
                    switch (matcher.group("opcode")) {
                        case "qdate":
                            searchMessageByTime(allmessages, matcher);
                            break;
                        case "qsend":
                            searchMessageByGiver(allmessages, matcher);
                            break;
                        case "qrecv":
                            searchMessageByReceiver(allmessages, matcher);
                            break;
                        default:
                            break;
                    }
                }

            }

        }


    }

    private static int checkInstruction(String op, Matcher matcher) {
        if (matcher.group("isv") == null && matcher.group("opcode2") != null) {
            System.out.println("Command Error!: Not Vague Query! \"" + op + "\"");
            return 0;
        }
        return 1;
    }

    public static void searchMessageByReceiver(ArrayList<Message> allmessages, Matcher matcher) {
        String opcode;
        String name;
        ArrayList<String> tmp = null;
        name = matcher.group("partStr");
        if (matcher.group("isv") == null) {
            tmp = findMesFromMessageByName(allmessages, name, OBJECTNAME[1], matcher);
        } else {
            opcode = matcher.group("opcode2");
            if (opcode == null || opcode.equals("-ssr")) {
                tmp = findMesFromMessageByPartName(allmessages, name, OBJECTNAME[1], matcher);
            } else if (opcode.equals("-ssq")) {
                tmp = findMesFromMessageBySubsq(allmessages, name, OBJECTNAME[1], matcher);
            } else if (opcode.equals("-pre")) {
                tmp = findMesFromMessageByPre(allmessages, name, OBJECTNAME[1], matcher);
            } else if (opcode.equals("-pos")) {
                tmp = findMesFromMessageBySuf(allmessages, name, OBJECTNAME[1], matcher);
            }
        }
        assert tmp != null;
        for (String it : tmp) {
            System.out.println(it);
        }
    }

    private static String replaceBadWords(String str, Matcher matcher) {
        String mesContent;
        String replaceStr = "";  //"*".repeat(badWords.length())
        String badWords = matcher.group("badwords");
        for (int i = 0; i < badWords.length(); ++i) {
            replaceStr += "*";
        }
        Pattern pattern = Pattern.compile("\"[a-zA-Z0-9|( ?!@,.)*]+\"");
        Matcher matcher1 = pattern.matcher(str);
        if (matcher1.find()) {
            mesContent = matcher1.group();
            mesContent = mesContent.replace(badWords, replaceStr);
            return matcher1.replaceAll(mesContent);
        }
        return null;
    }

    private static ArrayList<String> findMesFromMessageByName(
            ArrayList<Message> allmessages,
            String name,
            String objName,
            Matcher matcher) {
        String str;
        String tmpMes;
        ArrayList<String> tmp = new ArrayList<>();
        for (Message it : allmessages) {
            if (objName.equals(OBJECTNAME[0])) {
                str = it.getGiver();
            } else {
                str = it.getReceiver();
            }
            if (str.equals(name)) {
                tmpMes = it.getMessage();
                if (matcher.group("isc") != null) {
                    tmpMes = replaceBadWords(tmpMes, matcher);
                }
                tmp.add(tmpMes);
            }
        }
        return tmp;
    }

    private static ArrayList<String> findMesFromMessageByPartName(
            ArrayList<Message> allmessages,
            String name,
            String objName,
            Matcher matcher) {
        String str;
        String tmpMes;
        ArrayList<String> tmp = new ArrayList<>();
        for (Message it : allmessages) {
            if (objName.equals(OBJECTNAME[0])) {
                str = it.getGiver();
            } else {
                str = it.getReceiver();
            }
            if (str.contains(name)) {
                tmpMes = it.getMessage();
                if (matcher.group("isc") != null) {
                    tmpMes = replaceBadWords(it.getMessage(), matcher);
                }
                tmp.add(tmpMes);
            }
        }
        return tmp;
    }

    public static void searchMessageByGiver(ArrayList<Message> allmessages, Matcher matcher) {
        String opcode;
        String name;
        ArrayList<String> tmp = null;
        name = matcher.group("partStr");
        if (matcher.group("isv") == null) {
            tmp = findMesFromMessageByName(allmessages, name, OBJECTNAME[0], matcher);
        } else {
            opcode = matcher.group("opcode2");
            if (opcode == null || opcode.equals("-ssr")) {
                tmp = findMesFromMessageByPartName(allmessages, name, OBJECTNAME[0], matcher);
            } else if (opcode.equals("-ssq")) {
                tmp = findMesFromMessageBySubsq(allmessages, name, OBJECTNAME[0], matcher);
            } else if (opcode.equals("-pre")) {
                tmp = findMesFromMessageByPre(allmessages, name, OBJECTNAME[0], matcher);
            } else if (opcode.equals("-pos")) {
                tmp = findMesFromMessageBySuf(allmessages, name, OBJECTNAME[0], matcher);
            }
        }
        assert tmp != null;
        for (String it : tmp) {
            System.out.println(it);
        }
    }

    private static boolean isSuffix(String des, String tar) {
        int curLength = des.length() - 1;
        for (int i = tar.length() - 1; i >= 0 && curLength >= 0; --i, --curLength) {
            if (tar.charAt(i) != des.charAt(curLength)) {
                break;
            }
        }
        return curLength == -1;
    }

    private static boolean isPrifix(String des, String tar) {
        int desLength = 0;
        for (int i = 0; i < tar.length() && desLength < des.length(); ++i, ++desLength) {
            if (tar.charAt(i) != des.charAt(desLength)) {
                break;
            }
        }
        return desLength == des.length();
    }

    private static boolean isSubsequence(String des, String tar) {
        int corLength = 0;
        for (int i = 0; i < tar.length() && corLength < des.length(); ++i) {
            if (tar.charAt(i) == des.charAt(corLength)) {
                ++corLength;
            }
        }
        return corLength == des.length();
    }

    private static ArrayList<String> findMesFromMessageBySuf(ArrayList<Message> allmessages,
                                                             String des,
                                                             String objName,
                                                             Matcher matcher) {
        String name;
        String tmpMes;
        ArrayList<String> tmpStrs = new ArrayList<>();
        for (Message mes : allmessages) {
            if (objName.equals(OBJECTNAME[0])) {
                name = mes.getGiver();
            } else {
                name = mes.getReceiver();
            }
            if (isSuffix(des, name)) {
                tmpMes = mes.getMessage();
                if (matcher.group("isc") != null) {
                    tmpMes = replaceBadWords(tmpMes, matcher);
                }
                tmpStrs.add(tmpMes);
            }
        }
        return tmpStrs;
    }

    private static ArrayList<String> findMesFromMessageBySubsq(ArrayList<Message> allmessages,
                                                               String des,
                                                               String objName,
                                                               Matcher matcher) {
        String name;
        String tmpMes;
        ArrayList<String> tmpStrs = new ArrayList<>();
        for (Message mes : allmessages) {
            if (objName.equals(OBJECTNAME[0])) {
                name = mes.getGiver();
            } else {
                name = mes.getReceiver();
            }
            if (isSubsequence(des, name)) {
                tmpMes = mes.getMessage();
                if (matcher.group("isc") != null) {
                    tmpMes = replaceBadWords(tmpMes, matcher);
                }
                tmpStrs.add(tmpMes);

            }
        }
        return tmpStrs;
    }

    private static ArrayList<String> findMesFromMessageByPre(ArrayList<Message> allmessages,
                                                             String des,
                                                             String objName,
                                                             Matcher matcher) {
        String giverName;
        String tmpMes;
        ArrayList<String> tmpStrs = new ArrayList<>();
        for (Message mes : allmessages) {
            if (objName.equals(OBJECTNAME[0])) {
                giverName = mes.getGiver();
            } else {
                giverName = mes.getReceiver();
            }
            if (isPrifix(des, giverName)) {
                tmpMes = mes.getMessage();
                if (matcher.group("isc") != null) {
                    tmpMes = replaceBadWords(tmpMes, matcher);
                }
                tmpStrs.add(tmpMes);
            }
        }
        return tmpStrs;
    }

    public static void searchMessageByTime(ArrayList<Message> allmessages, Matcher matcher) {
        String tmpStr;
        int[] date = new int[3];
        int[] isRec = new int[3];
        ArrayList<String> tmpMes;
        if (!(tmpStr = matcher.group("year")).equals("")) {
            date[0] = Integer.parseInt(tmpStr);
            isRec[0] = 1;
        }
        if (!(tmpStr = matcher.group("month")).equals("")) {
            date[1] = Integer.parseInt(tmpStr);
            isRec[1] = 1;
        }
        if (!(tmpStr = matcher.group("day")).equals("")) {
            date[2] = Integer.parseInt(tmpStr);
            isRec[2] = 1;
        }
        //判断是否合法
        if (isDateValid(date, isRec) == 1) {
            tmpMes = findMesFromMessageByTime(allmessages, date, isRec, matcher);
            for (String it : tmpMes) {
                System.out.println(it);
            }
        } else {
            System.out.println("Command Error!: Wrong Date Format! \"" + matcher.group(0) + "\"");
        }

    }

    private static int isDateValid(int[] data, int[] isRec) {
        //year是否无需判断
        if (isRec[1] == 1 && data[1] < 1 || data[1] > 12) {
            return 0;
        }
        if (isRec[2] == 1 && data[2] < 1 || data[2] > 31) {
            return 0;
        }
        if (isRec[1] == 1 && isRec[2] == 1) {
            if (data[1] == 2) {
                if (data[2] > 29) {
                    return 0;
                }
                if (data[2] == 29) {
                    if (isRec[0] == 1 &&
                            !(data[0] % 4 == 0 && data[0] % 100 != 0 || data[0] % 400 == 0)) {
                        return 0;
                    }
                }
            }
            if (data[2] == 31) {
                if (data[1] != 1 && data[1] != 3 &&
                        data[1] != 5 && data[1] != 7 && data[1] != 8
                        && data[1] != 10 && data[1] != 12) {
                    return 0;
                }
            }
        }
        return 1;

    }

    public static ArrayList<String> findMesFromMessageByTime(ArrayList<Message> allmessages,
                                                             int[] date,
                                                             int[] isRec,
                                                             Matcher matcher) {
        String str;
        int[] tmpdate;
        ArrayList<String> tmp = new ArrayList<>();
        for (Message it : allmessages) {
            tmpdate = it.getData();
            if (isRec[0] == 1 && date[0] != tmpdate[0] ||
                    isRec[1] == 1 && date[1] != tmpdate[1]
                    || isRec[2] == 1 && date[2] != tmpdate[2]) {
                continue;
            }
            str = it.getMessage();
            if (matcher.group("isc") != null) {
                str = replaceBadWords(str, matcher);
            }
            tmp.add(str);
        }
        return tmp;
    }

    public static void processRawMessage(ArrayList<Message> allmessages) {
        String tmpString;
        while (!(tmpString = INPUT.nextLine()).equals(END_MASSAGE)) {
            getValidMessageFromAMessage(allmessages, tmpString);
        }
    }

    public static void getValidMessageFromAMessage(ArrayList<Message> allmessages, String rawStr) {
        String mes;
        String giver;
        String receiver;
        int[] data = new int[3];
        Pattern patten = Pattern.compile(VALID_MESSAGE);
        Matcher matcher = patten.matcher(rawStr);
        Message tmpMes;
        while (matcher.find()) {
            mes = matcher.group();
            for (int i = 0; i < 3; ++i) {
                data[i] = Integer.parseInt(matcher.group(DATE[i]));
            }
            giver = matcher.group("giver");
            if ((receiver = matcher.group("receiver1")).equals("")) {
                receiver = matcher.group("receiver2");
            }
            tmpMes = new Message(mes, data, giver, receiver);
            allmessages.add(tmpMes);
        }
    }
}
