package com.limg1.githubsrchuser.data.remote;

import com.limg1.githubsrchuser.data.CommonText;
import com.limg1.githubsrchuser.data.UserRepository;
import com.limg1.githubsrchuser.data.UserRepositoryImpl;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Limg1 on 2017-08-04.
 */

public class Injection {

    private static OkHttpClient okHttpClient;
    private static GitHubUserService userRestService;
    private static Retrofit retrofitInstance;


    public static UserRepository provideUserRepo() {
        return new UserRepositoryImpl(provideGithubUserRestService());
    }

    static GitHubUserService provideGithubUserRestService() {
        if (userRestService == null) {
            userRestService = getRetrofitInstance().create(GitHubUserService.class);
        }
        return userRestService;
    }

    static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            okHttpClient = new OkHttpClient.Builder().addInterceptor(logging).build();
        }

        return okHttpClient;
    }

    static Retrofit getRetrofitInstance() {
        if (retrofitInstance == null) {
            Retrofit.Builder retrofit = new Retrofit.Builder().client(Injection.getOkHttpClient()).baseUrl(CommonText.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
            retrofitInstance = retrofit.build();

        }
        return retrofitInstance;
    }
}
