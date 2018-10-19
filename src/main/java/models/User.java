package models;

import org.apache.commons.codec.digest.DigestUtils;

public class User {

    private String login;
    private String password;
    private boolean isAdmin;

    public User(){
    }

    public User(String login, String password, boolean isAdmin){
        this.login = login;
        this.password = DigestUtils.md5Hex(password);
        this.isAdmin = isAdmin;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
