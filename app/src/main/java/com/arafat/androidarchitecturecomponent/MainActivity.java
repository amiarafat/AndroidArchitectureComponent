package com.arafat.androidarchitecturecomponent;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arafat.androidarchitecturecomponent.adapter.RecyclerViewAdapter;
import com.arafat.androidarchitecturecomponent.room.BorrowModel;
import com.arafat.androidarchitecturecomponent.view_model.BorrowListViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener {

    BorrowListViewModel viewModel;
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(new ArrayList<BorrowModel>(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(recyclerViewAdapter);


        viewModel = ViewModelProviders.of(this).get(BorrowListViewModel.class);;

        viewModel.getItemandPersonList().observe(MainActivity.this, new Observer<List<BorrowModel>>() {
            @Override
            public void onChanged(@Nullable List<BorrowModel> itemAndPeople) {
                recyclerViewAdapter.addItems(itemAndPeople);
            }
        });
    }


    @Override
    public boolean onLongClick(View view) {

        BorrowModel borrowModel = (BorrowModel) view.getTag();
        viewModel.deleteItem(borrowModel);
        return true;

    }
}
