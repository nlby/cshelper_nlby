package com.hhu.cshelper.model.bean.remote;

/** 用户类
 * @name  User
 * @description  用户类
 * @author  nlby
 * @date  2020/4/29
 */
public class User extends BaseBean{
    private String id;  //用户id 在前端生成，会导致请求参数复杂 因此还是在服务器端生成吧 但该字段可以保留

    private String username;  //用户名
    private String password;  //密码
    private String phone;     //手机号
    private String mail;     //邮箱
    private String createAt;  //创建时间
    private String updateAt;  //更新时间

    public User() {

    }

    public User(String username, String password, String phone, String mail) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.mail = mail;
   }

    public User(String id, String username, String password, String phone, String mail, String createAt, String updateAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.mail = mail;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }
}
