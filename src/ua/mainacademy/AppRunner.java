package ua.mainacademy;

import ua.mainacademy.model.ConnectionInfo;
import ua.mainacademy.service.FileManager;
import ua.mainacademy.service.ThreadService;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static ua.mainacademy.service.FileManager.*;

public class AppRunner {

    private static final Logger LOGGER = Logger.getLogger(AppRunner.class.getName());

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            ConnectionInfo connectionInfo = new ConnectionInfo(i, new Date().getTime() - (Long.valueOf(i) * 5000000), "10.0.0." + i);
            ThreadService threadService = new ThreadService(connectionInfo);
            threadService.run();
        }

        List<ConnectionInfo> connections = FileManager.readConnectionInfoFromFile("connections.txt");
        LOGGER.info(String.format("The first element is %s , and count of connections in file is %s", connections.get(0).toString(), connections.size()));
//        Thread.sleep(4000);
        connectionsClear("connections.txt", 1000 * 60 * 60 * 24);
        connections = FileManager.readConnectionInfoFromFile("connections.txt");
        LOGGER.info(String.format("Count of connections in valid file  is %s", connections.size()));

        writeSingleObjectToFile(connections.get(0), "Object.txt");
        LOGGER.info("Object in file Object.txt");

        ConnectionInfo connection = (ConnectionInfo) readSingleObjectFromFile("Object.txt");
        if (connection.equals(connections.get(0))) {
            LOGGER.info(String.format("Read element is %s", connection.toString()));
            LOGGER.info("Object read from file equals the object that wrote to file");
        }
    }
}
