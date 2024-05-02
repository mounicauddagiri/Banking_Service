package components.schemas;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ping {
    private LocalDateTime serverTime = java.time.LocalDateTime.now();

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    public Ping(){

    }
    public String getServerTime(){

        return serverTime.format(formatter);
    }
}
