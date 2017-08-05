package com.limg1.githubsrchuser.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

import com.limg1.githubsrchuser.R;
import com.limg1.githubsrchuser.data.CommonText;
import com.limg1.githubsrchuser.data.remote.model.GitUser;

import java.util.ArrayList;
import java.util.List;


public class UserLikeActivity extends AppCompatActivity {

    private UsersAdapter usersAdapter;
    private ProgressBar progressBar;
    private RecyclerView recyclerViewUsers;

    List<GitUser> githubUserLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_like);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        recyclerViewUsers = (RecyclerView) findViewById(R.id.recycler_view_users);

        if(getIntent()!=null){
            githubUserLists = (ArrayList<GitUser>)getIntent().getSerializableExtra(CommonText.INTENTDATA_RESULTUSERS);
        }
        usersAdapter = new UsersAdapter(githubUserLists, this, true);
        recyclerViewUsers.setAdapter(usersAdapter);

        usersAdapter.setItemClick(new UsersAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {
                githubUserLists.get(position).setLike(false);
                usersAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra(CommonText.INTENTDATA_RESULTUSERS, (ArrayList<GitUser>) githubUserLists);
        setResult(CommonText.LIKE_REQUEST_CODE, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }
}
