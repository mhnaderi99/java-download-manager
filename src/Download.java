import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by 9631815 on 5/12/2018.
 */
public class Download implements Serializable{

    public enum status {
        Downloading, Paused, Cancelled, Finished;
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
        saveTo = "Desktop";
        this.name = name;
        this.link = link;
        downloadedBytes = 0;
        creationTime = Calendar.getInstance().getTime();
    }

    public Download(String name, String link, String saveTo){
        this(name, link);
        this.saveTo = saveTo;
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

    public void finish() {
        state = status.Finished;
    }
}
