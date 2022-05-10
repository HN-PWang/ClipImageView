package com.mr.test;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther: pengwang
 * @date: 2022/4/20
 * @description:
 */
public abstract class MyAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    public List<Object> dataList = new ArrayList<>();


}
