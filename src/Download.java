import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Time;
import java.util.*;


/**
 * Created by 9631815 on 5/12/2018.
 * This class implements a download in download manager
 */
public class Download implements Serializable{

    public enum status implements Serializable{
        Downloading, Paused, Cancelled, Finished, InQueue;
    }

    /**
     * instances of download class
     */
    private transient Downloader downloader;
    private String name;
    private String link;
    private status state;
    private Integer sizeInBytes;
    private Integer downloadedBytes;
    private Integer bytesPerSecond;
    private Date creationTime;
    private Date finishTime;
    private String saveTo;
    private Boolean isStarted;

    /**
     * Construcor for download class
     * @param name
     * @param link
     */

    public Download(String name, String link){
        sizeInBytes = 0;
        downloadedBytes = 0;
        bytesPerSecond = 0;
        saveTo = DownloadManager.getSettings().getSaveToPath();
        this.name = validName(name);
        this.link = link;
        state = status.Downloading;
        creationTime = Calendar.getInstance().getTime();
        downloader = new Downloader(this);
        isStarted = false;
    }


    /**
     * getter method for downloader field
     * @return
     */
    public Downloader getDownloader() {
        return downloader;
    }

    /**
     * setter method for downloaded field
     * @param downloader
     */
    public void setDownloader(Downloader downloader) {
        this.downloader = downloader;
    }

    /**
     * getter method for started field
     * @return
     */
    public Boolean getStarted() {
        return isStarted;
    }

    /**
     * comparator for download class
     */
    public static Comparator<Download> downloadNameComparator = new Comparator<Download>() {
        @Override
        public int compare(Download o1, Download o2) {
            //Ascending order
            int d = o1.getName().toUpperCase().compareTo(o2.getName().toUpperCase());
            return d;
        }
    };

    /**
     * comparator for download class
     */
    public static Comparator<Download> downloadSizeComparator = new Comparator<Download>() {
        @Override
        public int compare(Download o1, Download o2) {
            int d = o1.getSizeInBytes() - o2.getSizeInBytes();
            return d;
        }
    };

    /**
     * comparator for download class
     */
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

    /**
     * comparator for download class
     */
    public static Comparator<Download> downloadedPercentageComparator = new Comparator<Download>() {
        @Override
        public int compare(Download o1, Download o2) {
            return (int)(o1.getDownloadedPercentage()*100 - o2.getDownloadedPercentage()*100);
        }
    };

    /**
     * comparator for download class
     */
    public static Comparator<Download> downloadedAmountComparator = new Comparator<Download>() {
        @Override
        public int compare(Download o1, Download o2) {
            return o1.getDownloadedBytes() - o2.getDownloadedBytes();
        }
    };

    /**
     * This method sorts downloads
     */
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

    /**
     * This method returns proper comparator given a string
     * @param item
     * @return
     */
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

    /**
     * getter method for name field
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * getter method for link field
     * @return
     */
    public String getLink() {
        return link;
    }

    /**
     * getter method for size field
     * @return
     */
    public Integer getSizeInBytes() {
        return sizeInBytes;
    }

    /**
     * getter method for downloaded bytes field
     * @return
     */
    public Integer getDownloadedBytes() {
        return downloadedBytes;
    }

    /**
     * getter method for state field
     * @return
     */
    public status getState() {
        return state;
    }

    /**
     * getter method for creation field
     * @return
     */
    public Date getCreationTime() {
        return creationTime;
    }

    /**
     * getter method for finish time field
     * @return
     */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
     * getter method for saveto field
     * @return
     */
    public String getSaveTo() {
        return saveTo;
    }

    /**
     * setter method for link field
     * @param link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * setter method for bytesPerSecond field
     * @param bytesPerSecond
     */
    public void setBytesPerSecond(int bytesPerSecond) {
        this.bytesPerSecond = bytesPerSecond;
    }

    /**
     * setter method for size field
     * @param sizeInBytes
     */
    public void setSizeInBytes(int sizeInBytes) {
        this.sizeInBytes = sizeInBytes;
    }

    /**
     * setter method for downloaded bytes field
     * @param downloadedBytes
     */
    public void setDownloadedBytes(int downloadedBytes) {
        this.downloadedBytes = downloadedBytes;
    }

    /**
     * setter method for state field
     * @param state
     */
    public void setState(status state) {
        this.state = state;
    }

    /**
     * setter method for creation time field
     * @param creationTime
     */
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * getter method for percentage field
     * @return
     */
    public float getDownloadedPercentage(){
        if (sizeInBytes != 0){
            return (float)(int)(((float) downloadedBytes / (float) sizeInBytes) * 10000)/100;
        }
        return 0;
    }

    /**
     * This method makes proper postfix for download size
     * @param bytes
     * @return
     */
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

    /**
     * This method returns a string representing progression of download
     * @return
     */
    public String getProgress(){
        return makePrefix(downloadedBytes) + " / " + makePrefix(sizeInBytes);
    }

    /**
     * This method returns speed of download
     * @return
     */
    public String getSpeed() {
        return makePrefix(bytesPerSecond) + "/S";
    }

    /**
     * This method estimates remaining time to finish
     * @return
     */
    public Time estimatedTimeRemaining() {
        if (bytesPerSecond != 0) {
            int remainingBytes = sizeInBytes - downloadedBytes;
            int remainingSecs = remainingBytes / bytesPerSecond;
            Time time = new Time(0,0,remainingSecs);
            return time;
        }
        return null;
    }


    /**
     * this method resumes a download
     */
    public void resume(){
        state = status.Downloading;
        DownloadManager.updateUI();
        downloader.resume();
    }

    /**
     * this method starts a download for the first time
     */
    public void start() {
        state = status.Downloading;
        isStarted = true;
        DownloadManager.updateUI();
        downloader.start();
        //DownloadManager.getService().submit(downloader);
        //DownloadManager.getService().execute(downloader);
        //downloader.run();
    }

    /**
     * this method pauses a download
     */
    public void pause(){
        state = status.Paused;
        DownloadManager.updateUI();
        downloader.suspend();
    }

    /**
     * this method pauses the queue
     */
    public void pauseInQueue() {
        state = status.InQueue;
        DownloadManager.updateUI();
        downloader.suspend();
    }

    public void cancel() {
        pause();
        state = status.Cancelled;
    }

    /**
     * This method determines file format
     * @return
     */
    public String fileFormat() {
        if (! link.contains(".") || link.lastIndexOf('.') == link.length()-1){
            return "file";
        }
        return link.substring(link.lastIndexOf('.'), link.length());
    }

    /**
     * This method finishes a downlaod
     */
    public void finish() {
        state = status.Finished;
        DownloadEntry entry = new DownloadEntry(this);
        if (DownloadManager.getQueue().getModel().contains(entry)) {
            int index = DownloadManager.getQueue().getModel().indexOf(entry);
            if (index < DownloadManager.getQueue().getModel().size() - 1) {
                DownloadManager.getQueue().getModel().getElementAt(index + 1).getDownload().setState(status.Downloading);
                DownloadManager.getQueueService().execute(DownloadManager.getQueue().getModel().getElementAt(index + 1).getDownload().getDownloader());
            }
            else {
                JOptionPane.showMessageDialog(GUI.getFrame(), "Queue finished", "Message", 1);
            }
            DownloadManager.getQueue().getModel().removeElementAt(0);

        }
        finishTime = Calendar.getInstance().getTime();
        DownloadManager.getCompleted().addDownloadToList(this);
        DownloadManager.getProccessing().getModel().removeElement(new DownloadEntry(this));
        DownloadManager.updateUI();
    }


    /**
     * This method opens the file after the file is downloaded
     */
    public void openFile() {
        try {
            String format = fileFormat();
            if (getName().contains(format)) {
                format = "";
            }
            String path = getSaveTo() + File.separator + getName() + format;
            path.replaceAll("//","/");
            path.replaceAll("..",".");
            System.out.println(path);

            System.out.println(path);
            File file = new File(path);
            Desktop.getDesktop().open(file);
        } catch (IOException e1) {
        }
    }

    /**
     * This method opens the folder of download
     */
    public void openFolder() {
        try {
            Desktop.getDesktop().open(new File(getSaveTo()));
        } catch (IOException e1) {
        }
    }

    /**
     * This method gives a name and returns a valid name to be saved
     * @param name
     * @return
     */
    public static String validName(String name) {

        name = name.replaceAll("\\?", "_");
        name = name.replaceAll(">", "_");
        name = name.replaceAll("<", "_");
        name = name.replaceAll(":", "_");
        name = name.replaceAll("/", "_");
        name = name.replaceAll("\\*", "_");
        name = name.replaceAll("\"", "_");
        name = name.replaceAll("\\\\", "_");
        name = name.replaceAll("\\|", "_");
        return name;

    }

    /**
     * override of equals method
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        Download dl = (Download)obj;
        return (name.equals(dl.name) && sizeInBytes.equals(dl.sizeInBytes) && link.equals(dl.link) && creationTime.equals(dl.creationTime) && downloadedBytes.equals(dl.downloadedBytes));
    }
}