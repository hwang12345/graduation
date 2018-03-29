package com.cauc.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author whn6325689
 */
public class server {//服务器端
    public static void main(String[] args) {
        try {
            //1.创建一个服务器端Socket，即serverSocket，指定绑定的端口，并监听此端口。
            ServerSocket serverSocket=new ServerSocket(60008,0,InetAddress.getByName("192.168.1.103"));
            //2.调用serverSocket的accept（）方法，等待客户端的连接
            System.out.println("==服务器即将启动，等待客户端的连接==");
            Socket socket=serverSocket.accept();
            System.out.println("New connection accepted "+
                    socket.getInetAddress()+":"+socket.getPort());
            //3.获取输入流用来读取客户端所发出的登录信息
            InputStream is=socket.getInputStream();//字节输入流
            InputStreamReader isr=new InputStreamReader(is);//将字节流转化为字符流
            //为字符流添加缓冲
            BufferedReader bufferedReader=new BufferedReader(isr);
            String info=null;
            //循环读取客户端提交的信息
            while((info=bufferedReader.readLine())!=null){
                System.out.println("我是服务器，客户端提交的信息是："+info);
            }
            socket.shutdownInput();
            //4.获取输出流，响应客户端的请求
            OutputStream os= socket.getOutputStream();
            PrintWriter pw=new PrintWriter(os);//转化为打印流
            pw.write("欢迎您！");
            pw.flush();//刷新缓存
            //5.关闭相关的资源
            bufferedReader.close();
            is.close();
            isr.close();
            socket.close();
            serverSocket.close();
            os.close();
            pw.close();
        } catch (IOException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}