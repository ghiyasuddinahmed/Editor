import java.time.LocalDateTime;

public class DatePlugin implements PluginHandle {

    @Override
    public LocalDateTime onClick() {

        LocalDateTime dateobj = LocalDateTime.now();
        return dateobj;

    }
}
