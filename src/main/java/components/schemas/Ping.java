package components.schemas;
import java.time.LocalDateTime;
public class Ping {
    private LocalDateTime serverTime = java.time.LocalDateTime.now();
    public Ping(){

    }
    public LocalDateTime getServerTime(){

        return serverTime;
    }
    public void setServerTime(LocalDateTime serverTime) {

        this.serverTime = serverTime;
    }
}
