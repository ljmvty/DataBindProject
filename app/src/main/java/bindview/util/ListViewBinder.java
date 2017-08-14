package bindview.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.myapplication.BindViewManager;

import java.util.ArrayList;

import bindview.data.BindMode;

/**
 * Created by LJM on 2017/8/8 9:57.
 * Description:
 */

public class ListViewBinder {

    BindMode bindMode;
    private String[] bindViewIds;

    Activity activity;
    private ListView listView;
    private int layoutId;
    private ArrayList<Object> datas;

    public ListViewBinder(Activity activity, ListView listView, ArrayList<Object> datas, int layoutId) {
        this.bindMode = BindMode.BIND_ALL;
        this.activity = activity;
        this.listView = listView;
        this.layoutId = layoutId;
        this.datas = datas;
    }

    public void bindData() {
        initAdapter(activity, datas, layoutId);
    }

    private void initAdapter(Activity activity, ArrayList<Object> datas, int layoutId) {
        CustomAdapter adapter = new CustomAdapter(activity, datas, layoutId);
        listView.setAdapter(adapter);
    }

    private class CustomAdapter extends BaseAdapter {

        private Activity activity;
        private ArrayList<Object> datas;
        private int layoutId;

        public CustomAdapter(Activity activity, ArrayList<Object> datas, int layoutId) {
            this.datas = datas;
            this.layoutId = layoutId;
            this.activity = activity;
        }

        @Override
        public int getCount() {
            if (datas != null) {
                return datas.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            final Object object = datas.get(i);
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(activity).inflate(layoutId, null);
                viewHolder.view = view;
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            BindViewManager bindViewManager = new BindViewManager(activity);
            bindViewManager.setBindModeAndViewIds(bindMode,bindViewIds);
            bindViewManager.bindData(object, viewHolder.view);
            return view;
        }

        private class ViewHolder {
            public View view;
        }
    }

    public void setBindModeAndViewIds(BindMode bindMode , String[] bindViewIdList) {
        this.bindMode = bindMode;
        this.bindViewIds = bindViewIdList;
    }
}
