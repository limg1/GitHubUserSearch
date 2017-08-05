package com.limg1.githubsrchuser.ui.search;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.limg1.githubsrchuser.R;


class UserViewHolder extends RecyclerView.ViewHolder {
    final TextView textViewBio;
    final TextView textViewName;
    final ImageView imageViewAvatar;
    final ImageView imageViewLike;
//    final LinearLayout.LayoutParams layoutParams;
    final ConstraintLayout constraintLayout;
    final View hideRow;

    final View view;

    UserViewHolder(View v ) {
        super(v);
        this.view = v;
//        layoutParams =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);


        constraintLayout = (ConstraintLayout) v.findViewById(R.id.constraintLayout);
        hideRow = (View) v.findViewById(R.id.hideRow);
        imageViewAvatar = (ImageView) v.findViewById(R.id.imgv_userprofilepic);
        imageViewLike = (ImageView) v.findViewById(R.id.imgv_like);
        textViewName = (TextView) v.findViewById(R.id.textview_username);
        textViewBio = (TextView) v.findViewById(R.id.textview_user_profile_info);
    }
//    public void Layout_hide() {
//        layoutParams.height = 0;
//        //itemView.setLayoutParams(params); //This One.
//        view.setLayoutParams(layoutParams);   //Or This one.
//
//    }
}
