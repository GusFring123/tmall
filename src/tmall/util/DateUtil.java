package tmall.util;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @Package: tmall.util
 * @Author: WangXu
 * @CreateDate: 2018/3/8 13:51
 * @Version: 1.0
 * 而为了在MySQL中的日期格式里保存时间信息，必须使用datetime类型的字段，而jdbc要获取datetime类型字段的信息，需要采用java.sql.Timestamp来获取，否则只会保留日期信息，而丢失时间信息。
 */

public class DateUtil {
    public static Timestamp d2t(Date date) {
        if (null == date) return null;
        return new Timestamp(date.getTime());
    }

    public static Date t2d(Timestamp timestamp) {
        if (null == timestamp) return null;
        return new Date(timestamp.getTime());
    }
}
