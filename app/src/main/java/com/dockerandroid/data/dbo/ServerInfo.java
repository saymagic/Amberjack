package com.dockerandroid.data.dbo;

/**
 * Created by saymagic on 16/1/27.
 */
public class ServerInfo {

    private String mServerAddress;

    private int mServerPort;

    public ServerInfo(String mServerAddress, int mServerPort) {
        this.mServerAddress = mServerAddress;
        this.mServerPort = mServerPort;
    }

    public String getServerAddress() {
        return mServerAddress;
    }

    public int getServerPort() {
        return mServerPort;
    }

    public String toEndPoint() {
        String host = getServerAddress();
        int port = getServerPort();
        host = host.startsWith("http://") || host.startsWith("https://") ? host : "http://"+host;
        host = host.endsWith("/") ? host.substring(host.length() - 1, host.length()) : host;
        return host + ":" + port;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof ServerInfo)) {
            return false;
        }
        if (mServerAddress.equals(((ServerInfo) o).getServerAddress()) && mServerPort == ((ServerInfo) o).getServerPort()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "ServerInfo{" +
                "mServerAddress='" + mServerAddress + '\'' +
                ", mServerPort=" + mServerPort +
                '}';
    }
}
