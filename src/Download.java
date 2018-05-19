import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;


/**
 * Created by 9631815 on 5/12/2018.
 */
public class Download implements Serializable{


    public enum status {
        Downloading, Paused, Cancelled, Finished
    }

    private String name;
    private String link;
    private status state;
    private int sizeInBytes;
    private int downloadedBytes;
    private int bytesPerSecond;
    private Date creationTime;
    private Date finishTime;
    private String saveTo;


    public Download(String name, String link){
        saveTo = Settings.getSaveToPath();
        this.name = name;
        this.link = link;
        state = status.Downloading;
        creationTime = Calendar.getInstance().getTime();
    }

    public static Comparator<Download> downloadNameComparator = new Comparator<Download>() {
        @Override
        public int compare(Download o1, Download o2) {
            //Ascending order
            int d = o1.getName().toUpperCase().compareTo(o2.getName().toUpperCase());
            return d;
        }
    };

    public static Comparator<Download> downloadSizeComparator = new Comparator<Download>() {
        @Override
        public int compare(Download o1, Download o2) {
            int d = o1.getSizeInBytes() - o2.getSizeInBytes();
            return d;
        }
    };

    public static Comparator<Download> creationTimeComparator = new Comparator<Download>() {
        @Override
        public int compare(Download o1, Download o2) {
            if (o1.getCreationTime().before(o2.getCreationTime())) {
                return 1;
            }
            if (o2.getCreationTime().before(o1.getCreationTime())) {
                return -1;
            }
            return 0;
        }
    };

    public static Comparator<Download> downloadedPercentageComparator = new Comparator<Download>() {
        @Override
        public int compare(Download o1, Download o2) {
            return (int)(o1.getDownloadedPercentage()*100 - o2.getDownloadedPercentage()*100);
        }
    };

    public static Comparator<Download> downloadedAmountComparator = new Comparator<Download>() {
        @Override
        public int compare(Download o1, Download o2) {
            return o1.getDownloadedBytes() - o2.getDownloadedBytes();
        }
    };

    public static void sortDownloads(ArrayList<Download> downloads, Comparator<Download> firstPriority, boolean firstIsAscending,
                              Comparator<Download> secondPriority, boolean secondIsAscending) {
        Collections.sort(downloads, firstPriority);
        if (! firstIsAscending) {
            Collections.reverse(downloads);
        }

        int order = 1;
        if (! secondIsAscending) {
            order = -1;
        }

        for (int i = 0; i < downloads.size(); i++) {
            for (int j = 0; j < i; j++) {
                Download d1 = downloads.get(i), d2 = downloads.get(j);
                if (firstPriority.compare(d1, d2) == 0 && secondPriority.compare(d1, d2) * order < 0) {
                    Collections.swap(downloads, i, j);
                }
            }
        }
    }

    public static Comparator getComparator(String item) {
        if (item.equals("By name")) {
            return downloadNameComparator;
        }
        if (item.equals("By size")) {
            return downloadSizeComparator;
        }
        if (item.equals("By creation time")) {
            return creationTimeComparator;
        }
        if (item.equals("By downloaded percentage")) {
            return downloadedPercentageComparator;
        }
        if (item.equals("By downloaded amount")) {
            return downloadedAmountComparator;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public int getSizeInBytes() {
        return sizeInBytes;
    }

    public int getDownloadedBytes() {
        return downloadedBytes;
    }

    public int getBytesPerSecond() {
        return bytesPerSecond;
    }

    public status getState() {
        return state;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public String getSaveTo() {
        return saveTo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setBytesPerSecond(int bytesPerSecond) {
        this.bytesPerSecond = bytesPerSecond;
    }

    public void setSizeInBytes(int sizeInBytes) {
        this.sizeInBytes = sizeInBytes;
    }

    public void setDownloadedBytes(int downloadedBytes) {
        this.downloadedBytes = downloadedBytes;
    }

    public void setState(status state) {
        this.state = state;
    }

    public void setSaveTo(String saveTo) {
        this.saveTo = saveTo;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public float getDownloadedPercentage(){
        if (sizeInBytes != 0){
            return (float)(int)(((float) downloadedBytes / (float) sizeInBytes) * 10000)/100;
        }
        return 0;
    }

    public static String makePrefix(int bytes) {
        if (bytes >= 0 && bytes < 1024){
            return bytes + "B";
        }
        else if (bytes >= 1024 && bytes < 1024*1024) {
            return (float)(int)((float)bytes/(float)1024 * 100) / 100 + "KB";
        }
        else if (bytes >= 1024*1024 && bytes < 1024*1024*1024) {
            return  (float)(int)(((float)bytes/(float) (1024*1024))*100)/100 + "MB";
        }
        else {
            return (float)(bytes/(1024*1024*1024)) + "GB";
        }
    }

    public String getProgress(){
        return makePrefix(downloadedBytes) + " / " + makePrefix(sizeInBytes);
    }

    public String getSpeed() {
        return makePrefix(bytesPerSecond) + "/S";
    }

    public void pause(){
        state = status.Paused;
    }

    public void resume(){
        state = status.Downloading;
    }

    public void cancel() {
        state = status.Cancelled;
    }

    public String fileFormat() {
        if (! link.contains(".") || link.lastIndexOf('.') == link.length()-1){
            return "file";
        }
        return link.substring(link.lastIndexOf('.'), link.length());
    }

    public void finish() {
        state = status.Finished;
    }

    public void openFile() {
        try {
            Desktop.getDesktop().open(new File(getSaveTo() + getName()));
        } catch (IOException e1) {
        }
    }

    public void openFolder() {
        try {
            Desktop.getDesktop().open(new File(getSaveTo()));
        } catch (IOException e1) {
        }
    }
}
