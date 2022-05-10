package com.mr.testproject;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mr.test.MyAdapter;

/**
 * @auther: pengwang
 * @date: 2022/4/20
 * @description:
 */
public class CustomAdapter extends MyAdapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


//    @Override
//    public int getDataCount() {
////        return super.getDataCount();
//        return 0;
//    }
}
