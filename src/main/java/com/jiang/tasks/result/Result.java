package com.jiang.tasks.result;

public class Result<T> {
    private int code;
    private String msg;
    private T data;
    //success
    private Result(T data) {
        this.code=200;
        this.msg="success";
        this.data=data;
    }
    //error
    private Result(CodeMsg cm) {
        if(cm==null) {
            return;
        }

        this.code=cm.getCode();
        this.msg=cm.getMsg();
    }

    public static <T> Result<T> success(T data){
        return new Result<>(data) ;
    }

    public static <T> Result<T> error(CodeMsg codeMsg) {
        return new Result<>(codeMsg);
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
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

}

