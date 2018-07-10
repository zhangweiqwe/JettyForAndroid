package cn.wsgwz.server.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Setting {

    public @PrimaryKey int id;
    public @NonNull String tokenKey;
}
