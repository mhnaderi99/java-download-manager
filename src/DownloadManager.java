public class DownloadManager {

    private GUI gui;
    private DownloadsList completed, proccessing;


    public DownloadManager() {
        gui = new GUI();
        gui.showMainFrame();
        completed = new DownloadsList(true);
        proccessing = new DownloadsList(false);
    }
}
