import java.time.LocalDateTime;

public class DatePlugin implements DatePluginHandle {

    @Override
    public LocalDateTime onClick() {

        LocalDateTime dateobj = LocalDateTime.now();
        return dateobj;

    }
}
