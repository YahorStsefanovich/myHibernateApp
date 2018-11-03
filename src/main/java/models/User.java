package models;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * User entity
 * @author StephanovichYegor
 * @version 1.1
 */
public class User {

    private String login;
    private String password;
    private boolean isAdmin;

    /**
     * Empty constructor
     */
    public User(){
    }

    /**
     * Overloaded constructor with 4 params
     * @param login user's Email
     * @param password user's password
     * @param isAdmin if user is admin true, else false
     */
    public User(String login, String password, boolean isAdmin){
        this.login = login;
        this.password = DigestUtils.md5Hex(password);
        this.isAdmin = isAdmin;
    }

    /**
     * Get user's email
     * @return user's email
     */
    public String getLogin() {
        return login;
    }

    /**
     * Get user's password hash
     * @return user's password hash
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get type of user
     * @return If admin true, else false
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Set type of user
     * @param admin If admin true, else false
     */
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
