package cn.ecut.tomastu.bean;

import cn.ecut.tomastu.utils.PasswordUtils;

import java.util.Objects;

public class User {
    private String username;
    private String password;
    private short type;

    public User(String username) {
        this(username, "", (short) 0);
    }

    public User(String username, String password) {
        this(username, password, (short) 0);
    }

    public User(String username, String password, short type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public String getPassword() {
//        return password;
//    }

    public void setPassword(String password) {
//        this.password = password;
        this.password = PasswordUtils.getSaltMD5(password);
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return type == user.type && username.equals(user.username) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, type);
    }
}
