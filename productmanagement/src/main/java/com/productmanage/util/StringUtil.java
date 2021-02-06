package com.productmanage.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class StringUtil {

    // 此方法将service中多处删除操作需要使用的代码提取出来作为工具
    public static String getResultString(StringBuffer delFail, StringBuffer delSuccess){
        StringBuffer result = new StringBuffer("");
        if(delFail.length() <= 0 && delSuccess.length() > 0){
            result.append(delSuccess.insert(0, "成功删除：").toString());
        }
        if(delFail.length() > 0 && delSuccess.length() <= 0){
            result.append(delFail.append("删除失败").toString());
        }
        if(delFail.length() > 0 && delSuccess.length() > 0){
            result.append(delSuccess.insert(0, "成功删除：").append("；").toString())
                    .append(delFail.append("删除失败").toString());
        }
        return result.toString();
    }

    // 获取最新编号时的工具方法
    public static String getIdStr(int index){
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString().split("-")[index];
        return str;
    }

    // 密码加密
    public static String encrypt(String password) {
        String key = "QWEdsazxcvbnmR123qwertTYUIyuiop45lkjhgf6OPL789KJHGFDSAZXCVBNM";
        int keyLen = key.length();
        StringBuffer after = new StringBuffer("");
        Random random = new Random();
        for(int i = 0; i < password.length(); i++) {
            int ran = random.nextInt(keyLen);
            after.append((char)((int)password.charAt(i) + 4))
                 .append((char)((int)key.charAt(ran) + 4));
        }
        return after.toString();
    }

    // 密码解密
    public static String decrypt(String password) {
        StringBuffer mima = new StringBuffer("");
        for(int j = 0; j < password.length(); j = j + 2) {
            int jie = (int)password.charAt(j) - 4;
            mima.append((char)jie);
        }
        return mima.toString();
    }

    // 日期转换为字符串
    public static String formatDate(Date date, int type){
        String[] format = {"yyyy/MM/dd","yyyy/MM/dd HH:mm:ss"};
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format[type]);
        if(date == null){
            return "- - -";
        }
        return simpleDateFormat.format(date);
    }

    //
    public static boolean isRightId(String Id, int type){
        if(Id.indexOf("admin") < 0 && Id.indexOf("inventory") < 0 && Id.indexOf("depot") < 0){
            return false;
        }
        return true;
    }

}
