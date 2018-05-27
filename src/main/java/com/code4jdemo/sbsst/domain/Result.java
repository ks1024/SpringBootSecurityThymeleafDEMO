package com.code4jdemo.sbsst.domain;

public class Result<T> {

    public enum ExceptionMsg {
        Success("000000", "操作成功"),
        LoginNameOrCodeError("000100", "用户名或验证码"),
        PhoneEmptyError("000101", "手机号为空");

        private String code;
        private String msg;

        private ExceptionMsg(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

    }

    /** 默认返回信息码 */
    private String code = "000000";
    /** 默认返回消息 */
    private String msg = "操作成功";
    private T data;

    public Result(){}

    public Result(T data) {
        this.data = data;
    }

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(ExceptionMsg e) {
        this.code = e.getCode();
        this.msg = e.getMsg();
    }

    public Result(ExceptionMsg e, T data) {
        this.code = e.getCode();
        this.msg = e.getMsg();
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
