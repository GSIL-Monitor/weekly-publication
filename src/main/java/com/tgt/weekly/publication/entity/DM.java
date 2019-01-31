package com.tgt.weekly.publication.entity;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/18
 * @Time: 15:35
 * @Description: To change this template use File | Settings | File Templates.
 **/
public class DM {

    private String host;
    private String user;
    private String password;
    private String command;
    private String fileName;
    private String path;

    public String getHost() {
        return host;
    }

    public DM setHost(String host) {
        this.host = host;
        return this;
    }

    public String getUser() {
        return user;
    }

    public DM setUser(String user) {
        this.user = user;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public DM setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getCommand() {
        return command;
    }

    public DM setCommand(String command) {
        this.command = command;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public DM setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getPath() {
        return path;
    }

    public DM setPath(String path) {
        this.path = path;
        return this;
    }

    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this)
                .append("host", host)
                .append("user", user)
                .append("password", password)
                .append("command", command)
                .append("fileName", fileName)
                .append("path", path)
                .toString();
    }
}
