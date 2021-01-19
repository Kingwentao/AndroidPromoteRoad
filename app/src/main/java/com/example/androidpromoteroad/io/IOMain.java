package com.example.androidpromoteroad.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

/**
 * author: created by wentaoKing
 * date: created in 1/19/21
 * description: java io example
 */
class IOMain {

    private static final String path = "./app/src/main/java/com/example/androidpromoteroad/io/io.text";

    public static void main(String[] args) {
        //fileOutPutStream();
        //fileInputStream();
        //bufferReader();
        //bufferInputStream();
        bufferOutputStream();
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
}
