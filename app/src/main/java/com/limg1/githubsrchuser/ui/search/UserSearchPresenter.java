package com.limg1.githubsrchuser.ui.search;


import com.limg1.githubsrchuser.data.UserRepository;
import com.limg1.githubsrchuser.data.remote.model.GitUser;
import com.limg1.githubsrchuser.ui.base.BasePresenter;

import java.util.List;

import rx.Scheduler;
import rx.Subscriber;

class UserSearchPresenter extends BasePresenter<UserSearchContract.View> implements UserSearchContract.Presenter {
    private final Scheduler mainScheduler, ioScheduler;
    private UserRepository userRepository;

    UserSearchPresenter(UserRepository userRepository, Scheduler ioScheduler, Scheduler mainScheduler) {
        this.userRepository = userRepository;
        this.ioScheduler = ioScheduler;
        this.mainScheduler = mainScheduler;
    }

    @Override
    public void search(String term, int page) {
        checkViewAttached();
        getView().showLoading();
        addSubscription(userRepository.searchUsers(term, page).subscribeOn(ioScheduler).observeOn(mainScheduler)
                .subscribe(new Subscriber<List<GitUser>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoading();
                        getView().showError(
                                e.getMessage()); //TODO You probably don't want this error to show to users - Might want to show a friendlier message :)
                    }

                    @Override
                    public void onNext(List<GitUser> users) {
                        getView().hideLoading();
                        getView().showSearchResults(users);
                    }
                }));
    }
}
