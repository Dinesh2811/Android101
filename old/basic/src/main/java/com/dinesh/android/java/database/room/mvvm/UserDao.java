package com.dinesh.android.java.database.room.mvvm;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM user_table WHERE username = :username AND password = :password")
    User getUserByUsernameAndPassword(String username, String password);

    @Query("SELECT * FROM user_table ORDER BY username ASC, password ASC")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM user_table WHERE username IN (:names) ORDER BY username ASC")
    LiveData<List<User>> getUsersByNameList(List<String> names);

    @Query("SELECT * FROM user_table WHERE username LIKE :namePrefix || '%' ORDER BY username ASC")
    LiveData<List<User>> getUsersByNamePrefix(String namePrefix);

    @Query("SELECT * FROM user_table WHERE LENGTH(password) < 8")
    List<User> getUsersByPasswordLengthLessThan8();

    @Query("SELECT * FROM user_table WHERE password BETWEEN :minPassword AND :maxPassword ORDER BY password ASC")
    LiveData<List<User>> getUsersByPasswordRange(String minPassword, String maxPassword);

    @Query("SELECT * FROM user_table WHERE username LIKE '%john%' OR password LIKE '%john%'")
    List<User> getUsersByUsernameOrPasswordContainsJohn();

//    @Query("SELECT * FROM user_table WHERE password GLOB '[1-5]*'")
//    @Query("SELECT * FROM user_table WHERE password LIKE '%[1-5]%'")
//    @Query("SELECT * FROM user_table WHERE password REGEXP '[^A-Za-z0-9]'")
//    @Query("SELECT * FROM user_table WHERE password REGEXP '%[A-Z]%'")
//    List<User> getUsersByPasswordContainsSpecialChar();

//    @Query("SELECT * FROM user_table WHERE password LIKE '%B%'")
//    @Query("SELECT * FROM user_table WHERE password LIKE '%[_|-]%'")
//    LiveData<List<User>> getUsersByPasswordContainsUnderscoreOrDash();

    @Query("SELECT * FROM user_table WHERE username <> 'john'")
    List<User> getUsersByUsernameIsNotJohn();

    @Query("SELECT * FROM user_table WHERE password = 'john123'")
    List<User> getUsersByPasswordIsJohn123();

    @Query("SELECT * FROM user_table WHERE id BETWEEN 5 AND 10")
    List<User> getUsersByIdBetween5And10();

    @Query("SELECT * FROM user_table WHERE username IS NOT NULL")
    List<User> getUsersByUsernameIsNotNull();

    @Query("SELECT * FROM user_table WHERE username IN ('john', 'peter', 'jane')")
    List<User> getUsersByUsernameInList();

    @Query("SELECT * FROM user_table WHERE password NOT IN ('john123', 'peter123', 'jane123')")
    List<User> getUsersByPasswordNotInList();

    @Query("SELECT * FROM user_table WHERE username <> 'john' AND (password IS NOT NULL AND LENGTH(password) > 8) OR (id BETWEEN 5 AND 10)")
    List<User> getUsersByUsernameIsNotJohnAndPasswordIsNotNullAndLengthGreaterThan8OrIdBetween5And10();

    @Update
    void update(User user);

    @Query("UPDATE user_table SET password = :password WHERE username LIKE 'A%'")
    void updateUserPasswordByUsernameStartsWithA(String password);

    @Query("DELETE FROM user_table")
    void deleteAllUsers();

    @Query("DELETE FROM user_table WHERE id = :userId")
    void delete(int userId);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user_table WHERE password IS NOT NULL AND username LIKE 'A%'")
    void deleteUserPasswordByUsernameStartsWithA();

    @Query("SELECT COUNT(*) FROM user_table")
    int getUserCount();
}