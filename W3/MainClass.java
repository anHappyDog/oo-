import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Collections;

public class MainClass {
    private static final Scanner INPUT = new Scanner(System.in);

    public static void main(String [] args) throws IOException, ClassNotFoundException {
        int opM;
        int opN;
        ClassData  curClassData = new ClassData("1");
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        ArrayList<ClassData> classData = new ArrayList<>();
        classData.add(curClassData);
        opM = INPUT.nextInt();
        for (int i = 0; i < opM;i++) {
            opN = INPUT.nextInt();
            switch (opN)
            {
                case 1:
                    addAdventurer(adventurers); //完成，待测试
                    break;
                case 2:
                    giveAdventurerEquipment(adventurers); //完成，待测试
                    break;
                case 3:
                    deleteSomeCommodities(adventurers);//完成，待测试
                    break;
                case 4:
                    calPriceForAllCommodities(adventurers);//完成，待测试
                    break;
                case 5:
                    calMaxPriceForAllCommodities(adventurers);//完成，待测试
                    break;
                case 6:
                    calCommodityNumbers(adventurers);//完成，待测试
                    break;
                case 7:
                    printCommodities(adventurers);//完成，待测试
                    break;
                case 8:
                    useCommodities(adventurers);//完成，待测试
                    break;
                case 9:
                    printAdventurer(adventurers);
                    break;
                case 10:
                    hireAdventurer(adventurers);
                    break;
                case 11:
                    curClassData = addNewBranch(adventurers,classData,curClassData);
                    break;
                case 12:
                    curClassData = goToSomeBranch(adventurers,classData,curClassData);
                    adventurers = curClassData.getAdventurers();
                    break;
                default:
                    break;
            }

        }

    }

    private static ClassData findSomeClassData(ArrayList<ClassData> classData, String name) {
        for (ClassData iter : classData) {
            if (iter.getName().equals(name)) {
                return iter;
            }
        }
        return null;
    }

    public static ClassData goToSomeBranch(ArrayList<Adventurer> adventurers,
                                           ArrayList<ClassData> classData, ClassData curClassData)
            throws IOException, ClassNotFoundException {
        String name = INPUT.next();
        ClassData tmpClassData = findSomeClassData(classData,name);
        //if (tmpClassData != curClassData) {
        byte[] data = curClassData.out(adventurers);
        curClassData.setAdventurers(curClassData.in(data));
        assert tmpClassData != null;
        byte[] dataT = tmpClassData.out(tmpClassData.getAdventurers());
        tmpClassData.setAdventurers(tmpClassData.in(dataT));
        return tmpClassData;
        //}
        //else {
        //    return curClassData;
        //}
    }

    public static ClassData addNewBranch(ArrayList<Adventurer> adventurers,
                                         ArrayList<ClassData> classData,
                                         ClassData curClassData)
            throws IOException, ClassNotFoundException {
        String name = INPUT.next();
        ClassData tmpClassData = new ClassData(name);
        byte[] data = curClassData.out(adventurers);
        curClassData.setAdventurers(curClassData.in(data));
        classData.add(tmpClassData);
        return tmpClassData;
    }

    public static void hireAdventurer(ArrayList<Adventurer> adventurers) {
        int advid1 = INPUT.nextInt();
        int advid2 = INPUT.nextInt();
        Adventurer adv1 = findSomeAdventurer(adventurers,advid1);
        Adventurer adv2 = findSomeAdventurer(adventurers,advid2);
        assert adv1 != null;
        adv1.getCommodities().add(adv2);
        assert adv2 != null;

        adv1.setPrice(adv1.getPrice().add(adv2.getPrice()));
    }

    public static void useCommodities(ArrayList<Adventurer> adventurers) {
        int advid = INPUT.nextInt();
        Adventurer adv = findSomeAdventurer(adventurers,advid);
        assert adv != null;
        Collections.sort(adv.getCommodities());
        for (Commodity iter : adv.getCommodities()) {
            iter.userBy(adv);
        }
    }

    public static void printCommodities(ArrayList<Adventurer> adventurers) {
        int advid = INPUT.nextInt();
        int equid = INPUT.nextInt();
        ArrayList<Commodity> tmpComs = Objects.requireNonNull(
                findSomeAdventurer(adventurers, advid)).getCommodities();
        System.out.println(findSomeCommodity(tmpComs,equid));

    }

    public static void calCommodityNumbers(ArrayList<Adventurer> adventurers) {
        int advid = INPUT.nextInt();
        System.out.println(Objects.requireNonNull(findSomeAdventurer(adventurers, advid))
                        .getCommodities().size());
    }

    public static void calMaxPriceForAllCommodities(ArrayList<Adventurer> adventurers) {
        int advid = INPUT.nextInt();
        Adventurer adv = findSomeAdventurer(adventurers,advid);
        BigInteger maxPrice = new BigInteger("0");
        BigInteger tmpPrice;
        assert adv != null;
        for (Commodity iter : adv.getCommodities()) {
            tmpPrice = iter.getPrice();
            if (tmpPrice.compareTo(maxPrice) > 0) {
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

    public static void deleteSomeCommodities(ArrayList<Adventurer> adventurers) {
        int advid = INPUT.nextInt();
        int equid = INPUT.nextInt();
        Adventurer tmpAd = findSomeAdventurer(adventurers,advid);
        assert tmpAd != null;
        ArrayList<Commodity> tmpEqu = tmpAd.getCommodities();
        Commodity tmpCom = findSomeCommodity(tmpEqu,equid);
        assert tmpCom != null;
        tmpAd.setPrice(tmpAd.getPrice().subtract(tmpCom.getPrice()));
        tmpEqu.remove(tmpCom);


    }

    private static Commodity findSomeCommodity(ArrayList<Commodity> commodities,int id) {
        for (Commodity iter : commodities) {
            if (iter.getId() == id) {
                return iter;
            }
        }
        return null;
    }

    public static void calPriceForAllCommodities(ArrayList<Adventurer> adventurers) {
        int advid = INPUT.nextInt();
        Adventurer adv = findSomeAdventurer(adventurers,advid);
        BigInteger allPrice = BigInteger.valueOf(0);
        assert adv != null;
        for (Commodity iter : adv.getCommodities())
        {
            allPrice = allPrice.add(iter.getPrice());
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
        tmpAd.getCommodities().add(equ);
        tmpAd.setPrice(tmpAd.getPrice().add(equ.getPrice()));
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



