package cn.wsgwz.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Const {
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();
}
