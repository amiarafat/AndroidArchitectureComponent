package com.arafat.androidarchitecturecomponent.view_model;

import android.app.Application;
import android.app.ListActivity;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.arafat.androidarchitecturecomponent.room.AppDatabase;
import com.arafat.androidarchitecturecomponent.room.BorrowModel;

import java.util.List;

public class BorrowListViewModel extends AndroidViewModel {

    private  LiveData<List<BorrowModel>>  itemandPersonList;
    private AppDatabase appDatabase;


    public BorrowListViewModel(@NonNull Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());
        itemandPersonList =appDatabase.itemAndPersonModel().getAllBorrowedItems();
    }

    public LiveData<List<BorrowModel>> getItemandPersonList(){
        return itemandPersonList;
    }


    public void deleteItem(BorrowModel borrowModel){
        new DeleteAsyncTask(appDatabase).execute(borrowModel);
    }


    private static class DeleteAsyncTask extends AsyncTask<BorrowModel,Void,Void>{

        private AppDatabase db;

        DeleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(BorrowModel... borrowModels) {
            db.itemAndPersonModel().deleteBorrow(borrowModels[0]);
            return null;
        }
    }
}

