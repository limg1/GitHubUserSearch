package com.limg1.githubsrchuser.data;

import com.limg1.githubsrchuser.data.remote.model.GitUser;

import java.util.List;

import rx.Observable;

/**
 * Created by Limg1 on 2017-08-04.
 */

public interface UserRepository {
    Observable<List<GitUser>> searchUsers(String searchTerm, int page);
}
