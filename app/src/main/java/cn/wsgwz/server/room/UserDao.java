package cn.wsgwz.server.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface UserDao {
    @Query("select * from user")
    List<User> loadAllUsers();

    @Query("select * from user where id = :id")
    User loadUserById(int id);
    @Query("select * from user where id = :id")
    User loadUserById(String id);

    @Query("select * from user where name = :firstName")
    List<User> findByName(String firstName);

    @Insert(onConflict = IGNORE)
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("delete from user where id = :id")
    int deleteUsersById(String id);

    @Insert(onConflict = IGNORE)
    void insertOrReplaceUsers(User... users);

    @Delete
    void deleteUsers(User user1, User user2);

    @Update
    void updateUser(User user);

//    @Query("SELECT * FROM User WHERE age == :age")
//    List<User> findYoungerThan(int age);

//    @Query("SELECT * FROM User WHERE age < :age")
//    List<User> findYoungerThanSolution(int age);

    @Query("DELETE FROM User")
    void deleteAll();
}