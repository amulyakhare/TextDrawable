package com.amulyakhare.td.sample.sample;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

/**
 * @author amulya
 * @datetime 17 Oct 2014, 3:49 PM
 */
public class DataSource {

    public static final int NO_NAVIGATION = -1;

    private ArrayList<DataItem> mDataSource;
    private DrawableProvider mProvider;

    public DataSource(Context context) {
        mProvider = new DrawableProvider(context);
        mDataSource = new ArrayList<DataItem>();
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_RECT));
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_ROUND_RECT));
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_ROUND));
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_RECT_BORDER));
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_ROUND_RECT_BORDER));
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_ROUND_BORDER));
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_MULTIPLE_LETTERS));
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_FONT));
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_SIZE));
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_ANIMATION));
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_MISC));
    }

    public int getCount() {
        return mDataSource.size();
    }

    public DataItem getItem(int position) {
        return mDataSource.get(position);
    }

    private DataItem itemFromType(int type) {
        String label = null;
        Drawable drawable = null;
        switch (type) {
            case DrawableProvider.SAMPLE_RECT:
                label = "Rectangle with Text";
                drawable = mProvider.getRect("A");
                break;
            case DrawableProvider.SAMPLE_ROUND_RECT:
                label = "Round Corner with Text";
                drawable = mProvider.getRoundRect("B");
                break;
            case DrawableProvider.SAMPLE_ROUND:
                label = "Round with Text";
                drawable = mProvider.getRound("C");
                break;
            case DrawableProvider.SAMPLE_RECT_BORDER:
                label = "Rectangle with Border";
                drawable = mProvider.getRectWithBorder("D");
                break;
            case DrawableProvider.SAMPLE_ROUND_RECT_BORDER:
                label = "Round Corner with Border";
                drawable = mProvider.getRoundRectWithBorder("E");
                break;
            case DrawableProvider.SAMPLE_ROUND_BORDER:
                label = "Round with Border";
                drawable = mProvider.getRoundWithBorder("F");
                break;
            case DrawableProvider.SAMPLE_MULTIPLE_LETTERS:
                label = "Support multiple letters";
                drawable = mProvider.getRectWithMultiLetter();
                type = NO_NAVIGATION;
                break;
            case DrawableProvider.SAMPLE_FONT:
                label = "Support variable font styles";
                drawable = mProvider.getRoundWithCustomFont();
                type = NO_NAVIGATION;
                break;
            case DrawableProvider.SAMPLE_SIZE:
                label = "Support for custom size";
                drawable = mProvider.getRectWithCustomSize();
                type = NO_NAVIGATION;
                break;
            case DrawableProvider.SAMPLE_ANIMATION:
                label = "Support for animations";
                drawable = mProvider.getRectWithAnimation();
                type = NO_NAVIGATION;
                break;
            case DrawableProvider.SAMPLE_MISC:
                label = "Miscellaneous";
                drawable =  mProvider.getRect("\u03c0");
                type = NO_NAVIGATION;
                break;
        }
        return new DataItem(label, drawable, type);
    }
}
