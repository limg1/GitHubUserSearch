package com.limg1.githubsrchuser.ui.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.limg1.githubsrchuser.R;
import com.limg1.githubsrchuser.data.remote.model.GitUser;

import java.util.List;


class UsersAdapter extends RecyclerView.Adapter<UserViewHolder> {
    private final Context context;
    private boolean isLike;
    private List<GitUser> items;

    private ItemClick itemClick;

    public interface ItemClick {
        public void onClick(View view, int position);
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    UsersAdapter(List<GitUser> items, Context context, boolean isLike) {
        this.items = items;
        this.context = context;
        this.isLike = isLike;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        GitUser item = items.get(position);

        if (item.getLike() != null && item.getLike()) {
            holder.imageViewLike.setVisibility(View.VISIBLE);
            holder.constraintLayout.setVisibility(View.VISIBLE);
            holder.hideRow.setVisibility(View.GONE);
        }
        else if (isLike) {
            holder.constraintLayout.setVisibility(View.GONE);
            holder.hideRow.setVisibility(View.VISIBLE);
//            holder.Layout_hide();
        }
        else {
            holder.imageViewLike.setVisibility(View.GONE);
            holder.constraintLayout.setVisibility(View.VISIBLE);
            holder.hideRow.setVisibility(View.GONE);
        }
        //holder.textViewBio.setText(item.getUrl());
        if (item.getName() != null) {
            holder.textViewName.setText(item.getLogin() + " - " + item.getName());
        } else {
            holder.textViewName.setText(item.getLogin());
        }
        Glide.with(context).load(item.getAvatarUrl()).into(holder.imageViewAvatar);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick != null) {
                    itemClick.onClick(v, position);

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    void setItems(List<GitUser> githubUserList) {
        this.items = githubUserList;
        notifyDataSetChanged();
    }
}