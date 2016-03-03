package com.dockerandroid.data.database.dpo;

import com.dockerandroid.data.dbo.DboTransform;
import com.dockerandroid.data.dbo.ServerInfo;
import com.dockerandroid.misc.Constant;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by saymagic on 16/2/4.
 */
@DatabaseTable(tableName = Constant.SERVER_INFO_NAME)
public class ServerInfoDpo implements DboTransform<ServerInfo>{

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String serverId;

    @DatabaseField
    private String serverHost;

    @DatabaseField
    private int serverPort;

    @DatabaseField
    private String name;

    @DatabaseField
    private String driver;

    @DatabaseField
    private int containers;

    @DatabaseField
    private int images;

    @DatabaseField
    private int memTotal;

    @DatabaseField
    private String operatingSystem;

    @DatabaseField
    private String indexServerAddress;

    @DatabaseField
    private String systemTime;

    @DatabaseField
    private String initSha1;

    @DatabaseField
    private String initPath;

    @DatabaseField
    private int NCPU;

    @DatabaseField
    private String kernelVersion;

    @DatabaseField
    private String dockerRootDir;

    @DatabaseField
    private String version;

    @DatabaseField
    private String apiVersion;

    @DatabaseField
    private String gitCommit;

    @DatabaseField
    private String goVersion;

    @DatabaseField
    private String os;

    @DatabaseField
    private String arch;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public int getContainers() {
        return containers;
    }

    public void setContainers(int containers) {
        this.containers = containers;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }

    public int getMemTotal() {
        return memTotal;
    }

    public void setMemTotal(int memTotal) {
        this.memTotal = memTotal;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getIndexServerAddress() {
        return indexServerAddress;
    }

    public void setIndexServerAddress(String indexServerAddress) {
        this.indexServerAddress = indexServerAddress;
    }

    public String getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

    public String getInitSha1() {
        return initSha1;
    }

    public void setInitSha1(String initSha1) {
        this.initSha1 = initSha1;
    }

    public String getInitPath() {
        return initPath;
    }

    public void setInitPath(String initPath) {
        this.initPath = initPath;
    }

    public int getNCPU() {
        return NCPU;
    }

    public void setNCPU(int NCPU) {
        this.NCPU = NCPU;
    }

    public String getKernelVersion() {
        return kernelVersion;
    }

    public void setKernelVersion(String kernelVersion) {
        this.kernelVersion = kernelVersion;
    }

    public String getDockerRootDir() {
        return dockerRootDir;
    }

    public void setDockerRootDir(String dockerRootDir) {
        this.dockerRootDir = dockerRootDir;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getGitCommit() {
        return gitCommit;
    }

    public void setGitCommit(String gitCommit) {
        this.gitCommit = gitCommit;
    }

    public String getGoVersion() {
        return goVersion;
    }

    public void setGoVersion(String goVersion) {
        this.goVersion = goVersion;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getArch() {
        return arch;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    public ServerInfoDpo setServerInfo(ServerInfo info) {
        setServerHost(info.getServerAddress());
        setServerPort(info.getServerPort());
        return this;
    }

    @Override
    public String toString() {
        return "ServerInfoDpo{" +
                "id=" + id +
                ", serverId='" + serverId + '\'' +
                ", serverHost='" + serverHost + '\'' +
                ", serverPort=" + serverPort +
                ", name='" + name + '\'' +
                ", driver='" + driver + '\'' +
                ", containers=" + containers +
                ", images=" + images +
                ", memTotal=" + memTotal +
                ", operatingSystem='" + operatingSystem + '\'' +
                ", indexServerAddress='" + indexServerAddress + '\'' +
                ", systemTime='" + systemTime + '\'' +
                ", initSha1='" + initSha1 + '\'' +
                ", initPath='" + initPath + '\'' +
                ", NCPU=" + NCPU +
                ", kernelVersion='" + kernelVersion + '\'' +
                ", dockerRootDir='" + dockerRootDir + '\'' +
                ", version='" + version + '\'' +
                ", apiVersion='" + apiVersion + '\'' +
                ", gitCommit='" + gitCommit + '\'' +
                ", goVersion='" + goVersion + '\'' +
                ", os='" + os + '\'' +
                ", arch='" + arch + '\'' +
                '}';
    }

    @Override
    public ServerInfo transformToDbo() {
        ServerInfo info = new ServerInfo(getServerHost(), getServerPort());
        return info;
    }
}
