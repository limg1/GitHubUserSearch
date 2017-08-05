package com.limg1.githubsrchuser.data;


import com.limg1.githubsrchuser.data.remote.GitHubUserService;
import com.limg1.githubsrchuser.data.remote.model.GitUser;

import java.io.IOException;
import java.util.List;

import rx.Observable;

public class UserRepositoryImpl implements UserRepository {

    private GitHubUserService githubUserRestService;

    public UserRepositoryImpl(GitHubUserService githubUserRestService) {
        this.githubUserRestService = githubUserRestService;
    }

    @Override
    public Observable<List<GitUser>> searchUsers(final String searchTerm, final int page) {
        return Observable.defer(() -> githubUserRestService.searchGithubUsers(searchTerm, page).concatMap(
                usersList -> Observable.from(usersList.getItems())
                        .toList()))
                .retryWhen(observable -> observable.flatMap(o -> {
                    if (o instanceof IOException) {
                        return Observable.just(null);
                    }
                    return Observable.error(o);
                }));

    }

}
