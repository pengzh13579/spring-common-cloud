package cn.pzh.common.config.utils;

import java.util.Random;

public class IdUtils {

    /**
     * 随机生成盐值
     *
     * @param num 盐值得长度（num）
     */
    public static String salt(int num) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+-@#$%^&*()";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < num; ++j) {
            int number = random.nextInt(73);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
