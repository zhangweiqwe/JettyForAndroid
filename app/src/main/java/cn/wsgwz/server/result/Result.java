package cn.wsgwz.server.result;

public enum Result {
    SUCCESS(1000,new String[]{"操作成功","登录成功"}),ERROR(1004,new String[]{"操作失败","数据异常","登录过期","用户id或密码错误","请在RequestHeader中携带Token"});

    private int code;
    private String[] msgs;

    public int getCode() {
        return code;
    }

    public String[] getMsgs() {
        return msgs;
    }

    Result(int code, String[] msgs) {
        this.code = code;
        this.msgs = msgs;
    }
}
