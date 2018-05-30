import java.io.*;
import java.util.ArrayList;

public class SerializationHandler {



    private static <T>  ArrayList<T> load(String path) {

        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<T> list = (ArrayList<T>) ois.readObject();
            return list;
        }
        catch (FileNotFoundException e) { }
        catch (IOException e) { }
        catch (ClassNotFoundException e) { }
        return null;
    }

    private static <T> void save(String path, ArrayList<T> list) {

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
        }
        catch (FileNotFoundException e) { }
        catch (IOException e) { }

    }

    public static Settings loadSettings() {
        Settings settings = new Settings();
        ArrayList list = load("settings.jdm");
        if (list != null){
            settings = (Settings) list.get(0);
        }
        return settings;
    }

    public static void saveSettings() {
        ArrayList list = new ArrayList();
        list.add(DownloadManager.getSettings());
        save("settings.jdm", list);
    }
}
