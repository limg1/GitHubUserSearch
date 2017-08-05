package com.limg1.githubsrchuser.ui.search;


import com.limg1.githubsrchuser.data.remote.model.GitUser;
import com.limg1.githubsrchuser.ui.base.MvpPresenter;
import com.limg1.githubsrchuser.ui.base.MvpView;

import java.util.List;



interface UserSearchContract {

    interface View extends MvpView {
        void showSearchResults(List<GitUser> githubUserList);

        void showError(String message);

        void showLoading();

        void hideLoading();
    }

    interface Presenter extends MvpPresenter<View> {
        void search(String term, int page);
    }
}
