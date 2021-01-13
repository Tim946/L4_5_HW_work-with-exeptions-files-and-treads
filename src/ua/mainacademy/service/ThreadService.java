package ua.mainacademy.service;

import ua.mainacademy.AppRunner;
import ua.mainacademy.model.ConnectionInfo;

import java.util.logging.Logger;

public class ThreadService extends Thread{
    private  static final Logger LOGGER=Logger.getLogger(AppRunner.class.getName());
    private ConnectionInfo connectionInfo;

    public ThreadService(ConnectionInfo connectionInfo){
        this.connectionInfo = connectionInfo;
    }

    @Override
    public void run() {
        FileManager.writeConnectionInfoToFile(connectionInfo, "connections.txt", true);
    }


}
