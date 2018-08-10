package ru.tebloev.workmanager;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        PeriodicWorkRequest periodicWorkRequest =
                new PeriodicWorkRequest.Builder(MyWorker.class, 3, TimeUnit.DAYS)
                        .build();
        OneTimeWorkRequest someWork =
                new OneTimeWorkRequest.Builder(MyWorker.class)
                        .build();
        WorkManager.getInstance().enqueue(someWork);
        WorkManager.getInstance().enqueue(someWork, periodicWorkRequest);

        WorkManager mManagerInstance = WorkManager.getInstance();

        mManagerInstance.beginUniqueWork("nameOfWork", ExistingWorkPolicy.APPEND)

    }

    public class MyWorker extends Worker {
        @NonNull
        @Override
        public Result doWork() {
            String result = someWork();
            if (result == null) {
                return Result.FAILURE;
            } else if (result.isEmpty()) {
                return Result.RETRY;
            } else {
                return Result.SUCCESS;
            }
        }
    }

    String someWork() {
        return null;
    }
}
