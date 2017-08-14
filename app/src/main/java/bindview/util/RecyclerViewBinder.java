package bindview.util;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myapplication.BindViewManager;

import java.util.ArrayList;

import bindview.data.BindMode;

/**
 * Created by LJM on 2017/8/8 9:57.
 * Description:
 */

public class RecyclerViewBinder {
    private BindMode bindMode;
    private String[] bindViewIds;

    Activity activity;
    private RecyclerView recyclerView;
    private int layoutId;
    private ArrayList<Object> datas;

    public RecyclerViewBinder(Activity activity, RecyclerView recyclerView, ArrayList<Object> datas, int layoutId, BindMode bindMode) {
        this.bindMode = bindMode;
        this.activity = activity;
        this.recyclerView = recyclerView;
        this.layoutId = layoutId;
        this.datas = datas;
    }

    public void bindData() {
        initAdapter(activity, datas, layoutId);
    }

    private void initAdapter(Activity activity, ArrayList<Object> datas, int layoutId) {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(new CustomAdapter(activity, datas, layoutId));
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

        private Activity activity;
        private ArrayList<Object> datas;
        private int layoutId;

        public CustomAdapter(Activity activity, ArrayList<Object> datas, int layoutId) {
            this.datas = datas;
            this.layoutId = layoutId;
            this.activity = activity;
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            CustomViewHolder viewHolder = new CustomViewHolder(LayoutInflater.from(activity).inflate(layoutId, null));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            BindViewManager bindViewManager = new BindViewManager(activity);
            bindViewManager.setBindModeAndViewIds(bindMode, bindViewIds);
            bindViewManager.bindData(datas.get(position), holder.view);
        }

        @Override
        public int getItemCount() {
            if (datas != null) {
                return datas.size();
            }
            return 0;
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            public View view;

            public CustomViewHolder(View view) {
                super(view);
                this.view = view;
            }
        }
    }

    public void setBindModeAndViewIds(BindMode bindMode , String[] bindViewIdList) {
        this.bindMode = bindMode;
        this.bindViewIds = bindViewIdList;
    }
}
