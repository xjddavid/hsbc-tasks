package com.jiang.tasks.result;

public class CodeMsg {
    private int code;
    private String msg;

    public static CodeMsg SUCCESS=new CodeMsg(200,"success");
    public static CodeMsg SERVER_ERROR=new CodeMsg(500100,"服务端异常!");
    public static CodeMsg BIND_ERROR=new CodeMsg(500101,"参数校验异常:%s");
    public static CodeMsg REQUEST_ILLEAGAL=new CodeMsg(500102,"非法请求!");
    public static CodeMsg QUERY_ERROR=new CodeMsg(500103,"No search result");

    private CodeMsg(int code, String msg) {
        this.code=code;
        this.msg=msg;
    }
    int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CodeMsg fillArgs(Object...args) {
        int code=this.code;
        String message=String.format(this.msg, args);
        return new CodeMsg(code,message);
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }

}
