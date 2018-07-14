package com.reweyou.master.kavyanjali;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.reweyou.master.kavyanjali.adapter.PoemAdapter;
import com.reweyou.master.kavyanjali.data.model.PoemModel;
import com.reweyou.master.kavyanjali.databinding.ContentMainBinding;
import com.reweyou.master.kavyanjali.ui.base.MvpView;
import com.reweyou.master.kavyanjali.ui.home.HomeViewModel;
import com.reweyou.master.kavyanjali.ui.image.WriteTextActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MvpView {


    private HomeViewModel viewModel;
    private PoemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ContentMainBinding binding = DataBindingUtil.setContentView(this, R.layout.content_main);
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        binding.setModel(viewModel);


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PoemAdapter(this);
        binding.recyclerView.setAdapter(mAdapter);


        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                binding.swiperefresh.setRefreshing(aBoolean.booleanValue());
            }
        });

        viewModel.getPoemList().observe(this, new Observer<List<PoemModel>>() {
            @Override
            public void onChanged(@Nullable List<PoemModel> poemModels) {
                mAdapter.add(poemModels);
            }
        });

        binding.getRoot().findViewById(R.id.create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WriteTextActivity.class));
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
