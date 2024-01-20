package com.dinesh.android.kotlin.database.room.v0

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM user_table WHERE username = :username AND password = :password")
    fun getUserByUsernameAndPassword(username: String?, password: String?): User?

    @get:Query("SELECT * FROM user_table ORDER BY username ASC, password ASC")
    val allUsers: LiveData<List<User?>?>?

    @Query("SELECT * FROM user_table WHERE username IN (:names) ORDER BY username ASC")
    fun getUsersByNameList(names: List<String?>?): LiveData<List<User?>?>?

    @Query("SELECT * FROM user_table WHERE username LIKE :namePrefix || '%' ORDER BY username ASC")
    fun getUsersByNamePrefix(namePrefix: String?): LiveData<List<User?>?>?

    @get:Query("SELECT * FROM user_table WHERE LENGTH(password) < 8")
    val usersByPasswordLengthLessThan8: List<User?>?

    @Query("SELECT * FROM user_table WHERE password BETWEEN :minPassword AND :maxPassword ORDER BY password ASC")
    fun getUsersByPasswordRange(minPassword: String?, maxPassword: String?): LiveData<List<User?>?>?

    @get:Query("SELECT * FROM user_table WHERE username LIKE '%john%' OR password LIKE '%john%'")
    val usersByUsernameOrPasswordContainsJohn: List<User?>?


//    @Query("SELECT * FROM user_table WHERE password GLOB '[1-5]*'")
//    @Query("SELECT * FROM user_table WHERE password LIKE '%[1-5]%'")
//    @Query("SELECT * FROM user_table WHERE password REGEXP '[^A-Za-z0-9]'")
//    @Query("SELECT * FROM user_table WHERE password REGEXP '%[A-Z]%'")
//    fun getUsersByPasswordContainsSpecialChar(): List<User?>?


//    @Query("SELECT * FROM user_table WHERE password LIKE '%B%'")
//    @Query("SELECT * FROM user_table WHERE password LIKE '%[_|-]%'")
//    fun getUsersByPasswordContainsUnderscoreOrDash(): LiveData<List<User?>?>?


    @get:Query("SELECT * FROM user_table WHERE username <> 'john'")
    val usersByUsernameIsNotJohn: List<User?>?

    @get:Query("SELECT * FROM user_table WHERE password = 'john123'")
    val usersByPasswordIsJohn123: List<User?>?

    @get:Query("SELECT * FROM user_table WHERE id BETWEEN 5 AND 10")
    val usersByIdBetween5And10: List<User?>?

    @get:Query("SELECT * FROM user_table WHERE username IS NOT NULL")
    val usersByUsernameIsNotNull: List<User?>?

    @get:Query("SELECT * FROM user_table WHERE username IN ('john', 'peter', 'jane')")
    val usersByUsernameInList: List<User?>?

    @get:Query("SELECT * FROM user_table WHERE password NOT IN ('john123', 'peter123', 'jane123')")
    val usersByPasswordNotInList: List<User?>?

    @get:Query("SELECT * FROM user_table WHERE username <> 'john' AND (password IS NOT NULL AND LENGTH(password) > 8) OR (id BETWEEN 5 AND 10)")
    val usersByUsernameIsNotJohnAndPasswordIsNotNullAndLengthGreaterThan8OrIdBetween5And10: List<User?>?

    @Update
    fun update(user: User)

    @Query("UPDATE user_table SET password = :password WHERE username LIKE 'A%'")
    fun updateUserPasswordByUsernameStartsWithA(password: String?)

    @Query("DELETE FROM user_table")
    fun deleteAllUsers()

    @Query("DELETE FROM user_table WHERE id = :userId")
    fun delete(userId: Int)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM user_table WHERE password IS NOT NULL AND username LIKE 'A%'")
    fun deleteUserPasswordByUsernameStartsWithA()

    @get:Query("SELECT COUNT(*) FROM user_table")
    val userCount: Int
}