package ua.mainacademy.service;

import ua.mainacademy.exceptiom.MyOwnException;
import ua.mainacademy.model.ConnectionInfo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileManager {

    private static final String MAIN_DIR = System.getProperty("user.dir");
    private static final String SEPARATOR = System.getProperty("file.separator");
    private static final String FILES_DIR = MAIN_DIR+SEPARATOR+"files";

    public static synchronized void writeConnectionInfoToFile (ConnectionInfo connectionInfo, String fileName, boolean append) {
        checkFileDir();
        String filePath = FILES_DIR + SEPARATOR + fileName;

        try {
            FileWriter fileWriter = new FileWriter(filePath, append);
            fileWriter.write(connectionInfo.toString() + "\n");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkFileDir() {
        File file = new File(FILES_DIR);
        if (!file.exists()){
            file.mkdir();
        }

    }

    public static List<ConnectionInfo> readConnectionInfoFromFile (String fileName) {
        List<ConnectionInfo> result = new ArrayList<>();
        String filePath = FILES_DIR + SEPARATOR + fileName;

        if(isNotExist(filePath)){
            throw new MyOwnException("Sorry , can`t find file");
        }
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line=bufferedReader.readLine()) != null ) {
                String[] elements = line.split(" ");
                ConnectionInfo connectionInfo = new ConnectionInfo(
                        Integer.valueOf(elements[0]),
                        Long.valueOf(elements[1]),
                        elements[2]
                );
                result.add(connectionInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void connectionsClear(String fileName, long notOlderThat) {
        List<ConnectionInfo> allConnections = readConnectionInfoFromFile (fileName);
        boolean append = false;
        for (ConnectionInfo connection:allConnections) {
            if (connection.getTime() > (new Date().getTime() - notOlderThat)) {
                FileManager.writeConnectionInfoToFile(connection, fileName, append);
                append = true;
            }
        }

    }

    private static boolean isNotExist(String filePath) {
        File file = new File(filePath);
        return !file.exists();
    }

    public static void writeBytesToFile(byte[] bytes, String fileName){
        checkFileDir();
        String filePath = FILES_DIR + SEPARATOR + fileName;

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static byte[] readBytesFromFile(String fileName){
        String filePath = FILES_DIR + SEPARATOR + fileName;
        if(isNotExist(filePath)){
            throw new  MyOwnException("Sorry , can`t find file");
        }
        File file= new File(filePath);
        byte [] bytes = new  byte[0];
        try {
            bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static void writeSingleObjectToFile(Object obj, String fileName){
        checkFileDir();
        String filePath = FILES_DIR + SEPARATOR + fileName;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(obj);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object readSingleObjectFromFile(String fileName){
        checkFileDir();
        String filePath = FILES_DIR + SEPARATOR + fileName;
        Object obj = null;
        FileInputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileOutputStream);
            obj = in.readObject();
            in.close();
            return obj;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static void copyFile(String sourceFile, String targetFile){
        byte[] bytes =readBytesFromFile(sourceFile);
        writeBytesToFile(bytes, targetFile);
    }

    public static void moveFile(String sourceFile, String targetFile){
        String filePath = FILES_DIR + SEPARATOR + sourceFile;
        byte[] bytes =readBytesFromFile(sourceFile);
        writeBytesToFile(bytes, targetFile);
        File file = new File(filePath);
        file.delete();
    }

}
