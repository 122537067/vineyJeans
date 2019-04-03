package com.hwx.viney.oneUtils;

import org.springframework.stereotype.Component;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @author one
 * @date 2019/2/14 0021 上午 10:39
 */
@Component
public class TimeUtil {
    /**
     * 格式化日期输出
     * @param data
     * @return
     */
    public static String get_SFD_Time(Date data){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(data);
    }


    /**
     * 判断两个日期大小、若d1<d2 返回true 否则返回false
     * @param d1
     * @param d2
     * @return
     */
    public boolean boolTime(Date d1,Date d2){
        String[] temp;
        temp=this.getYMD(d1).split("-");
        int date1=new Integer(temp[0]+temp[1]+temp[2]);
        temp=this.getYMD(d2).split("-");
        int date2=new Integer(temp[0]+temp[1]+temp[2]);
        if(date1<date2){
            return true;
        }
        return false;
    }

    public String getYMD(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str=sdf.format(date);
        return str.split(" ")[0];
    }

    /**
     * 获得n个月后的日期
     * @param now
     * @param num
     * @return
     */
    public Date getAfterDate(Date now,int num){
        //创建一个日期实例
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MONTH,num);//在日历的月份上增加6个月
        return  calendar.getTime();
    }

    /**
     * 获得n个月前的日期
     * @param now
     * @param num
     * @return
     */
    public Date getBeforeDate(Date now,int num){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        //过去n个月
        c.setTime(new Date());
        c.add(Calendar.MONTH, -num);
        Date m = c.getTime();
        String mon = format.format(m);
        return strToDateLong(mon);
    }

    /**
     * 将前端转过来的格式化后的日期转回Date
     * @param strDate
     * @return
     */
    public Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 计算时间差
     * @param minuend       被减数
     * @param subtrahend    减数
     * @return  天,小时,分钟
     */
    public String getDatePoor(Date minuend, Date subtrahend) {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = minuend.getTime() - subtrahend.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        //System.out.println(day + "天" + hour + "小时" + min + "分钟");
        return day + "," + hour + "," + min;
    }

    /**
     * 生成时间戳随机数
     * @param tailNumber 时间戳+后面几位随机数
     * @return
     */
    public String dateToRandom(int tailNumber){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<tailNumber;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }

    /**
     * dateBig比dateLittle多的天数  2018-06-06 00:00:01 与 2018-06-05 23:59:59 相差一天
     * @param dateLittle        2018-06-05 23:59:59
     * @param dateBig           2018-06-06 00:00:01
     * @return                  1
     */
    public int differentDays(Date dateLittle,Date dateBig)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(dateLittle);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(dateBig);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            return day2-day1;
        }
    }

    /**
     * 获取n天前/后 的时间
     * @param day               一天前(day = -1)  一天后(day = 1)
     * @return                          2019-06-05
     */
    public String getNdayDate(int day){
        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        calendar1.add(Calendar.DATE, day);
        String days_ago = sdf1.format(calendar1.getTime());
        return days_ago;
    }

}
