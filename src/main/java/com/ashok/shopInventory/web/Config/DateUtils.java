package com.ashok.shopInventory.web.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class DateUtils {
    private static final Logger LOG = LoggerFactory.getLogger(DateUtils.class);
    private static final TimeZone IST = TimeZone.getTimeZone("IST");
    public static TimeZone DEFAULT_TZ;

    public DateUtils() {
    }

    public static Date stringToDate(String date, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);

        try {
            return format.parse(date);
        } catch (ParseException var4) {
            LOG.debug(var4.getMessage());
            return null;
        }
    }

    public static String dateToString(Date date, String pattern) {
        if (date != null && StringUtils.hasText(pattern)) {
            DateFormat format = new SimpleDateFormat(pattern);
            return format.format(date);
        } else {
            return null;
        }
    }

    public static boolean isPastTime(Date input) {
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTime(getCurrentTime());
        Calendar inputTime = Calendar.getInstance();
        inputTime.setTime(input);
        return inputTime.before(currentTime);
    }

    public static boolean after(Date checkDate, Date givenDate) {
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTime(givenDate);
        Calendar inputTime = Calendar.getInstance();
        inputTime.setTime(checkDate);
        return inputTime.after(currentTime);
    }

    public static boolean isFutureTime(Date input) {
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTime(getCurrentTime());
        Calendar inputTime = Calendar.getInstance();
        inputTime.setTime(input);
        return inputTime.after(currentTime);
    }

    public static Date getCurrentTime() {
        return new Date();
    }

    public static Date getCurrentDate() {
        Calendar now = Calendar.getInstance();
        now.set(now.get(1), now.get(2), now.get(5), 0, 0, 0);
        now.set(14, 0);
        return now.getTime();
    }


    public static Date createDate(int year, int month, int day) {
        Calendar date = Calendar.getInstance();
        date.set(year, month - 1, day, 0, 0, 0);
        return date.getTime();
    }

    public static boolean isDNDHour() {
        Calendar now = Calendar.getInstance();
        return now.get(11) >= 21 || now.get(11) < 9;
    }

    public static Date addToDate(Date date, int type, int noOfUnits) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, noOfUnits);
        return calendar.getTime();
    }

    public static Date round(Date date, Resolution resolution) {
        return new Date(round(date.getTime(), resolution));
    }

    public static long round(long time, Resolution resolution) {
        return round(time, resolution, DEFAULT_TZ);
    }

    public static long round(long time, Resolution resolution, TimeZone tz) {
        Calendar cal = Calendar.getInstance(tz == null ? DEFAULT_TZ : tz);
        cal.setTime(new Date(time));
        if (resolution == Resolution.YEAR) {
            cal.set(2, 0);
            cal.set(5, 1);
            cal.set(11, 0);
            cal.set(12, 0);
            cal.set(13, 0);
            cal.set(14, 0);
        } else if (resolution == Resolution.MONTH) {
            cal.set(5, 1);
            cal.set(11, 0);
            cal.set(12, 0);
            cal.set(13, 0);
            cal.set(14, 0);
        } else if (resolution == Resolution.DAY) {
            cal.set(11, 0);
            cal.set(12, 0);
            cal.set(13, 0);
            cal.set(14, 0);
        } else if (resolution == Resolution.HOUR) {
            cal.set(12, 0);
            cal.set(13, 0);
            cal.set(14, 0);
        } else if (resolution == Resolution.MINUTE) {
            cal.set(13, 0);
            cal.set(14, 0);
        } else if (resolution == Resolution.SECOND) {
            cal.set(14, 0);
        } else if (resolution != Resolution.MILLISECOND) {
            throw new IllegalArgumentException("unknown resolution " + resolution);
        }

        return cal.getTime().getTime();
    }



    public static DateRange getDayRange(Date anytime) {
        Date startTime = round(anytime, Resolution.DAY);
        return new DateRange(startTime, addToDate(startTime, 5, 1));
    }

    public static DateRange getLastDayRange() {
        return getDayRange(addToDate(getCurrentTime(), 5, -1));
    }

    public static Date parseTime(String token) {
        if (token != null && !"".equals(token)) {
            Calendar cal = new GregorianCalendar();
            cal.clear();
            char[] ctoken = token.toCharArray();
            StringBuilder hours = new StringBuilder(2);
            StringBuilder mins = new StringBuilder(2);
            int i;
            if (token.indexOf(":") < 0) {
                for(i = 0; i < ctoken.length; ++i) {
                    if (Character.isDigit(ctoken[i]) && i + 1 < ctoken.length && Character.isDigit(ctoken[i + 1]) && i + 2 < ctoken.length && Character.isDigit(ctoken[i + 2]) && i + 3 < ctoken.length && Character.isDigit(ctoken[i + 3])) {
                        hours.append(ctoken[i]).append(ctoken[i + 1]);
                        mins.append(ctoken[i + 2]).append(ctoken[i + 3]);
                    }
                }
            } else {
                for(i = 0; i < ctoken.length; ++i) {
                    if (ctoken[i] == ':') {
                        if (i - 1 >= 0 && Character.isDigit(ctoken[i - 1])) {
                            if (i - 2 >= 0 && Character.isDigit(ctoken[i - 2])) {
                                hours.append(ctoken[i - 2]).append(ctoken[i - 1]);
                            } else {
                                hours.append(ctoken[i - 1]);
                            }
                        } else if (i - 2 >= 0 && Character.isDigit(ctoken[i - 2])) {
                            if (i - 3 >= 0 && Character.isDigit(ctoken[i - 3])) {
                                hours.append(ctoken[i - 3]).append(ctoken[i - 2]);
                            } else {
                                hours.append(ctoken[i - 2]);
                            }
                        }

                        if (i + 1 < ctoken.length && Character.isDigit(ctoken[i + 1])) {
                            if (i + 2 < ctoken.length && Character.isDigit(ctoken[i + 2])) {
                                mins.append(ctoken[i + 1]).append(ctoken[i + 2]);
                            } else {
                                mins.append(ctoken[i + 1]);
                            }
                        } else if (i + 2 < ctoken.length && Character.isDigit(ctoken[i + 2])) {
                            if (i + 3 < ctoken.length && Character.isDigit(ctoken[i + 3])) {
                                mins.append(ctoken[i + 2]).append(ctoken[i + 3]);
                            } else {
                                mins.append(ctoken[i + 2]);
                            }
                        }
                        break;
                    }
                }
            }

            try {
                i = Integer.parseInt(hours.toString());
                int minutes = Integer.parseInt(mins.toString());
                if ((token.contains("pm") || token.contains("PM")) && i != 12) {
                    i += 12;
                    i %= 24;
                }

                if ((token.contains("am") || token.contains("AM")) && i == 12) {
                    i = 0;
                }

                cal.set(11, i);
                cal.set(12, minutes);
            } catch (NumberFormatException var7) {
                LOG.debug("Could not parse " + hours + ":" + mins + " as time. Initial token was " + token);
                return null;
            }

            return cal.getTime();
        } else {
            return null;
        }
    }

    public static long getDateDiff(Date current, Date dealtime) {
        long diff = current.getTime() - dealtime.getTime();
        diff /= 3600000L;
        return diff;
    }

    public static long getNextInterval(Date current, Date reference, long interval) {
        return current.getTime() + (interval - (current.getTime() - reference.getTime()) % interval);
    }

    public static Date getDateTimeAfterGivenWorkingDays(Date fromDate, int noOfDays) {
        int count = 0;
        Calendar c1 = Calendar.getInstance();
        if (fromDate != null) {
            c1.setTime(fromDate);

            while(count < noOfDays) {
                c1.add(5, 1);
                if (c1.get(7) != 7 && c1.get(7) != 1) {
                    ++count;
                }
            }

            return c1.getTime();
        } else {
            return null;
        }
    }

    public static Date getDateAfterGivenWorkingDays(Date fromDate, int noOfDays) {
        int count = 0;
        Calendar c1 = Calendar.getInstance();
        if (fromDate == null) {
            return null;
        } else {
            c1.setTime(fromDate);

            while(count < noOfDays) {
                c1.add(5, 1);
                ++count;
            }

            return c1.getTime();
        }
    }

    public static Date getDateBeforeGivenWorkingDays(Date fromDate, int noOfDays) {
        int count = noOfDays;
        Calendar c1 = Calendar.getInstance();
        if (fromDate == null) {
            return null;
        } else {
            c1.setTime(fromDate);

            while(count > 0) {
                c1.add(5, -1);
                --count;
            }

            return c1.getTime();
        }
    }

    public static boolean isExpiringIn24Hrs(Date endTime) {
        return endTime.after(getCurrentTime()) && endTime.before(addToDate(getCurrentTime(), 12, 1440));
    }

    public static Date getStartOfMonth(int numOfMonths) {
        Calendar c = Calendar.getInstance();
        c.add(2, -numOfMonths);
        c.set(5, c.getActualMinimum(5));
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        c.set(14, 0);
        return c.getTime();
    }

    public static Date getEndOfMonth(int numOfMonths) {
        Calendar c = Calendar.getInstance();
        c.add(2, -numOfMonths);
        c.set(5, c.getActualMaximum(5));
        c.set(11, 23);
        c.set(12, 59);
        c.set(13, 59);
        c.set(14, 999);
        return c.getTime();
    }

    public static Date getStartOfDay(Date date) {
        Calendar startTime = Calendar.getInstance();
        startTime.setTime(date);
        startTime.set(11, 0);
        startTime.set(12, 0);
        startTime.set(13, 0);
        startTime.set(14, 0);
        return startTime.getTime();
    }

    public static Date getEndOfDay(Date date) {
        Calendar endTime = Calendar.getInstance();
        endTime.setTime(date);
        endTime.set(11, 23);
        endTime.set(12, 59);
        endTime.set(13, 59);
        endTime.set(14, 999);
        return endTime.getTime();
    }

    public static void main(String[] args) {
        Date date = getCurrentTime();
        System.out.println(date);
        System.out.println(getWeekFormatForDate(date));
        System.out.println(getFornightFormatForDate(getCurrentTime()));
        System.out.println(date);
    }

    public static Date getDateAfterGivenWorkingDays(Date fromDate, int noOfDays, Set<Integer> weekends) {
        int count = 0;
        Calendar c1 = Calendar.getInstance();
        if (fromDate == null) {
            return null;
        } else {
            c1.setTime(fromDate);

            while(count < noOfDays) {
                c1.add(5, 1);
                boolean skipDate = false;
                Iterator i$ = weekends.iterator();

                while(i$.hasNext()) {
                    Integer weekend = (Integer)i$.next();
                    if (c1.get(7) == weekend) {
                        skipDate = true;
                        break;
                    }
                }

                if (!skipDate) {
                    ++count;
                }
            }

            return c1.getTime();
        }
    }

    public static String getFornightFormatForDate(Date date) {
        Calendar endTime = Calendar.getInstance();
        endTime.setTime(date);
        return (endTime.get(5) > 15 ? "2nd" : "1st") + " Half of " + (new SimpleDateFormat("MMMM")).format(date);
    }

    public static String getWeekFormatForDate(Date date) {
        Calendar endTime = Calendar.getInstance();
        endTime.setTime(date);
        return endTime.get(4) + getDayOfMonthSuffix(endTime.get(4)) + " Week Of " + (new SimpleDateFormat("MMMM")).format(date);
    }

    private static String getDayOfMonthSuffix(int n) {
        String suffix = "th";
        if (n != 1 && n != 21 && n != 31) {
            if (n != 2 && n != 22) {
                if (n == 3 || n == 23) {
                    suffix = "rd";
                }
            } else {
                suffix = "nd";
            }
        } else {
            suffix = "st";
        }

        return suffix;
    }

    static {
        DEFAULT_TZ = IST;
    }

    public static class DateRange {

        @NotNull
        private Date start;
        @NotNull
        private Date end;

        public DateRange() {
        }

        public DateRange(Date start, Date end) {
            this.start = start;
            this.end = end;
        }

        public Date getStart() {
            return this.start;
        }

        public Date getEnd() {
            return this.end;
        }

        public void setStart(Date start) {
            this.start = start;
        }

        public void setEnd(Date end) {
            this.end = end;
        }

        public String toString() {
            return this.start + " - " + this.end;
        }
    }

    public static final class Interval {
        private final TimeUnit timeUnit;
        private final long period;

        public Interval(TimeUnit timeUnit, long period) {
            this.timeUnit = timeUnit;
            this.period = period;
        }

        public TimeUnit getTimeUnit() {
            return this.timeUnit;
        }

        public long getPeriod() {
            return this.period;
        }

    }

    public static class Resolution {
        public static final Resolution YEAR = new Resolution("year");
        public static final Resolution MONTH = new Resolution("month");
        public static final Resolution DAY = new Resolution("day");
        public static final Resolution HOUR = new Resolution("hour");
        public static final Resolution MINUTE = new Resolution("minute");
        public static final Resolution SECOND = new Resolution("second");
        public static final Resolution MILLISECOND = new Resolution("millisecond");
        private String resolution;

        private Resolution() {
        }

        private Resolution(String resolution) {
            this.resolution = resolution;
        }

        public String toString() {
            return this.resolution;
        }
    }
}

