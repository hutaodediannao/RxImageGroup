package com.hutao.ui.imagetabgroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import java.util.LinkedList;

/**
 * Created by 胡涛 on 2018/5/9.
 */

public abstract class ImageGridAdapter extends BaseAdapter {

    private LinkedList<String> mImagePathList;//图片路径列表
    private Context mContext;
    private boolean mIsAbleEdit = false;//是否为编辑状态，默认为否

    public ImageGridAdapter(LinkedList<String> imagePathList,boolean isAbleEdit, Context mContext) {
        this.mImagePathList = imagePathList;
        this.mIsAbleEdit = isAbleEdit;
        this.mContext = mContext;
    }

    public void updateList(LinkedList<String> updateImagePathList) {
        this.mImagePathList = updateImagePathList;
        this.notifyDataSetChanged();
    }

    public boolean ismIsAbleEdit() {
        return mIsAbleEdit;
    }

    public void setmIsAbleEdit(boolean mIsAbleEdit) {
        this.mIsAbleEdit = mIsAbleEdit;
    }

    @Override
    public int getCount() {
        return mImagePathList.size() + (mIsAbleEdit?1:0);
    }

    @Override
    public Object getItem(int i) {
        return mImagePathList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_tab_lay, null, false);
            viewHolder = new ViewHolder();
            viewHolder.ivIcon = convertView.findViewById(R.id.ivIcon);
            viewHolder.ivDelete = convertView.findViewById(R.id.ivDelete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //是否可以编辑，判断是否展示删除tub
        viewHolder.ivDelete.setVisibility(ismIsAbleEdit()?View.VISIBLE:View.GONE);
        //展示图片
        loadImage(viewHolder.ivIcon, mImagePathList.get(position));
        viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteImage(position);
            }
        });
        viewHolder.ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickItem(position);
            }
        });

        return convertView;
    }

    private class ViewHolder{
        ImageView ivIcon, ivDelete;
    }

    /**
     * 图片加载，根据用户需求自定义加载方式
     * @param imageView
     * @param path
     */
    abstract void loadImage(ImageView imageView, String path);

    /**
     * 点击某个item的删除图标
     * @param position
     */
    abstract void deleteImage(int position);

    /**
     * 点击某张图片
     * @param position
     */
    abstract void clickItem(int position);
}
