package com.productmanage.util;

import com.productmanage.bean.Product;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

public class Test {

    public static void main(String[] args) {
        String pwd1 = StringUtil.encrypt("11111");
        System.out.println(pwd1);
        Double b = 1.8279689356E10;
        System.out.printf("%.0f", b);
        String a = String.format("%.0f", b);
        System.out.println(a);
        System.out.println(StringUtil.encrypt("121212"));
        System.out.println(Double.MAX_VALUE > 99999999999.0);
        Integer k = 9;
        System.out.println(k instanceof Integer);
        System.out.println(test());
    }

    private static Object test(){
        try{
            String str = "fdgdfgd".trim();
            str.substring(-1);
            System.out.println(str);
        }catch (Exception e){
            return 3;
        }
        return 0;
    }


}
