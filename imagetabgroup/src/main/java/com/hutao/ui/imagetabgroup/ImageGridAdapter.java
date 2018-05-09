package com.hutao.ui.imagetabgroup;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.LinkedList;

/**
 * Created by 胡涛 on 2018/5/9.
 */

public abstract class ImageGridAdapter<T> extends BaseAdapter {

    private Context mContext;
    private LinkedList<T> mImagePathList;//图片路径列表
    private boolean mIsAbleEdit = false;//是否为编辑状态，默认为否
    private int mDeleteSrcResId;//删除图片icon的资源id
    private int mImgWidth, mImgHeight;//图片的宽高

    public void setmImgSize(int mImgWidth, int mImgHeight) {
        this.mImgWidth = mImgWidth;
        this.mImgHeight = mImgHeight;
    }

    //使用默认删除图标
    public ImageGridAdapter(Context mContext, LinkedList<T> mImagePathList, boolean mIsAbleEdit) {
        this.mContext = mContext;
        this.mImagePathList = mImagePathList;
        this.mIsAbleEdit = mIsAbleEdit;
    }

    //使用自定义删除图标
    public ImageGridAdapter(LinkedList<T> imagePathList, boolean isAbleEdit, int deleteSrcResId, Context mContext) {
        this.mImagePathList = imagePathList;
        this.mIsAbleEdit = isAbleEdit;
        this.mDeleteSrcResId = deleteSrcResId;
        this.mContext = mContext;
    }

    public void updateList(LinkedList<T> updateImagePathList) {
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
        return mImagePathList.size();
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
        viewHolder.ivDelete.setVisibility(mIsAbleEdit ? View.VISIBLE : View.GONE);
        viewHolder.ivDelete.setImageResource(mDeleteSrcResId == 0 ? R.drawable.delete : mDeleteSrcResId);
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

        //给icon图片排位
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) viewHolder.ivIcon.getLayoutParams();
        lp.gravity = Gravity.CENTER;
        lp.width = mImgWidth;
        lp.height = mImgHeight;
        lp.setMargins(
                DensityUtil.dip2px(mContext, 10),
                DensityUtil.dip2px(mContext, 10),
                DensityUtil.dip2px(mContext, 10),
                DensityUtil.dip2px(mContext, 10)
        );
        viewHolder.ivIcon.setScaleType(ImageView.ScaleType.FIT_XY);
        viewHolder.ivIcon.setLayoutParams(lp);

        //给删除图标排位
        FrameLayout.LayoutParams deLp = (FrameLayout.LayoutParams) viewHolder.ivDelete.getLayoutParams();
        deLp.gravity = Gravity.CENTER;
        deLp.setMargins(mImgWidth / 2, 0, 0, mImgHeight / 2);
        viewHolder.ivDelete.setLayoutParams(deLp);
        return convertView;
    }

    private class ViewHolder {
        ImageView ivIcon, ivDelete;
    }

    /**
     * 图片加载，根据用户需求自定义加载方式
     */
    abstract void loadImage(ImageView imageView, T t);

    /**
     * 点击某个item的删除图标
     */
    abstract void deleteImage(int position);

    /**
     * 点击某张图片
     */
    abstract void clickItem(int position);
}
