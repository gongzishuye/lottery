package monitor.utils;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * Created by yfzhangbin on 15-5-6.
 */
public class DateUtil {
    private static final Logger log = Logger.getLogger(DateUtil.class);

    private final static String YYYYMMDD_000000 = "yyyy-MM-dd 00:00:00";
    private final static String YYYYMMDD_HH0000 = "yyyy-MM-dd HH:00:00";
    private final static String YYYYMMDD_HHmm00 = "yyyy-MM-dd HH:mm:00";
    private final static String YYYYMMDD_HHmmss = "yyyy-MM-dd HH:mm:ss";

    public static Date getTomorrow(Date date) {
        return getLastDays(date, -1);
    }

    public static Date getYesterday(Date date) {
        return getLastDays(date, 1);
    }

    public static Date getLastDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -days);
        return c.getTime();
    }

    public static Date getLastHours(Date date, int hours) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, -hours);
        return c.getTime();
    }

    public static Date getLastMinutes(Date date, int minutes) {
        long begin = System.currentTimeMillis();
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.MINUTE, -minutes);
            return c.getTime();
        } finally {
            log.error("DateUtil getLastMinutes("+date+", "+minutes+") expend " + (System.currentTimeMillis() - begin) + "ms");
        }
    }

    public static String formatDate(Date date, String format) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(d);
    }

    public static String formatDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD_HHmmss);
        return sdf.format(d);
    }

    public static String toYmdFromDate(Date date) {
        return formatDate(date, YYYYMMDD_000000);
    }

    public static String toYmdhFromDate(Date date) {
        return formatDate(date, YYYYMMDD_HH0000);
    }

    public static String toYmdhmFromDate(Date date) {
        long begin = System.currentTimeMillis();
        try {
            return formatDate(date, YYYYMMDD_HHmm00);
        } finally {
            log.error("DateUtil toYmdhmFromDate("+date+") expend " + (System.currentTimeMillis() - begin) + "ms");
        }
    }

    public static Date toDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    public static void main(String[] args) {
        /*String s = DateUtil.formatDate(new Date(), YYYYMMDD_HHmm00);
        System.out.println(s);
        s = DateUtil.formatDate(getLastHours(new Date(), 30), YYYYMMDD_HHmm00);
        System.out.println(s);*/

        Date currentDate = new Date();
        String beginDate = DateUtil.toYmdhmFromDate(currentDate);
        System.out.println(beginDate);
    }
}
