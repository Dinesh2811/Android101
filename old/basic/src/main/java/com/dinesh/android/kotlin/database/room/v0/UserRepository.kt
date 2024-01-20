package com.dinesh.android.kotlin.database.room.v0

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class UserRepository(application: Application?) {
    private val userDao: UserDao?
    val allUsers: LiveData<List<User?>?>?
    private val executorService: ExecutorService

    init {
        val database = application?.let { UserDatabase.getInstance(it) }
//        val database = UserDatabase.getInstance(application!!)
        userDao = database!!.userDao()
        allUsers = userDao!!.allUsers
        executorService = Executors.newSingleThreadExecutor()
    }

    fun insert(user: User) {
        executorService.execute { userDao!!.insert(user) }
    }

    fun update(user: User) {
        executorService.execute { userDao!!.update(user) }
    }

    fun delete(user: User) {
        executorService.execute { userDao!!.delete(user) }
    }

    fun deleteAllUsers() {
        executorService.execute { userDao!!.deleteAllUsers() }
    }

    fun shutdownExecutor() {
        executorService.shutdown()
    }
}