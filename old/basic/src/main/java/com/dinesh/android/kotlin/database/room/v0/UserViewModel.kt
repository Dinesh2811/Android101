package com.dinesh.android.kotlin.database.room.v0

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao: UserDao?
    val allUsers: LiveData<List<User?>?>?

    init {
        database = UserDatabase.getInstance(application)
        userDao = database!!.userDao()
        allUsers = userDao!!.allUsers
        executor = Executors.newSingleThreadExecutor()
    }

    fun insert(user: User) {
        executor.execute { userDao!!.insert(user) }
    }

    fun update(user: User) {
        executor.execute { userDao!!.update(user) }
    }

    fun delete(user: User) {
        executor.execute { userDao!!.delete(user) }
    }

    fun deleteAllUsers() {
        executor.execute { userDao!!.deleteAllUsers() }
    }

    fun shutdownExecutor() {
        executor.shutdown()
    }

    companion object {
        private var database: UserDatabase? = null
        private lateinit var executor: ExecutorService
    }
}


//class UserViewModel(application: Application) : AndroidViewModel(application) {
//    private val repository: UserRepository
//    val allUsers: LiveData<List<User?>?>?
//
//    init {
//        repository = UserRepository(application)
//        allUsers = repository.allUsers
//    }
//
//    fun insert(user: User?) {
//        repository.insert(user)
//    }
//
//    fun update(user: User?) {
//        repository.update(user)
//    }
//
//    fun delete(user: User?) {
//        repository.delete(user)
//    }
//
//    fun deleteAllUsers() {
//        repository.deleteAllUsers()
//    }
//
//    fun shutdownExecutor() {
//        repository.shutdownExecutor()
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        repository.shutdownExecutor()
//    }
//}