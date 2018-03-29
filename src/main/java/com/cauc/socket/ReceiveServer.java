package com.cauc.socket;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiveServer {
    public static void main(String[] args) {
        ReceiveServer receiveServer = new ReceiveServer();
        receiveServer.receive();
    }
    public void receive(){
        try {
            //1.创建一个服务器端Socket，即serverSocket，指定绑定的端口，并监听此端口。
            ServerSocket serverSocket=new ServerSocket(60008,0, InetAddress.getByName("192.168.1.103"));
            //2.调用serverSocket的accept（）方法，等待客户端的连接
            System.out.println("==服务器即将启动，等待客户端的连接==");
            Socket socket=serverSocket.accept();
            System.out.println("New connection accepted "+
                    socket.getInetAddress()+":"+socket.getPort());
            //3.获取输入流用来读取客户端所发出的登录信息
            InputStream inputStream=socket.getInputStream();//字节输入流
            Reader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
//            InputStreamReader isr=new InputStreamReader(is);//将字节流转化为字符流
//            BufferedReader bufferedReader=new BufferedReader(isr);//为字符流添加缓冲
            String info=null;
            //循环读取客户端提交的信息
            while((info=bufferedReader.readLine())!=null){
                System.out.println("我是服务器，客户端提交的信息是："+info);
            }
            socket.shutdownInput();
//            4.获取输出流，响应客户端的请求
            OutputStream os= socket.getOutputStream();
            PrintWriter pw=new PrintWriter(os);//转化为打印流
            pw.write("ok");
            pw.flush();//刷新缓存
//            5.关闭相关的资源
            bufferedReader.close();
            inputStream.close();
//            isr.close();
            socket.close();
            serverSocket.close();
            os.close();
            pw.close();
        } catch (IOException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
