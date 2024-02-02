package lk.ijse.liveChat.utill;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    public static String dateNow() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }

    public static String timeNow() {
        SimpleDateFormat dateFormat=new SimpleDateFormat("hh:mm:ss");
        return dateFormat.format(new Date()) ;
    }
}
