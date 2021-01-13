package ua.mainacademy.model;

import java.io.Serializable;
import java.util.Objects;

public class ConnectionInfo implements Serializable {
    private Integer id;
    private  Long time;
    private String connectionIP;

    public Integer getId() {
        return id;
    }

    public Long getTime() {
        return time;
    }

    public String getConnectionIP() {
        return connectionIP;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void setConnectionIP(String connectionIP) {
        this.connectionIP = connectionIP;
    }

    public ConnectionInfo(Integer id, Long time, String connectionIP) {
        this.id = id;
        this.time = time;
        this.connectionIP = connectionIP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectionInfo that = (ConnectionInfo) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(time, that.time) &&
                Objects.equals(connectionIP, that.connectionIP);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, time, connectionIP);
    }

    @Override
    public String toString() {
        return id + " " + time + " " + connectionIP; //
    }
}
