package cn.wsgwz.server.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class User {
    public @PrimaryKey int id;
    public String name;
    public @NonNull String password;
    public String token;
    public String tag;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
