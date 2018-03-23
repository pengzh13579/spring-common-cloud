package cn.pzh.common.config.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;

public class MD5Util {

    /**
     * 加密字符串
     */
    public static String EncoderStringByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        // 确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();

        // 加密后的字符串
        String newStr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newStr;
    }

    /**
     * 加密文件
     */
    public static String EncoderFileByMd5(File file) {

        String newstr = null;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            // 确定计算方法
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            byte[] buffer = new byte[1024];
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            int length = -1;
            while ((length = bis.read(buffer)) != -1) {
                md5.update(buffer, 0, length);
            }
            // 加密后的字符串
            newstr = base64en.encode(md5.digest());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseIOUtil.closeAll(bis, fis);
        }
        return newstr;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        System.out.println(EncoderStringByMd5("23"));
    }
}

