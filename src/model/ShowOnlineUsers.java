package model;

public class ShowOnlineUsers {
    String username;
    String status;
    String avilable;

    public ShowOnlineUsers() {
    }

    public ShowOnlineUsers(String username, String status, String avilable) {
        this.username = username;
        this.status = status;
        this.avilable = avilable;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvilable() {
        return avilable;
    }

    public void setAvilable(String avilable) {
        this.avilable = avilable;
    }
    
    
}
