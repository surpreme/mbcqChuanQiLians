package com.mbcq.baselibrary.dialog.system;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

/**
 * * 你可能会觉得泛型会浪费性能 但这里不是object 已经给它指定了类型 所以就没了所谓的强转类型
 * @param <T> 数据的种类
 * @param <RA> recyclerview的适配器名字
 * @param <SX> 抛出来的接口参数种类
 * TODO
 */
public abstract class CommonListSystemDialog<T, RA extends RecyclerView.Adapter<?>, SX> extends BaseSystemDialog {

    protected abstract int setRecyclerViewId();
    protected abstract RA setAdapter();
    protected abstract RecyclerView.LayoutManager setLayoutManager();
    protected ArrayList<T> mData = new ArrayList<>();
    protected RA adapter;

    /**
     *   super.initView(view);
     *   如果不写就不会执行了 这个类则变得无用
     * @param view
     */
    @Override
    protected void initView(View view) {
        RecyclerView recyclerView = view.findViewById(setRecyclerViewId());
        recyclerView.setLayoutManager(setLayoutManager());
        recyclerView.setAdapter(setAdapter());
    }

    public void setOnClickInterface(OnClickInterface<SX> onClickInterface) {
        this.onClickInterface = onClickInterface;
    }

    protected OnClickInterface<SX> onClickInterface;

    public CommonListSystemDialog(@NonNull Context context) {
        super(context);
    }
    public void setmData(ArrayList<T> mData) {
        this.mData = mData;
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }
    public interface OnClickInterface<S> {
        void getData(View v, S s);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        System.gc();
    }
}
