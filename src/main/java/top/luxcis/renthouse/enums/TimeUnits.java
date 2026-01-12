package top.luxcis.renthouse.enums;

/**
 * @author zhuang
 */
@SuppressWarnings("unused")
public enum TimeUnits {
    /**
     * 一毫秒
     */
    MS(1),
    /**
     * 一秒的毫秒数
     */
    SECOND(MS.getMillis() * 1000),
    /**
     * 一分钟的毫秒数
     */
    MINUTE(SECOND.getMillis() * 60),
    /**
     * 一小时的毫秒数
     */
    HOUR(MINUTE.getMillis() * 60),
    /**
     * 一天的毫秒数
     */
    DAY(HOUR.getMillis() * 24),
    /**
     * 一周的毫秒数
     */
    WEEK(DAY.getMillis() * 7);

    private final long millis;

    TimeUnits(long millis) {
        this.millis = millis;
    }

    /**
     * @return 单位对应的毫秒数
     */
    public long getMillis() {
        return getMillis(1);
    }

    /**
     * 获取指定数值单位的毫秒<br>
     * 例：TimeUnits.MINUTE.getMillis(5) 获取5分钟对应的毫秒
     *
     * @param num 数值
     * @return long
     */
    public long getMillis(int num) {
        return this.millis * num;
    }

    /**
     * @return 单位对应的秒数
     */
    public int getSeconds() {
        return Math.toIntExact(this.millis / SECOND.millis);
    }

    /**
     * 获取指定数值单位的秒<br>
     * 例：TimeUnits.MINUTE.getMillis(5) 获取5分钟对应的秒
     *
     * @param num 数值
     * @return int
     */
    public int getSeconds(int num) {
        return Math.toIntExact(this.millis / SECOND.millis) * num;
    }
}
