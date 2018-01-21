package com.jackwu.news.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jackwu.news.R;

/**
 * ViewHolder
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> views;
    private View convertView;


    public RecyclerViewHolder(View convertView) {
        super(convertView);

        views = new SparseArray<>();
        this.convertView = convertView;
    }

    public static RecyclerViewHolder getInstance(Context context, ViewGroup parent, int layout) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    public <T extends View> T getView(int resId) {
        View view = views.get(resId);
        if (view == null) {
            view = convertView.findViewById(resId);
            views.put(resId, view);
        }
        return (T) view;
    }

    public RecyclerViewHolder setTextView(int tvId, String text) {//为TextView设置文本，下同
        TextView tv = getView(tvId);
        tv.setText(text);
        return this;
    }

    public RecyclerViewHolder setTextView(int tvId, String text, View.OnClickListener listener) {
        TextView tv = getView(tvId);
        tv.setText(text);
        tv.setOnClickListener(listener);
        return this;
    }

    public RecyclerViewHolder setCheckBox(int cbId, boolean checked) {
        CheckBox cb = getView(cbId);
        cb.setChecked(checked);
        return this;
    }

    public RecyclerViewHolder setCheckBox(int cbId, boolean checked, String text) {
        CheckBox cb = getView(cbId);
        cb.setText(text);
        cb.setChecked(checked);
        return this;
    }

    public RecyclerViewHolder setCheckBox(int cbId, String text, View.OnClickListener listener) {
        CheckBox cb = getView(cbId);
        cb.setText(text);
        cb.setOnClickListener(listener);
        return this;
    }


    public RecyclerViewHolder setCheckBox(int cbId, boolean checked, String text, View.OnClickListener listener) {
        CheckBox cb = getView(cbId);
        cb.setText(text);
        cb.setChecked(checked);
        cb.setOnClickListener(listener);
        return this;
    }

    public RecyclerViewHolder setCheckBox(int cbId, View.OnClickListener listener) {
        CheckBox cb = getView(cbId);
        cb.setOnClickListener(listener);
        return this;
    }

    public RecyclerViewHolder setImageView(int imgId, Drawable drawable) {
        ImageView img = getView(imgId);
        img.setImageDrawable(drawable);
        return this;
    }

    public RecyclerViewHolder setImageView(int imgId, int res) {
        ImageView img = getView(imgId);
        img.setImageResource(res);
        return this;
    }

    public RecyclerViewHolder setImageView(int imgId, int res, View.OnClickListener listener) {
        ImageView img = getView(imgId);
        img.setImageResource(res);
        img.setOnClickListener(listener);
        return this;
    }


    public RecyclerViewHolder setSwitch(int swId, boolean isChecked, CompoundButton.OnCheckedChangeListener listener) {
        Switch swi = getView(swId);
        swi.setChecked(isChecked);
        swi.setOnCheckedChangeListener(listener);
        return this;
    }

    public RecyclerViewHolder setImageView(int imgId, String url) {
        ImageView img = getView(imgId);
        //.skipMemoryCache(true)
        Glide.with(convertView.getContext()).load(url).centerCrop().placeholder(R.drawable.ic_default_image)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade().into(img);
        return this;
    }

    public RecyclerViewHolder setImageView(int imgId, Bitmap bitmap) {
        ImageView img = getView(imgId);
        img.setImageBitmap(bitmap);
        return this;
    }

    public RecyclerViewHolder setImageView(int imgId, Uri uri) {
        ImageView img = getView(imgId);
        img.setImageURI(uri);
        return this;
    }

    public RecyclerViewHolder setImageView(int imgId, View.OnClickListener listener) {
        ImageView img = getView(imgId);
        img.setOnClickListener(listener);
        return this;
    }

    public RecyclerViewHolder setImageView(int imgId, Icon icon) {
        ImageView img = getView(imgId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            img.setImageIcon(icon);
        return this;
    }

    public RecyclerViewHolder setButton(int btnId, View.OnClickListener listener) {
        Button button = getView(btnId);
        button.setOnClickListener(listener);
        return this;
    }

    public RecyclerViewHolder setButton(int btnId, String text, View.OnClickListener listener) {
        Button button = getView(btnId);
        button.setOnClickListener(listener);
        button.setText(text);
        return this;
    }

    public RecyclerViewHolder setButton(int btnId, String text) {
        Button button = getView(btnId);
        button.setText(text);
        return this;
    }

    public RecyclerViewHolder setImageButton(int btnId, Bitmap bitmap, View.OnClickListener listener) {
        ImageButton imageButton = getView(btnId);
        imageButton.setImageBitmap(bitmap);
        imageButton.setOnClickListener(listener);
        return this;
    }

    public RecyclerViewHolder setImageButton(int btnId, View.OnClickListener listener) {
        ImageButton imageButton = getView(btnId);
        imageButton.setOnClickListener(listener);
        return this;
    }

    public RecyclerViewHolder setImageButton(int btnId, int res, View.OnClickListener listener) {
        ImageButton imageButton = getView(btnId);
        imageButton.setImageResource(res);
        imageButton.setOnClickListener(listener);
        return this;
    }

    public RecyclerViewHolder setImageButton(int btnId, Drawable drawable, View.OnClickListener listener) {
        ImageButton imageButton = getView(btnId);
        imageButton.setImageDrawable(drawable);
        imageButton.setOnClickListener(listener);
        return this;
    }

    public RecyclerViewHolder setImageButton(int btnId, Uri uri, View.OnClickListener listener) {
        ImageButton imageButton = getView(btnId);
        imageButton.setImageURI(uri);
        imageButton.setOnClickListener(listener);
        return this;
    }

    public RecyclerViewHolder setImageButton(int btnId, Matrix matrix, View.OnClickListener listener) {
        ImageButton imageButton = getView(btnId);
        imageButton.setImageMatrix(matrix);
        imageButton.setOnClickListener(listener);
        return this;
    }
}
