package com.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import bindview.data.BindMode;
import bindview.data.ViewContainerItemMap;
import bindview.util.ArrayListUtil;
import bindview.util.FindViewUtil;
import bindview.util.ListViewBinder;
import bindview.util.RecyclerViewBinder;
import bindview.util.ReflectUtil;
import bindview.util.SimpleBindUtil;
import bindview.util.StringUtil;

/**
 * Created by LJM on 2017/8/7 14:02.
 * Description:
 */

public class BindViewManager {

    private BindMode bindMode;

    private Activity activity;
    private Fragment fragment;
    private ArrayList<ListView> listViews;
    private ArrayList<RecyclerView> recyclerViews;
    private String[] bindViewIds;

    private ArrayList<ViewContainerItemMap> viewContainerItemMaps;

    /**
     * 绑定Activity
     *
     * @param activity
     */
    public BindViewManager(Activity activity) {
        this.activity = activity;
        this.bindMode = BindMode.BIND_ALL;
        listViews = new ArrayList<>();
        recyclerViews = new ArrayList<>();
        viewContainerItemMaps = new ArrayList<>();
    }

    /**
     * 绑定Fragment
     *
     * @param fragment
     */
    public BindViewManager(Fragment fragment) {
        this.fragment = fragment;
        if (fragment != null) {
            this.activity = fragment.getActivity();
        }
        this.bindMode = BindMode.BIND_ALL;
        listViews = new ArrayList<>();
        recyclerViews = new ArrayList<>();
        viewContainerItemMaps = new ArrayList<>();
    }

    /**
     * 填充某个Activity包含的页面中的View的数据
     *
     * @param object
     */
    public void bindData(Object object) {
        ArrayList<String> stringArrayList = ReflectUtil.getAllKeys(object);
        for (String keyFromObject : stringArrayList) {

            View viewFindByKey;
            if (fragment != null) {
                viewFindByKey = FindViewUtil.getViewByObjectFieldName(fragment, keyFromObject, true);
            } else if (activity != null) {
                viewFindByKey = FindViewUtil.getViewByObjectFieldName(activity, keyFromObject, true);
            } else {
                return;
            }
            bindDataToView(object, keyFromObject, viewFindByKey);
        }
    }

    /**
     * 将数据填充到具有id的View中
     *
     * @param object
     * @param keyFromObject
     * @param viewFindByKey
     */
    private void bindDataToView(Object object, String keyFromObject, View viewFindByKey) {
        if (viewFindByKey != null) {
            Object objectFromObject = ReflectUtil.getObjectByKey(object, keyFromObject);
            boolean flag = ArrayListUtil.isArrayOrList(objectFromObject);

            //不需要绑定的情况
            if (!isNeedBind(objectFromObject, keyFromObject)) {
                return;
            }

            if (flag == false) {
                String value = ReflectUtil.getValueByKey(object, keyFromObject);
                bindDataToSingleView(viewFindByKey, value);
            } else {
                ArrayList<Object> datas = ArrayListUtil.objectToArray(objectFromObject);
                if (getLayoutIdByKey(keyFromObject) != 0) {
                    bindDataToViewContainer(activity, viewFindByKey, datas,
                            getLayoutIdByKey(keyFromObject));
                }
            }

        }
    }

    /**
     * 填充根目录中的所有View的数据
     *
     * @param object
     */
    public void bindData(Object object, View view) {
        ArrayList<String> stringArrayList = ReflectUtil.getAllKeys(object);
        for (String keyFromObject : stringArrayList) {
            View viewFindByKey = FindViewUtil.getViewByObjectFieldName(activity, view, keyFromObject, true);
            bindDataToView(object, keyFromObject, viewFindByKey);
        }
    }

    /**
     * 填充数据到某一个View，该View是一个具体的控件，不是ViewGroup
     *
     * @param view
     * @param string
     */
    private void bindDataToSingleView(View view, String string) {
        if (view instanceof TextView) {
            SimpleBindUtil.bindDataToTextView((TextView) view, string);
        } else if (view instanceof ImageView) {
            SimpleBindUtil.bindDataToImageView((ImageView) view, string);
        }
    }

    /**
     * 填充数据到某一个View，该View是一个ListView 或者recycleview
     *
     * @param activity
     * @param view
     * @param datas
     * @param layoutId
     */
    private void bindDataToViewContainer(Activity activity, View view, ArrayList<Object> datas, int layoutId) {
        if (view instanceof ListView) {
            ListViewBinder listViewBinder = new ListViewBinder(activity, (ListView) view, datas, layoutId);
            listViewBinder.setBindModeAndViewIds(bindMode, bindViewIds);
            listViewBinder.bindData();
            listViews.add((ListView) view);
        } else if (view instanceof RecyclerView) {
            RecyclerViewBinder recyclerViewBinder = new RecyclerViewBinder(activity, (RecyclerView) view, datas, layoutId, bindMode);
            recyclerViewBinder.setBindModeAndViewIds(bindMode, bindViewIds);
            recyclerViewBinder.bindData();
            recyclerViews.add((RecyclerView) view);
        }
    }

    /**
     * @param viewIdName
     * @param layoutId
     */
    public void bindContainerWithItem(String viewIdName, int layoutId) {
        viewContainerItemMaps.add(new ViewContainerItemMap(viewIdName, layoutId));
    }

    /**
     * 获取对应的子布局Id
     *
     * @param key
     * @return
     */
    private int getLayoutIdByKey(String key) {
        if (viewContainerItemMaps == null || viewContainerItemMaps.size() < 1 || TextUtils.isEmpty(key)) {
            return 0;
        }

        for (ViewContainerItemMap itemMap : viewContainerItemMaps) {
            if (key.equals(itemMap.key)) {
                return itemMap.layoutId;
            }
        }
        return 0;
    }

    /**
     * 判断一个Id需不需要绑定
     *
     * @param key
     * @return
     */
    private boolean isNeedBind(Object object, String key) {
        switch (bindMode) {
            case BIND_ALL:
                return true;
            case BIND_ONLY_INPUT:
                if (StringUtil.isKeyAtArray(bindViewIds, key)) {
                    return true;
                }
                return false;
            case BIND_EXCEPT_INPUT:
                if (StringUtil.isKeyAtArray(bindViewIds, key)) {
                    return false;
                }
                return true;
            case BIND_EXCEPT_CONTAINER:
                if (ArrayListUtil.isArrayOrList(object)) {
                    return false;
                } else {
                    return true;
                }
            default:
                return true;//默认为需要绑定
        }
    }

    /**
     * 回传ListView，可能有多个
     *
     * @return
     */
    public ArrayList<ListView> getListViews() {
        return listViews;
    }

    /**
     * 回传RecycleView 可能有多个
     *
     * @return
     */
    public ArrayList<RecyclerView> getRecyclerViews() {
        return recyclerViews;
    }

    public void setBindModeAndViewIds(BindMode bindMode, String... strings) {
        this.bindMode = bindMode;
        bindViewIds = strings;
    }
}
