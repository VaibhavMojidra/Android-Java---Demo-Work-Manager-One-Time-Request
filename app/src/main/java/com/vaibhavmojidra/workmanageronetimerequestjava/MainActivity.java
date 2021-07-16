package com.vaibhavmojidra.workmanageronetimerequestjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.widget.Toast;

import com.vaibhavmojidra.workmanageronetimerequestjava.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.startWork.setOnClickListener(v-> setOneTimeRequestWork());

    }

    private void setOneTimeRequestWork() {
        Constraints constraints=new Constraints.Builder()
                .setRequiresCharging(true)
                .build(); //Defining Constraints
        Data data=new Data.Builder()
                .putInt("SECONDS",30) //Declaring Input Data
                .build();
        OneTimeWorkRequest oneTimeWorkRequest= new OneTimeWorkRequest.Builder(MyWorker.class)
                .setConstraints(constraints) //Setting Constraints
                .setInputData(data) //Passing Input data
                .build();


        WorkManager workManager=WorkManager.getInstance(getApplicationContext());
        workManager.enqueue(oneTimeWorkRequest);
        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.getId()).observe(this,o->{
            binding.status.setText(o.getState().name()); //Getting Status
            if(o.getState().isFinished()){
                String dateTime=o.getOutputData().getString("DATE_TIME");
                Toast.makeText(this, dateTime, Toast.LENGTH_LONG).show();
            }
        });
    }
}