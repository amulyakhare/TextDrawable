package com.amulyakhare.textdrawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.amulyakhare.textdrawable.util.TypefaceHelper;

/**
 * @author amulya
 * @datetime 14 Oct 2014, 3:53 PM
 */
public class TextDrawable extends ShapeDrawable {

    private final Paint textPaint;
    private final Paint borderPaint;
    private static final float SHADE_FACTOR = 0.9f;
    private final String text;
    private final RectShape shape;
    private final int height;
    private final int width;
    private final int fontSize;
    private final float radius;
    private final int borderThickness;

    private TextDrawable(Builder builder) {
        super(builder.shape);

        // shape properties
        shape = builder.shape;
        height = builder.height;
        width = builder.width;
        radius = builder.radius;

        // text and color
        text = builder.toUpperCase ? builder.text.toUpperCase() : builder.text;

        // text paint settings
        fontSize = builder.fontSize;
        textPaint = new Paint();
        textPaint.setColor(builder.textColor);
        textPaint.setAntiAlias(true);
        textPaint.setFakeBoldText(builder.isBold);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTypeface(builder.font);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStrokeWidth(builder.borderThickness);

        // border paint settings
        borderThickness = builder.borderThickness;
        borderPaint = new Paint();
        borderPaint.setColor(getDarkerShade(builder.color));
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderThickness);

        // drawable paint color
        Paint paint = getPaint();
        paint.setColor(builder.color);

    }

    private int getDarkerShade(@ColorInt int color) {
        return Color.rgb((int) (SHADE_FACTOR * Color.red(color)),
                (int) (SHADE_FACTOR * Color.green(color)),
                (int) (SHADE_FACTOR * Color.blue(color)));
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Rect r = getBounds();

        // draw border
        if (borderThickness > 0)
            drawBorder(canvas);

        int count = canvas.save();
        canvas.translate(r.left, r.top);

        // draw text
        int width = this.width < 0 ? r.width() : this.width;
        int height = this.height < 0 ? r.height() : this.height;
        int fontSize = this.fontSize < 0 ? (Math.min(width, height) / 2) : this.fontSize;
        textPaint.setTextSize(fontSize);
        canvas.drawText(text, width / 2, height / 2 - ((textPaint.descent() + textPaint.ascent()) / 2), textPaint);

        canvas.restoreToCount(count);

    }

    private void drawBorder(Canvas canvas) {
        RectF rect = new RectF(getBounds());
        rect.inset(borderThickness / 2, borderThickness / 2);

        if (shape instanceof OvalShape) {
            canvas.drawOval(rect, borderPaint);
        } else if (shape instanceof RoundRectShape) {
            canvas.drawRoundRect(rect, radius, radius, borderPaint);
        } else {
            canvas.drawRect(rect, borderPaint);
        }
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        textPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        textPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return width;
    }

    @Override
    public int getIntrinsicHeight() {
        return height;
    }

    public static IShapeBuilder builder(@NonNull Context context) {
        return new Builder(context);
    }

    public static class Builder implements IConfigBuilder, IShapeBuilder, IBuilder {

        private Context context;
        private String text;
        private int color;
        private int borderThickness;
        private int width;
        private int height;
        private Typeface font;
        private RectShape shape;
        public int textColor;
        private int fontSize;
        private boolean isBold;
        private boolean toUpperCase;
        public float radius;

        private Builder(@NonNull Context context) {
            this.context = context;
            text = "";
            color = Color.GRAY;
            textColor = Color.WHITE;
            borderThickness = 0;
            width = -1;
            height = -1;
            shape = new RectShape();
            fontSize = -1;
            isBold = false;
            toUpperCase = false;
        }

        @Override
        public IConfigBuilder width(@IntRange(from = 1, to = Integer.MAX_VALUE) int width) {
            this.width = width;
            return this;
        }

        @Override
        public IConfigBuilder widthRes(@DimenRes int widthRes) {
            return width((int) context.getResources().getDimension(widthRes));
        }

        @Override
        public IConfigBuilder height(@IntRange(from = 1, to = Integer.MAX_VALUE) int height) {
            this.height = height;
            return this;
        }

        @Override
        public IConfigBuilder heightRes(@DimenRes int heightRes) {
            return height((int) context.getResources().getDimension(heightRes));
        }

        @Override
        public IConfigBuilder textColor(@ColorInt int color) {
            this.textColor = color;
            return this;
        }

        @Override
        public IConfigBuilder textColorRes(@ColorRes int color) {
            //noinspection deprecation
            return textColor(context.getResources().getColor(color));
        }

        @Override
        public IConfigBuilder withBorder(@IntRange(from = 1, to = Integer.MAX_VALUE) int thickness) {
            this.borderThickness = thickness;
            return this;
        }

        @Override
        public IConfigBuilder withBorderRes(@DimenRes int thicknessRes) {
            return withBorder((int) context.getResources().getDimension(thicknessRes));
        }

        @Override
        public IConfigBuilder useFont(@NonNull Typeface font) {
            this.font = font;
            return this;
        }

        @Override
        public IConfigBuilder useFont(@NonNull String name, int style) {
            this.font = TypefaceHelper.get(name, style);
            return this;
        }

        @Override
        public IConfigBuilder fontSize(@IntRange(from = 1, to = Integer.MAX_VALUE) int size) {
            this.fontSize = size;
            return this;
        }

        @Override
        public IConfigBuilder fontSizeRes(@DimenRes int sizeRes) {
            return fontSize((int) context.getResources().getDimension(sizeRes));
        }

        @Override
        public IConfigBuilder bold() {
            this.isBold = true;
            return this;
        }

        @Override
        public IConfigBuilder toUpperCase() {
            this.toUpperCase = true;
            return this;
        }

        @Override
        public IConfigBuilder beginConfig() {
            return this;
        }

        @Override
        public IShapeBuilder endConfig() {
            return this;
        }

        @Override
        public IBuilder rect() {
            this.shape = new RectShape();
            return this;
        }

        @Override
        public IBuilder round() {
            this.shape = new OvalShape();
            return this;
        }

        @Override
        public IBuilder roundRect(@IntRange(from = 1, to = Integer.MAX_VALUE) int radius) {
            this.radius = radius;
            float[] radii = {radius, radius, radius, radius, radius, radius, radius, radius};
            this.shape = new RoundRectShape(radii, null, null);
            return this;
        }

        @Override
        public IBuilder roundRectRes(@IntRange(from = 1, to = Integer.MAX_VALUE) int radius) {
            return roundRect((int) context.getResources().getDimension(radius));
        }

        @Override
        public TextDrawable buildRect(@NonNull String text, @ColorInt int color) {
            rect();
            return build(text, color);
        }

        @Override
        public TextDrawable buildRectRes(@NonNull String text, @ColorRes int colorRes) {
            rect();
            //noinspection deprecation
            return build(text, context.getResources().getColor(colorRes));
        }

        @Override
        public TextDrawable buildRoundRect(@NonNull String text, @ColorInt int color, @IntRange(from = 1, to = Integer.MAX_VALUE) int radius) {
            roundRect(radius);
            return build(text, color);
        }

        @Override
        public TextDrawable buildRoundRectRes(@NonNull String text, @ColorRes int colorRes, @DimenRes int radiusRes) {
            //noinspection deprecation
            return buildRoundRect(text, context.getResources().getColor(colorRes),
                    (int) context.getResources().getDimension(radiusRes));
        }

        @Override
        public TextDrawable buildRound(@NonNull String text, @ColorInt int color) {
            round();
            return build(text, color);
        }

        @Override
        public TextDrawable buildRoundRes(@NonNull String text, @ColorRes int colorRes) {
            //noinspection deprecation
            return buildRound(text, context.getResources().getColor(colorRes));
        }

        @Override
        public TextDrawable build(@NonNull String text, @ColorInt int color) {
            if (this.font == null)
                this.font = TypefaceHelper.get("sans-serif-light", Typeface.NORMAL);
            this.color = color;
            this.text = text;
            return new TextDrawable(this);
        }

        @Override
        public TextDrawable buildRes(@NonNull String text, @ColorRes int colorRes) {
            //noinspection deprecation
            return build(text, context.getResources().getColor(colorRes));
        }
    }

    public interface IConfigBuilder {
        IConfigBuilder width(@IntRange(from = 1, to = Integer.MAX_VALUE) int width);

        IConfigBuilder widthRes(@DimenRes int width);

        IConfigBuilder height(@IntRange(from = 1, to = Integer.MAX_VALUE) int height);

        IConfigBuilder heightRes(@DimenRes int height);

        IConfigBuilder textColor(@ColorInt int color);

        IConfigBuilder textColorRes(@ColorRes int colorRes);

        IConfigBuilder withBorder(@IntRange(from = 1, to = Integer.MAX_VALUE) int thickness);

        IConfigBuilder withBorderRes(@DimenRes int thickness);

        IConfigBuilder useFont(@NonNull Typeface font);

        IConfigBuilder useFont(@NonNull String name, int style);

        IConfigBuilder fontSize(@IntRange(from = 1, to = Integer.MAX_VALUE) int size);

        IConfigBuilder fontSizeRes(@DimenRes int size);

        IConfigBuilder bold();

        IConfigBuilder toUpperCase();

        IShapeBuilder endConfig();
    }

    public interface IBuilder {

        TextDrawable build(@NonNull String text, @ColorInt int color);

        TextDrawable buildRes(@NonNull String text, @ColorRes int colorRes);
    }

    public interface IShapeBuilder {

        IConfigBuilder beginConfig();

        IBuilder rect();

        IBuilder round();

        IBuilder roundRect(@IntRange(from = 1, to = Integer.MAX_VALUE) int radius);

        IBuilder roundRectRes(@DimenRes int radius);

        TextDrawable buildRect(@NonNull String text, @ColorInt int color);

        TextDrawable buildRectRes(@NonNull String text, @ColorRes int colorRes);

        TextDrawable buildRoundRect(@NonNull String text, @ColorInt int color, @IntRange(from = 1, to = Integer.MAX_VALUE) int radius);

        TextDrawable buildRoundRectRes(@NonNull String text, @ColorRes int colorRes, @IntRange(from = 1, to = Integer.MAX_VALUE) int radius);

        TextDrawable buildRound(@NonNull String text, @ColorInt int color);

        TextDrawable buildRoundRes(@NonNull String text, @ColorRes int colorRes);
    }
}