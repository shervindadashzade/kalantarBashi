package com.example.kalantarbashi.Core;

import java.net.Socket;

public class Account {
    String name;
    String userPic;
    Socket socket;
    ShootInfo shootInfo;
    public GameResult result;
    public Account(String name, String userPic, Socket socket) {
        this.name = name;
        this.userPic = userPic;
        this.socket = socket;
    }

    public Account(String data){
        String infoes[] = data.split("@");
        name = infoes[0];
        userPic = infoes[1];
        shootInfo = new ShootInfo();
        shootInfo.setPitch(Float.parseFloat(infoes[2]));
        shootInfo.time = Long.parseLong(infoes[3]);
    }
    public ShootInfo getShootInfo() {
        return shootInfo;
    }

    @Override
    public String toString() {
        String str = String.format("%s@%s@%f@%d",name,userPic,shootInfo.getPitch(),shootInfo.getTime());
        return str;
    }

    public void setShootInfo(ShootInfo shootInfo) {
        this.shootInfo = shootInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
