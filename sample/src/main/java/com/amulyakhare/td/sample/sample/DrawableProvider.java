package com.amulyakhare.td.sample.sample;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.TypedValue;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

/**
 * @author amulya
 * @datetime 17 Oct 2014, 4:02 PM
 */
public class DrawableProvider {

    public static final int SAMPLE_RECT = 1;
    public static final int SAMPLE_ROUND_RECT = 2;
    public static final int SAMPLE_ROUND = 3;
    public static final int SAMPLE_RECT_BORDER = 4;
    public static final int SAMPLE_ROUND_RECT_BORDER = 5;
    public static final int SAMPLE_ROUND_BORDER = 6;
    public static final int SAMPLE_MULTIPLE_LETTERS = 7;
    public static final int SAMPLE_FONT = 8;
    public static final int SAMPLE_SIZE = 9;
    public static final int SAMPLE_ANIMATION = 10;
    public static final int SAMPLE_MISC = 11;

    private final ColorGenerator mGenerator;
    private final Context mContext;

    public DrawableProvider(Context context) {
        mGenerator = ColorGenerator.DEFAULT;
        mContext = context;
    }

    public TextDrawable getRect(String text) {
        return TextDrawable.builder()
                .buildRect(text, mGenerator.getColor(text));
    }

    public TextDrawable getRound(String text) {
        return TextDrawable.builder()
                .buildRound(text, mGenerator.getColor(text));
    }

    public TextDrawable getRoundRect(String text) {
        return TextDrawable.builder()
                .buildRoundRect(text, mGenerator.getColor(text), toPx(10));
    }

    public TextDrawable getRectWithBorder(String text) {
        return TextDrawable.builder()
                .beginConfig()
                    .withBorder(toPx(2))
                .endConfig()
                .buildRect(text, mGenerator.getColor(text));
    }

    public TextDrawable getRoundWithBorder(String text) {
        return TextDrawable.builder()
                .beginConfig()
                    .withBorder(toPx(2))
                .endConfig()
                .buildRound(text, mGenerator.getColor(text));
    }

    public TextDrawable getRoundRectWithBorder(String text) {
        return TextDrawable.builder()
                .beginConfig()
                    .withBorder(toPx(2))
                .endConfig()
                .buildRoundRect(text, mGenerator.getColor(text), toPx(10));
    }

    public TextDrawable getRectWithMultiLetter() {
        String text = "AK";
        return TextDrawable.builder()
                .beginConfig()
                    .fontSize(toPx(20))
                    .toUpperCase()
                .endConfig()
                .buildRect(text, mGenerator.getColor(text));
    }

    public TextDrawable getRoundWithCustomFont() {
        String text = "Bold";
        return TextDrawable.builder()
                .beginConfig()
                    .useFont(Typeface.DEFAULT)
                    .fontSize(toPx(15))
                    .textColor(0xfff58559)
                    .bold()
                .endConfig()
                .buildRect(text, Color.DKGRAY /*toPx(5)*/);
    }

    public Drawable getRectWithCustomSize() {
        String leftText = "I";
        String rightText = "J";

        TextDrawable.IBuilder builder = TextDrawable.builder()
                .beginConfig()
                    .width(toPx(29))
                    .withBorder(toPx(2))
                .endConfig()
                .rect();

        TextDrawable left = builder
                .build(leftText, mGenerator.getColor(leftText));

        TextDrawable right = builder
                .build(rightText, mGenerator.getColor(rightText));

        Drawable[] layerList = {
                new InsetDrawable(left, 0, 0, toPx(31), 0),
                new InsetDrawable(right, toPx(31), 0, 0, 0)
        };
        return new LayerDrawable(layerList);
    }

    public Drawable getRectWithAnimation() {
        TextDrawable.IBuilder builder = TextDrawable.builder()
                .rect();

        AnimationDrawable animationDrawable = new AnimationDrawable();
        for (int i = 10; i > 0; i--) {
            TextDrawable frame = builder.build(String.valueOf(i), mGenerator.getRandomColor());
            animationDrawable.addFrame(frame, 1200);
        }
        animationDrawable.setOneShot(false);
        animationDrawable.start();

        return animationDrawable;
    }

    public int toPx(int dp) {
        Resources resources = mContext.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }
}
