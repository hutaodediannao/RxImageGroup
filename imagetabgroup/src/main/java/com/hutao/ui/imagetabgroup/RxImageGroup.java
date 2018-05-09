package com.hutao.ui.imagetabgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import java.util.LinkedList;

/**
 * 图片删除和预览缩微图的控件
 * Created by 胡涛 on 2018/5/9.
 */

public class RxImageGroup<T> extends FrameLayout {

    private int row;//横向排位的列数
    private GridView mGridView;//图片容器
    private ImageGridAdapter mImageGridAdapter;
    private int imgWidth, imgHeight;//单张图片的宽高
    private int deleteIconSrc;//右上角的叉叉图片资源id
    private LinkedList<T> mImagePathList = new LinkedList<T>();//图片地址集合
    private boolean isAbleEdit;//是否可以被剪辑，如果可以编辑，会显示叉叉删除按钮
    private static final int deleteIvIconHeight = 20;//表示删除叉叉的高度默认为20dp的高度，写死

    public RxImageGroup(Context context) {
        this(context, null);
    }

    public RxImageGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RxImageGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RxImageGroup);
        row = typedArray.getInt(R.styleable.RxImageGroup_row, 3);
        deleteIconSrc = typedArray.getResourceId(R.styleable.RxImageGroup_deleteIconSrc, R.drawable.delete);
        isAbleEdit = typedArray.getBoolean(R.styleable.RxImageGroup_isAbleEdit, false);
        typedArray.recycle();
        updateUI();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (row ==0) return;
        imgWidth = getMeasuredWidth() / row - DensityUtil.dip2px(getContext(), deleteIvIconHeight);
        double heightNum = Math.ceil(mImagePathList.size()*1.0d / row);//向上取整获取行数
        if (heightNum == 0) return;
        imgHeight = (int) (getMeasuredHeight() / heightNum - DensityUtil.dip2px(getContext(), deleteIvIconHeight));
        //取正方形图片
        int size = (imgWidth > imgHeight ? imgHeight : imgWidth);
        mImageGridAdapter.setmImgSize(size, size);
        //开始适配
        mImageGridAdapter.updateList(mImagePathList);
    }

    public boolean isAbleEdit() {
        return this.mImageGridAdapter.ismIsAbleEdit();
    }

    public RxImageGroup setAbleEdit(boolean ableEdit) {
        this.isAbleEdit = ableEdit;
        mImageGridAdapter.setmIsAbleEdit(this.isAbleEdit);
        return this;
    }

    private void updateUI() {
        mGridView = (GridView) LayoutInflater.from(getContext()).inflate(R.layout.image_group_tab_layout, null, false);
        mImageGridAdapter = new ImageGridAdapter(mImagePathList, isAbleEdit, deleteIconSrc, getContext()) {
            @Override
            void loadImage(ImageView imageView, Object t) {
                if (mRxImageGroupListener != null) mRxImageGroupListener.loadImage(imageView, t);
            }

            @Override
            void deleteImage(int position) {
                if (mRxImageGroupListener != null) mRxImageGroupListener.deleteItem(position);
            }

            @Override
            void clickItem(int position) {
                if (mRxImageGroupListener != null) mRxImageGroupListener.clickItem(position);
            }
        };
        mGridView.setAdapter(mImageGridAdapter);
        mGridView.setNumColumns(row);
        this.addView(mGridView);
    }

    public RxImageGroup updateData(LinkedList<T> imagePathList) {
        this.mImagePathList = imagePathList;
        return this;
    }

    public RxImageGroup deletePosition(int position) {
        this.mImagePathList.remove(position);
        mImageGridAdapter.notifyDataSetChanged();
        return this;
    }

    public interface RxImageGroupListener{
        void loadImage(ImageView imageView, Object t);//加载图片使用自己的方法

        void clickItem(int position);//点击图片，返回下标位置

        void deleteItem(int position);//点击右上角的叉叉按钮，删除图片
    }

    private RxImageGroupListener mRxImageGroupListener;

    public RxImageGroup setmRxImageGroupListener(RxImageGroupListener mRxImageGroupListener) {
        this.mRxImageGroupListener = mRxImageGroupListener;
        return this;
    }
}
