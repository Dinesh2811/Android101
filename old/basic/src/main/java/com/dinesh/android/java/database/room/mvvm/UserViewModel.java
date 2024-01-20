package com.dinesh.android.java.database.room.mvvm;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class UserViewModel extends AndroidViewModel {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    private static UserDatabase database;
    private static ExecutorService executor;

    public UserViewModel(@NonNull Application application) {
        super(application);
        database = UserDatabase.getInstance(application);
        userDao = database.userDao();
        allUsers = userDao.getAllUsers();
        executor = Executors.newSingleThreadExecutor();
    }

    public void insert(User user) {
        executor.execute(() -> userDao.insert(user));
    }

    public void update(User user) {
        executor.execute(() -> userDao.update(user));
    }

    public void delete(User user) {
        executor.execute(() -> userDao.delete(user));
    }

    public void deleteAllUsers() {
        executor.execute(() -> userDao.deleteAllUsers());
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }


    public void shutdownExecutor() {
        executor.shutdown();
    }
}
//
//public class UserViewModel extends AndroidViewModel {
//    private UserRepository repository;
//    private LiveData<List<User>> allUsers;
//
//    public UserViewModel(@NonNull Application application) {
//        super(application);
//        repository = new UserRepository(application);
//        allUsers = repository.getAllUsers();
//    }
//
//    public void insert(User user) {
//        repository.insert(user);
//    }
//
//    public void update(User user) {
//        repository.update(user);
//    }
//
//    public void delete(User user) {
//        repository.delete(user);
//    }
//
//    public void deleteAllUsers() {
//        repository.deleteAllUsers();
//    }
//
//    public LiveData<List<User>> getAllUsers() {
//        return allUsers;
//    }
//
//    public void shutdownExecutor(){
//        repository.shutdownExecutor();
//    }
//
//    @Override
//    protected void onCleared() {
//        super.onCleared();
//        repository.shutdownExecutor();
//    }
//}