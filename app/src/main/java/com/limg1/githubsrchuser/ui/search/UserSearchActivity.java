package com.limg1.githubsrchuser.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.limg1.githubsrchuser.R;
import com.limg1.githubsrchuser.data.CommonText;
import com.limg1.githubsrchuser.data.remote.Injection;
import com.limg1.githubsrchuser.data.remote.model.GitUser;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class UserSearchActivity extends AppCompatActivity implements UserSearchContract.View {

    private UserSearchContract.Presenter userSearchPresenter;
    private UsersAdapter usersAdapter;
    private SearchView searchView;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private RecyclerView recyclerViewUsers;
    private TextView textViewErrorMessage;
    private FloatingActionButton fab;

    List<GitUser> githubUserLists;
    String querySrchText;

    int cntPage =1;
    boolean isMoreData =false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);
        userSearchPresenter = new UserSearchPresenter(Injection.provideUserRepo(), Schedulers.io(),
                AndroidSchedulers.mainThread());
        userSearchPresenter.attachView(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        textViewErrorMessage = (TextView) findViewById(R.id.text_view_error_msg);
        recyclerViewUsers = (RecyclerView) findViewById(R.id.recycler_view_users);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        usersAdapter = new UsersAdapter(null, this, false);
        recyclerViewUsers.setAdapter(usersAdapter);

        usersAdapter.setItemClick(new UsersAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {
                githubUserLists.get(position).setLike(true);
                usersAdapter.notifyDataSetChanged();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserSearchActivity.this, UserLikeActivity.class);
                intent.putExtra(CommonText.INTENTDATA_RESULTUSERS, (ArrayList<GitUser>)githubUserLists);
                startActivityForResult(intent, CommonText.LIKE_REQUEST_CODE);
            }
        });
        recyclerViewUsers.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    fab.show();
                }
                if(!recyclerView.canScrollVertically(1) && isMoreData){
                    cntPage++;
                    userSearchPresenter.search(querySrchText, cntPage);
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 ||dy<0 && fab.isShown())
                    fab.hide();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == CommonText.LIKE_REQUEST_CODE && data!=null) {
            githubUserLists = (ArrayList<GitUser>) data.getSerializableExtra(CommonText.INTENTDATA_RESULTUSERS);
            usersAdapter.setItems(githubUserLists);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userSearchPresenter.detachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_user_search, menu);
        final MenuItem searchActionMenuItem = menu.findItem(R.id.menu_search);
        searchView = (SearchView) searchActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                querySrchText = query;
                cntPage = 1;
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                userSearchPresenter.search(querySrchText, cntPage);
                toolbar.setTitle(querySrchText);
                searchActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        searchActionMenuItem.expandActionView();
        return true;
    }

    @Override
    public void showSearchResults(List<GitUser> githubUserList) {
        recyclerViewUsers.setVisibility(View.VISIBLE);
        textViewErrorMessage.setVisibility(View.GONE);
        if(githubUserList!=null && githubUserList.size()>0){
            isMoreData =true;
        }else{
            isMoreData =false;
        }
        githubUserLists = githubUserList;
        if(cntPage==1){
            usersAdapter.setItems(githubUserLists);
        }else if(cntPage >1){
            usersAdapter.addItems(githubUserLists);
        }
    }

    @Override
    public void showError(String message) {
        textViewErrorMessage.setVisibility(View.VISIBLE);
        recyclerViewUsers.setVisibility(View.GONE);
        textViewErrorMessage.setText(message);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerViewUsers.setVisibility(View.GONE);
        textViewErrorMessage.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        recyclerViewUsers.setVisibility(View.VISIBLE);
        textViewErrorMessage.setVisibility(View.GONE);

    }
}
