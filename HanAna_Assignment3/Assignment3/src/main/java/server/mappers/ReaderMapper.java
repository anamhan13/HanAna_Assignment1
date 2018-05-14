package server.mappers;

import server.model.Writer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ReaderMapper implements Serializable {

    private HashMap<String, Reader> readers;

    public ReaderMapper() {
        readers = new HashMap<String, Reader>();
    }

    public HashMap<String, Reader> getWriters() {
        return readers;
    }

    public void setWriters(HashMap<String, Reader> readers) {
        this.readers = readers;
    }

    public boolean isWellFormed(HashMap<String,Reader> hmap) {
        for (Map.Entry<String, Reader> c : hmap.entrySet()) {
            if (c.equals(null))
                return false;
            if (c.getKey()==null)
                return false;
            // if (!isCNPCorrect(c.getValue()))
            //     return false;
        }
        return true;
    }

    public void serialize() {
        assert isWellFormed(readers);
        try {
            FileOutputStream outputFile = new FileOutputStream("readers.json");
            ObjectOutputStream output = new ObjectOutputStream(outputFile);
            output.writeObject(readers);
            output.close();
            outputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert isWellFormed(readers);
    }

    @SuppressWarnings("unchecked")
    public void deserialize() {
        assert isWellFormed(readers);
        try {
            FileInputStream inputFile = new FileInputStream("readers.json");
            ObjectInputStream input = new ObjectInputStream(inputFile);
            setWriters((HashMap<String, Reader>)input.readObject());
            input.close();
            inputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        assert isWellFormed(readers);
    }
}
