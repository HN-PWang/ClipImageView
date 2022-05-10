package com.mr.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @auther: pengwang
 * @date: 2022/4/20
 * @description:
 */
public abstract class MrRecyclerView extends RecyclerView {

    public MrRecyclerView(@NonNull Context context) {
        super(context);
    }

    public MrRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MrRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public abstract class MrAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

        private int headViewCount = 0;

        private int footViewCount = 0;

        public final void addHeadView(View view, int index, int tag) {

        }

        public final void removeHeadView() {

        }

        public final void clearHeadView() {

        }

        public final void addFootView() {

        }

        public final void removeFootView() {

        }

        public final void clearFootView() {

        }

        public int getDataCount() {
            return 0;
        }

        public int getHeadCount() {
            return headViewCount;
        }

        public int getFootCount() {
            return footViewCount;
        }

        public int getDataViewType(int position) {
            return getItemViewType(position);
        }

        @Override
        public final int getItemCount() {
            return getHeadCount() + getDataCount() + getDataCount();
        }

        @Override
        public final long getItemId(int position) {
            int realPosition = position + getHeadCount();
            return super.getItemId(realPosition);
        }

        @Override
        public final int getItemViewType(int position) {
            int realPosition = position + getHeadCount();
            return super.getItemViewType(realPosition);
        }
    }

}
