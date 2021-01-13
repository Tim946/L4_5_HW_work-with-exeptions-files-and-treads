package ua.mainacademy.service;

import ua.mainacademy.model.ConnectionInfo;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ua.mainacademy.service.FileManager.*;

class FileManagerTest {

    @org.junit.jupiter.api.Test
    void writeAndReadConnectionInfoToFile() {
        ConnectionInfo connectionInfo1 = new ConnectionInfo(1, new Date().getTime(), "10.0.0.1");
        ConnectionInfo connectionInfo2 = new ConnectionInfo(1, new Date().getTime(), "10.0.0.1");
        FileManager.writeConnectionInfoToFile(connectionInfo1, "connectionsTest.txt", true);
        List<ConnectionInfo> connections = FileManager.readConnectionInfoFromFile("connectionsTest.txt");
        int result = connections.size();
        assertEquals(1, result);
        FileManager.writeConnectionInfoToFile(connectionInfo2, "connectionsTest.txt", true);
        connections = FileManager.readConnectionInfoFromFile("connectionsTest.txt");
        result = connections.size();
        assertEquals(2, result);
    }

    @org.junit.jupiter.api.Test
    void connectionsClear() {
        long notOlderThat = 100000;
        long lessNotOlderThat = 10000;
        ConnectionInfo connectionInfo1 = new ConnectionInfo(1, new Date().getTime() - notOlderThat, "10.0.0.1");
        ConnectionInfo connectionInfo2 = new ConnectionInfo(1, new Date().getTime() - lessNotOlderThat, "10.0.0.1");
        FileManager.writeConnectionInfoToFile(connectionInfo1, "connectionsClearTest.txt", true);
        FileManager.writeConnectionInfoToFile(connectionInfo2, "connectionsClearTest.txt", true);
        FileManager.connectionsClear("connectionsClearTest.txt", notOlderThat);
        List<ConnectionInfo> connections = FileManager.readConnectionInfoFromFile("connectionsClearTest.txt");
        for (ConnectionInfo connection : connections) {
            assertEquals(connectionInfo2, connection);
        }
    }

    @org.junit.jupiter.api.Test
    void writeReadSingleObjectToFile() {
        ConnectionInfo connectionInfo = new ConnectionInfo(1, new Date().getTime(), "10.0.0.1");
        writeSingleObjectToFile(connectionInfo, "ObjectTest.txt");
        ConnectionInfo connection = (ConnectionInfo) FileManager.readSingleObjectFromFile("ObjectTest.txt");
        assertEquals(connectionInfo, connection);
    }
}
