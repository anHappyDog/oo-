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
    public static final String  []DATE = {"year","month","day"};
    public static final String QDATE = "(?<year>\\d{0,4})/(?<month>\\d{0,2})/(?<day>\\d{0,2})";
    public static final String END_MASSAGE = "END_OF_MESSAGE";

    public static void main(String[] args) {
        String op;
        ArrayList<Message> allmessages = new ArrayList<>();
        processRawMessage(allmessages);
        while (INPUT.hasNext()) {
            op = INPUT.next();
            switch (op) {
                case "qdate":
                    searchMessageByTime(allmessages);
                    break;
                case "qsend":
                    searchMessageByGiver(allmessages);
                    break;
                case "qrecv":
                    searchMessageByReceiver(allmessages);
                    break;
                default:
                    break;
            }
        }



    }

    private static ArrayList<String> findMesFromMessageByReceiverName(
            ArrayList<Message> allmessages,
            String name) {
        ArrayList<String> tmp = new ArrayList<>();
        for (Message it : allmessages) {
            if (it.getReceiver().equals(name)) {
                tmp.add(it.getMessage());
            }
        }
        return tmp;
    }

    private static ArrayList<String> findMesFromMessageByReceiverPartName(
            ArrayList<Message> allmessages,
            String partName) {
        ArrayList<String> tmp = new ArrayList<>();
        for (Message it : allmessages) {
            if (it.getReceiver().contains(partName)) {
                tmp.add(it.getMessage());
            }
        }
        return tmp;
    }

    public static void searchMessageByReceiver(ArrayList<Message> allmessages) {
        String name = INPUT.next();
        String partName;
        ArrayList<String> tmp;
        if (!name.equals("-v")) {
            name = name.substring(1,name.length() - 1);
            tmp = findMesFromMessageByReceiverName(allmessages,name);
        }
        else {
            partName = INPUT.next();
            partName = partName.substring(1,partName.length() - 1);
            tmp = findMesFromMessageByReceiverPartName(allmessages,partName);
        }
        for (String it : tmp) {
            System.out.println(it);
        }
    }

    private static ArrayList<String> findMesFromMessageByGiverName(
            ArrayList<Message> allmessages,
            String name) {
        ArrayList<String> tmp = new ArrayList<>();
        for (Message it : allmessages) {
            if (it.getGiver().equals(name)) {
                tmp.add(it.getMessage());
            }
        }
        return tmp;
    }

    private static ArrayList<String> findMesFromMessageByGiverPartName(
            ArrayList<Message> allmessages,
            String name) {
        ArrayList<String> tmp = new ArrayList<>();
        for (Message it : allmessages) {
            if (it.getGiver().contains(name)) {
                tmp.add(it.getMessage());
            }
        }
        return tmp;
    }

    public static void searchMessageByGiver(ArrayList<Message> allmessages) {
        String name = INPUT.next();
        String partName;
        ArrayList<String> tmp;
        if (!name.equals("-v")) {
            name = name.substring(1,name.length() - 1);
            tmp = findMesFromMessageByGiverName(allmessages,name);
        }
        else {
            partName = INPUT.next();
            partName = partName.substring(1,partName.length() - 1);
            tmp = findMesFromMessageByGiverPartName(allmessages,partName);
        }
        for (String it : tmp) {
            System.out.println(it);
        }
    }

    public static void searchMessageByTime(ArrayList<Message> allmessages) {
        String tmpStr;
        String str = INPUT.next();
        int []date = new int[3];
        int []isRec = new int[3];
        ArrayList<String> tmpMes;
        Pattern pattern = Pattern.compile(QDATE);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            if (!(tmpStr = matcher.group("year")).equals("")) {
                date[0] =  Integer.parseInt(tmpStr);
                isRec[0] = 1;
            }
            if (!(tmpStr = matcher.group("month")).equals("")) {
                date[1] =  Integer.parseInt(tmpStr);
                isRec[1] = 1;
            }
            if (!(tmpStr = matcher.group("day")).equals("")) {
                date[2] = Integer.parseInt(tmpStr);
                isRec[2] = 1;
            }
        }

        tmpMes = findMesFromMessageByTime(allmessages,date,isRec);
        for (String it : tmpMes) {
            System.out.println(it);
        }
    }

    public static ArrayList<String> findMesFromMessageByTime(ArrayList<Message> allmessages,
                                                             int[]date,
                                                             int[]isRec) {
        int []tmpdate;
        ArrayList<String> tmp = new ArrayList<>();
        for (Message it : allmessages) {
            tmpdate = it.getData();
            if (isRec[0] == 1 && date[0] != tmpdate[0] ||
                    isRec[1] == 1 && date[1] != tmpdate[1]
                    || isRec[2] == 1 && date[2] != tmpdate[2]) {
                continue;
            }
            tmp.add(it.getMessage());
        }
        return tmp;
    }

    public static void processRawMessage(ArrayList<Message> allmessages) {
        String tmpString;
        while (!(tmpString = INPUT.nextLine()).equals(END_MASSAGE)) {
            getValidMessageFromAMessage(allmessages,tmpString);
        }
    }

    public static void getValidMessageFromAMessage(ArrayList<Message> allmessages, String rawStr) {
        String mes;
        String giver;
        String receiver;
        int []data = new int[3];
        Pattern patten = Pattern.compile(VALID_MESSAGE);
        Matcher matcher = patten.matcher(rawStr);
        Message tmpMes;
        while (matcher.find()) {
            mes = matcher.group();
            for (int i = 0; i < 3; ++ i) {
                data[i] = Integer.parseInt(matcher.group(DATE[i]));
            }
            giver = matcher.group("giver");
            if ((receiver = matcher.group("receiver1")).equals("")) {
                receiver = matcher.group("receiver2");
            }
            tmpMes = new Message(mes,data,giver,receiver);
            allmessages.add(tmpMes);
        }
    }
}
