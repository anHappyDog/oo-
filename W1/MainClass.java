import java.math.BigInteger;
import java.util.*;

public class MainClass {
    private static final Scanner INPUT = new Scanner(System.in);
    private  static int[] EquipmentTypes;

    public static void main(String [] args) {
        int opM;
        int opN;

        ArrayList<Adventurer> adventurers = new ArrayList<>();

        opM = INPUT.nextInt();
        for (int i = 0; i < opM;i++) {
            opN = INPUT.nextInt();
            switch (opN)
            {
                case 1:
                    addAdventurer(adventurers);
                    break;
                case 2:
                    giveAdventurerEquipment(adventurers);
                    break;
                case 3:
                    deleteSomeEquipment(adventurers);
                    break;
                case 4:
                    calPriceForAllEquipment(adventurers);
                    break;
                case 5:
                    calMaxPriceForAllEquipment(adventurers);
                    break;
                case 6:
                    calEquipmentNumbers(adventurers);
                    break;
                case 7:
                    printEquipment(adventurers);
                    break;
                case 8:
                    useEquipment(adventurers);
                    break;
                case 9:
                    printAdventurer(adventurers);
                    break;
                default:
                    break;
            }
        }

    }

    public static void useEquipment(ArrayList<Adventurer> adventurers) {
        int advid = INPUT.nextInt();
        int equid = INPUT.nextInt();
        Adventurer tmpAd = findSomeAdventurer(adventurers,advid);
        assert tmpAd != null;
        tmpAd.use(tmpAd.getEquipment().get(equid));
    }

    public static void printEquipment(ArrayList<Adventurer> adventurers) {
        int advid = INPUT.nextInt();
        int equid = INPUT.nextInt();
        System.out.println(Objects.requireNonNull(findSomeAdventurer(adventurers, advid)).
                getEquipment().get(equid));

    }

    public static void calEquipmentNumbers(ArrayList<Adventurer> adventurers) {
        int advid = INPUT.nextInt();
        System.out.println(Objects.requireNonNull(findSomeAdventurer(adventurers, advid))
                        .getEquipment().size());
    }

    public static void calMaxPriceForAllEquipment(ArrayList<Adventurer> adventurers) {
        int advid = INPUT.nextInt();
        long maxPrice = Long.MIN_VALUE;
        long tmpPrice;
        for (Map.Entry<Integer,Equipment> entry :
                Objects.requireNonNull(findSomeAdventurer(adventurers, advid))
                        .getEquipment().entrySet()) {
            tmpPrice = entry.getValue().getPrice();
            if (tmpPrice > maxPrice) {
                maxPrice = tmpPrice;
            }
        }
        System.out.println(maxPrice);
    }

    public static void addAdventurer(ArrayList<Adventurer> adventurers) {
        int advid = INPUT.nextInt();
        String name = INPUT.next();
        Adventurer newAdventurer = new Adventurer(advid,name);
        adventurers.add(newAdventurer);
    }

    public static void deleteSomeEquipment(ArrayList<Adventurer> adventurers) {
        int advid = INPUT.nextInt();
        int equid = INPUT.nextInt();
        Adventurer tmpAd = findSomeAdventurer(adventurers,advid);
        assert tmpAd != null;
        HashMap<Integer,Equipment> tmpEqu = tmpAd.getEquipment();
        tmpEqu.remove(equid);

    }

    public static void calPriceForAllEquipment(ArrayList<Adventurer> adventurers) {
        int advid = INPUT.nextInt();
        BigInteger allPrice = BigInteger.valueOf(0);
        for (Map.Entry<Integer,Equipment> entry :
                Objects.requireNonNull(
                        findSomeAdventurer(adventurers, advid)).getEquipment().entrySet())
        {
            allPrice = allPrice.add(BigInteger.valueOf(entry.getValue().getPrice()));
        }
        System.out.println(allPrice);
    }

    public static void giveAdventurerEquipment(ArrayList<Adventurer> adventurers) {
        int advid = INPUT.nextInt();
        int equid = INPUT.nextInt();
        Equipment equ = createEquipment(equid);
        Adventurer tmpAd = findSomeAdventurer(adventurers,advid);
        assert tmpAd != null;
        assert equ != null;
        tmpAd.getEquipment().put(equ.getId(),equ);
    }

    private static Equipment createEquipment(int equid) {
        int id = INPUT.nextInt();
        String name = INPUT.next();
        long price = INPUT.nextLong();
        double capaOrSharp = INPUT.nextDouble();
        double tmp;
        switch (equid) {
            case 1:
                return  new Bottle(id,name,price,capaOrSharp);
            case 2:
                tmp = INPUT.nextDouble();
                return new HealingPotion(id,name,price,capaOrSharp,tmp);
            case 3:
                tmp = INPUT.nextDouble();
                return new ExpBottle(id,name,price,capaOrSharp,tmp);
            case 4:
                return new Sword(id,name,price,capaOrSharp);
            case 5:
                tmp = INPUT.nextDouble();
                return new RareSword(id,name,price,capaOrSharp,tmp);
            case 6:
                tmp = INPUT.nextDouble();
                return new EpicSword(id,name,price,capaOrSharp,tmp);
            default:
                break;
        }
        return null;
    }

    public static void printAdventurer(ArrayList<Adventurer> adventurers) {
        int advid = INPUT.nextInt();
        System.out.println(findSomeAdventurer(adventurers,advid));
    }

    private static Adventurer findSomeAdventurer(ArrayList<Adventurer> adventurers,int advid) {
        for (Adventurer ad: adventurers) {
            if (ad.isIdEqual(advid)) {
                return ad;
            }
        }
        return null;
    }
}



