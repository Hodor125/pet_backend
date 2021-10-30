package com.hodor.util;

import org.springframework.util.StringUtils;
import java.util.Calendar;

/**
 * @Author limingli006
 * @Date 2021/10/30
 */
public class IdCardUtils {

    /**
     * 获取出生日期
     *
     * @param idCard 身份证号（分为15位和18位） 110101199003074872
     * @return birthday
     **/
    public static String getBirthday(String idCard){
        if (StringUtils.isEmpty(idCard)) {
            return "";
        }
        if (idCard.length() < 15) {
            return "";
        }
        return idCard.substring(6, 10) + "-" + idCard.substring(10, 12) + "-" + idCard.substring(12, 14);
    }

    /**
     * 获取年龄
     *
     * @param idCard 身份证号（分为15位和18位） 110101199003074872
     * @return age
     **/
    public static Integer getAge(String idCard) {
        Calendar cal = Calendar.getInstance();
        String year = idCard.substring(6, 10);
        int iCurrYear = cal.get(Calendar.YEAR);
        return iCurrYear - Integer.parseInt(year);
    }

    /**
     * 获取性别（1男 2女）
     *
     * @param idCard 身份证号（分为15位和18位） 110101199003074872
     * @return 性别
     */
    public static String getGender(String idCard) {
        //
        String sGender = "2";
        String sCardNum = idCard.substring(16, 17);
        if (Integer.parseInt(sCardNum) % 2 != 0) {
            sGender = "1";
        }
        return sGender;
    }

}

