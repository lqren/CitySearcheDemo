package com.project.citysearchedemo.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.citysearchedemo.R;
import com.project.citysearchedemo.bean.Entity;
import com.project.citysearchedemo.utils.DisplayUtil;

import java.util.List;

/**
 * Created by SouthernBox on 2016/10/25 0025.
 * 适配器
 */

public class MainAdapter extends RecyclerView.Adapter {

    public final static int VIEW_INDEX = 0;
    public final static int VIEW_CONTENT = 1;

    private Context mContext;
    private List<Entity> mList;

    public MainAdapter(Context context, List<Entity> List) {
        this.mContext = context;
        this.mList = List;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_INDEX) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_index, parent, false);
            IndexViewHolder holder = new IndexViewHolder(view);
            holder.tvIndex = (TextView) view.findViewById(R.id.tv_index);
            return holder;
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_content, parent, false);
            ContentViewHolder holder = new ContentViewHolder(view);
            holder.tvName = (TextView) view.findViewById(R.id.tv_name);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_INDEX) {
            ((IndexViewHolder) holder).tvIndex.setText(mList.get(position).getFirstWord());
        } else {
            if (TextUtils.equals(mList.get(position).getFirstWord(), "当前城市")) {
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.city_item_shape);
                ((ContentViewHolder) holder).tvName.setBackgroundDrawable(drawable);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.leftMargin = DisplayUtil.dip2px(mContext, 15);
                layoutParams.gravity = Gravity.CENTER_VERTICAL;
                ((ContentViewHolder) holder).tvName.setPadding(DisplayUtil.dip2px(mContext, 10), DisplayUtil.dip2px(mContext, 5), DisplayUtil.dip2px(mContext, 10), DisplayUtil.dip2px(mContext, 5));
                ((ContentViewHolder) holder).tvName.setLayoutParams(layoutParams);
            }else{
                ((ContentViewHolder) holder).tvName.setBackgroundDrawable(null);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.leftMargin = DisplayUtil.dip2px(mContext, 15);
                ((ContentViewHolder) holder).tvName.setPadding(0,0,0,0);
                ((ContentViewHolder) holder).tvName.setLayoutParams(layoutParams);
            }
            ((ContentViewHolder) holder).tvName.setText(mList.get(position).getName());

        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).isIndex()) {
            return VIEW_INDEX;
        } else {
            return VIEW_CONTENT;
        }
    }

    private static class IndexViewHolder extends RecyclerView.ViewHolder {
        TextView tvIndex;

        IndexViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        ContentViewHolder(View itemView) {
            super(itemView);
        }
    }
}
