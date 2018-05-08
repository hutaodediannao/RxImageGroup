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
 * Created by Administrator on 2018/5/9.
 */

public class RxImageGroup extends FrameLayout {

    private GridView mGridView;
    private LinkedList<String> mImagePathList = new LinkedList<>();
    private ImageGridAdapter mImageGridAdapter;

    public RxImageGroup(Context context) {
        this(context, null);
    }

    public RxImageGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private int row;
    private float imgTabWidth, imgTabHeight, deleteIconWidth;
    private int deleteIconSrc;
    private boolean isAbleEdit;

    public RxImageGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RxImageGroup);
        row = typedArray.getInt(R.styleable.RxImageGroup_row, 3);
        imgTabWidth = typedArray.getDimension(R.styleable.RxImageGroup_imgTabWidth, R.dimen._50dp);
        imgTabHeight = typedArray.getDimension(R.styleable.RxImageGroup_imgTabHeight, R.dimen._50dp);
        deleteIconWidth = typedArray.getDimension(R.styleable.RxImageGroup_deleteIconWidth, R.dimen._20dp);
        deleteIconSrc = typedArray.getResourceId(R.styleable.RxImageGroup_deleteIconSrc, android.R.drawable.ic_delete);
        isAbleEdit = typedArray.getBoolean(R.styleable.RxImageGroup_isAbleEdit, false);
        typedArray.recycle();
        updateUI();
    }

    private void updateUI() {
        mGridView = (GridView) LayoutInflater.from(getContext()).inflate(R.layout.image_group_tab_layout, null, false);
        mGridView.setNumColumns(row);
        mImageGridAdapter = new ImageGridAdapter(mImagePathList, isAbleEdit, getContext()) {
            @Override
            void loadImage(ImageView imageView, String path) {
                if (mRxImageGroupListener != null) mRxImageGroupListener.loadImage(imageView, path);
            }

            @Override
            void deleteImage(int position) {
                mImagePathList.remove(position);
                mImageGridAdapter.notifyDataSetChanged();
            }

            @Override
            void clickItem(int position) {
                if (mRxImageGroupListener != null) mRxImageGroupListener.clickItem(position);
            }
        };

        mGridView.setAdapter(mImageGridAdapter);
    }

    public void updateData(LinkedList<String> imagePathList) {
        mImageGridAdapter.updateList(imagePathList);
    }

    public interface RxImageGroupListener {
        void loadImage(ImageView imageView, String path);

        void clickItem(int position);
    }

    private RxImageGroupListener mRxImageGroupListener;

    public void setmRxImageGroupListener(RxImageGroupListener mRxImageGroupListener) {
        this.mRxImageGroupListener = mRxImageGroupListener;
    }
}
