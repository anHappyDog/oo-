import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ClassData {
    private ArrayList<Adventurer> adventurers;
    private String name;

    public ClassData() {
        adventurers = new ArrayList<>();
    }

    public void setAdventurers(ArrayList<Adventurer> adventurers) {
        this.adventurers = adventurers;
    }

    public ClassData(String name) {
        this.name = name;
        adventurers = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public ArrayList<Adventurer> getAdventurers() {
        return adventurers;
    }

    public ArrayList<Adventurer> in(byte[] data) throws  IOException,ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return (ArrayList<Adventurer>)(is.readObject());
    }

    public byte[] out(ArrayList<Adventurer> adventurers)throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(adventurers);
        return bos.toByteArray();
    }

}
