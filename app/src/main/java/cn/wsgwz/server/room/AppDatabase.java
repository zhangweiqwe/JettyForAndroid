package cn.wsgwz.server.room;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {User.class,Setting.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static final String NAME = "main.db";


    private static AppDatabase IN_MEMORY_INSTANCE;
    private static AppDatabase INSTANCE;

    public abstract UserDao userModel();
    public abstract SettingDao settingModel();

    public static AppDatabase getInMemoryDatabase(Context context) {
        if (IN_MEMORY_INSTANCE == null) {
            IN_MEMORY_INSTANCE =
                    Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                            // To simplify the codelab, allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return IN_MEMORY_INSTANCE;
    }

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, NAME).build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        IN_MEMORY_INSTANCE = null;
    }
    

}