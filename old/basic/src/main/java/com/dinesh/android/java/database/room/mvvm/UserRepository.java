package com.dinesh.android.java.database.room.mvvm;


import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;
    private ExecutorService executorService;

    public UserRepository(Application application) {
        UserDatabase database = UserDatabase.getInstance(application);
        userDao = database.userDao();
        allUsers = userDao.getAllUsers();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(User user) {
        executorService.execute(() -> userDao.insert(user));
    }

    public void update(User user) {
        executorService.execute(() -> userDao.update(user));
    }

    public void delete(User user) {
        executorService.execute(() -> userDao.delete(user));
    }

    public void deleteAllUsers() {
        executorService.execute(() -> userDao.deleteAllUsers());
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void shutdownExecutor() {
        executorService.shutdown();
    }
}