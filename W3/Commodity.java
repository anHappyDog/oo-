import java.io.Serializable;
import java.math.BigInteger;

interface Commodity  extends Comparable<Commodity>,Serializable {
    void setId(int id);

    int getId();

    void setPrice(BigInteger price);

    BigInteger getPrice();

    void setName(String name);

    String getName();

    void userBy(Adventurer user);

    @Override
    int compareTo(Commodity other);

}
