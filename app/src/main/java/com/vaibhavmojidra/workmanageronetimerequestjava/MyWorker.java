package com.vaibhavmojidra.workmanageronetimerequestjava;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyWorker extends Worker {

    public MyWorker(@NonNull @NotNull Context context, @NonNull @NotNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public Result doWork() {
        try {
            int seconds= getInputData().getInt("SECONDS",0);
            for (int i = 1; i <=seconds; i++) {
                Thread.sleep(1000);
                Log.i("MyTag","Second: "+i);
            }
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/mm/yyyy hh:mm:ss");
            String dateTime=simpleDateFormat.format(new Date());
            Data outPutData=new Data.Builder()
                    .putString("DATE_TIME",dateTime) //Defining Output Data
                    .build();
           return Result.success(outPutData); //Passing Output Data
        } catch (Exception e) {
            return Result.failure();
        }
    }
}
