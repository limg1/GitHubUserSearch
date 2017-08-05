package com.limg1.githubsrchuser.data.remote;

import com.limg1.githubsrchuser.data.remote.model.GitUser;
import com.limg1.githubsrchuser.data.remote.model.GitUsersList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Limg1 on 2017-08-04.
 */

public interface GitHubUserService {
    @GET("/search/users")
    Observable<GitUsersList> searchGithubUsers(@Query("q") String searchTerm, @Query("page") int page);

//    @GET("/users/{username}")
//    Observable<GitUser> getUser(@Path("username") String username);

}
