package com.hhu.cshelper.model.bean.remote;

/** 处理服务器响应的信息
 * @name  BaseBean
 * @description  处理服务器响应的信息
 * @author  nlby
 * @date  2020/4/29
 */
public class BaseBean {
    /**
     * status : 100
     * message : 处理成功！
     */

    //状态码   100-成功    200-失败
    private int status;
    //提示信息
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(){
        status=100;
        message="处理成功！";
    }
    public void setFail(){
        status=200;
        message="处理失败！";
    }

    public void setFail(String msg){
        status=200;
        message=msg;
    }

    public BaseBean success(){
        setSuccess();
        return this;
    }

    public BaseBean fail(){
        setFail();
        return this;
    }

    public BaseBean fail(String msg){
        setFail(msg);
        return this;
    }

}