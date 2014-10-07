package trackingnumberwatcher;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

public class TrackingNumberWatcherApp extends SingleFrameApplication {

    @Override protected void startup() {
        show(new TrackingNumberWatcherView(this));
    }
    @Override protected void configureWindow(java.awt.Window root){
    }
    public static TrackingNumberWatcherApp getApplication() {
        return Application.getInstance(TrackingNumberWatcherApp.class);
    }
    public static void main(String[] args) {
        launch(TrackingNumberWatcherApp.class, args);
    }
}
