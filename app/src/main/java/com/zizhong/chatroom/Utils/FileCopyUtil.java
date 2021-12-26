package com.zizhong.chatroom.Utils;

import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileCopyUtil {
    /**
     * 复制单个文件
     *
     * @param oldPath 原文件路径 如：c:/fqf.txt String
     * @param newPath 复制后路径 如：f:/fqf.txt
     */
    public static void copyFileVideos(String oldPath, String newPath, Handler handler, int size) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {//文件存在时
                InputStream inStream = new FileInputStream(oldPath);//读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
//                int length;
//                int value = 0 ;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;//字节数 文件大小
//                    value ++ ;  //计数
                    System.out.println("完成" + bytesum);
                    fs.write(buffer, 0, byteread);

//                    Thread.sleep(10);//每隔10ms发送一消息，也就是说每隔10ms value就自增一次，将这个value发送给主线程处理
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错" + e);
            e.printStackTrace();
        } finally {
            // 复制完毕一张后，发送消息给handler，更新进度条
            Message msg  = new Message(); //创建一个msg对象
            msg.what =110 ;
            msg.arg1 = size ; //当前的value
            handler.sendMessage(msg) ;
        }
    }

    /**
     * 复制单个文件
     *
     * @param oldPath 原文件路径 如：c:/fqf.txt String
     * @param newPath 复制后路径 如：f:/fqf.txt
     */
    public static void copyFileImages(String oldPath, String newPath, Handler handler, int size) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {//文件存在时
                InputStream inStream = new FileInputStream(oldPath);//读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
//                int length;
//                int value = 0 ;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;//字节数 文件大小
//                    value ++ ;  //计数
                    System.out.println("完成" + bytesum);
                    fs.write(buffer, 0, byteread);

//                    Thread.sleep(10);//每隔10ms发送一消息，也就是说每隔10ms value就自增一次，将这个value发送给主线程处理
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错" + e);
            e.printStackTrace();
        } finally {
            // 复制完毕一张后，发送消息给handler，更新进度条
            Message msg1  = new Message(); //创建一个msg对象
            msg1.what =111 ;
            msg1.arg1 = size ; //当前的value
            handler.sendMessage(msg1) ;
        }
    }
}
