package cn.wsgwz.server.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface SettingDao {
    @Query("select * from setting where id=:id")
    Setting loadSettingById(int id);

    @Insert(onConflict = IGNORE)
    void insertSetting(Setting setting);

    @Update
    void updateSetting(Setting setting);
}
