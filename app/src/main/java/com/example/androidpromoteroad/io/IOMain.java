package com.example.androidpromoteroad.io;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * author: created by wentaoKing
 * date: created in 1/19/21
 * description: java io example
 */
class IOMain {

    private static final String path = "./app/src/main/java/com/example/androidpromoteroad/io/io.text";

    public static void main(String[] args) {
//        fileOutPutStream();
//        fileInputStream();
//        bufferReader();
//        bufferInputStream();
//        bufferOutputStream();
//        socket();
//        serverSocket();
//        nioBlocking();
//        nioUnBlocking();
//        okIo();
        writeFileByOKio();
        readFileByOKio();
    }

    private static void fileInputStream() {
        try (InputStream inputStream = new FileInputStream(path)) {
            System.out.println((char) inputStream.read());
            System.out.println((char) inputStream.read());
            System.out.println((char) inputStream.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fileOutPutStream() {
        try (OutputStream inputStream = new FileOutputStream(path)) {
            inputStream.write('w');
            inputStream.write('t');
            inputStream.write('k');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void bufferReader() {
        try (InputStream inputStream = new FileInputStream(path);
             Reader reader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(reader);
        ) {
            System.out.println(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void bufferInputStream() {
        try (InputStream inputStream = new FileInputStream(path);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        ) {
            System.out.println(bufferedInputStream.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void bufferOutputStream() {
        try (OutputStream outputStream = new FileOutputStream(path);
        ) {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            bufferedOutputStream.write('a');
            bufferedOutputStream.write('d');
            //使用BufferedOutputStream时，要记得flush。因为这是缓冲输出流的功能特性，在没有满的情况下，
            // 需要主动冲洗缓冲区，把内容写入到内存中（设置遮盖缓冲的目的，是为了减少与内存之间的频繁交互，提升性能）
            bufferedOutputStream.flush();
            //close也会去flush，把缓冲区冲洗掉
            //bufferedOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void socket() {
        try {
            Socket socket = new Socket("hencoder.com", 80);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer.write("GET / HTTP/1.1\n Host: www.example.com\n\n");
            writer.flush();
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void serverSocket() {
        try {
            //实现一个服务端socket
            ServerSocket serverSocket = new ServerSocket(80);
            Socket socket = serverSocket.accept();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer.write("HTTP/1.1 200 OK\n" +
                    "Date: Mon, 23 May 2005 22:38:34 GMT\n" +
                    "Content-Type: text/html; charset=UTF-8\n" +
                    "Content-Length: 138\n" +
                    "Last-Modified: Wed, 08 Jan 2003 23:11:55 GMT\n" +
                    "Server: Apache/1.3.3.7 (Unix) (Red-Hat/Linux)\n" +
                    "ETag: \"3f80f-1b6-3e1cb03b\"\n" +
                    "Accept-Ranges: bytes\n" +
                    "Connection: close\n" +
                    "\n" +
                    "<html>\n" +
                    "  <head>\n" +
                    "    <title>An Example Page</title>\n" +
                    "  </head>\n" +
                    "  <body>\n" +
                    "    <p>Hello World, this is a very simple HTML document.</p>\n" +
                    "  </body>\n" +
                    "</html>\n\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * nio的阻塞式用法
     */
    private static void nioBlocking() {
        try {
            RandomAccessFile file = new RandomAccessFile(path, "r");
            FileChannel channel = file.getChannel();
            //note：nio需要自己操作buffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            channel.read(byteBuffer);
            //flip后才能读，修正limit和position标志
            byteBuffer.flip();
            System.out.println(Charset.defaultCharset().decode(byteBuffer));
            //读完后要重置
            byteBuffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * nio的非阻塞式：只支持网络交互的io操作
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private static void nioUnBlocking() {
        try {
            //可用命令连接:telnet localhost 80
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(80));
            //把channel配置成非阻塞式的
            serverSocketChannel.configureBlocking(false);
            //selector: 类似一个监听器
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select();
                for (SelectionKey key : selector.selectedKeys()) {
                    if (!key.isAcceptable()) {
                        break;
                    }
                    //accept()会阻塞
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    //写入: 把读到的值返回
                    while (socketChannel.read(byteBuffer) != -1) {
                        byteBuffer.flip();
                        socketChannel.write(byteBuffer);
                        byteBuffer.clear();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * OKio的使用
     * 对于OKio来说，buffer是个外部工具而已，和nio不一样，OKio要把数据写入到buffer，需要通过source的read方法
     */
    private static void okIo() {
        try (Source source = Okio.source(new File(path))) {
            Buffer buffer = new Buffer();
            //把source的数据写入到buffer里面去
            source.read(buffer, 1024);
            System.out.println("okio buffer read:" + buffer.readUtf8Line());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * OKio读文件
     */
    private static void readFileByOKio() {
        try (Source source = Okio.source(new File(path));
             BufferedSource bufferedSource = Okio.buffer(source)) {
            for (String line; (line = bufferedSource.readUtf8Line()) != null; ) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * OKio写文件
     */
    private static void writeFileByOKio() {
        try (Sink sink = Okio.sink(new File(path));
             BufferedSink bufferedSink = Okio.buffer(sink)) {
            bufferedSink.writeUtf8("write" + "\n" + "success!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
