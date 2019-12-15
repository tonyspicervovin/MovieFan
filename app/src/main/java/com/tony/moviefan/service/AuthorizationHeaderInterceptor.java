package com.tony.moviefan.service;

import com.tony.moviefan.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizationHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request requestWithHeaders = request.newBuilder()
                .addHeader("Authorization", BuildConfig.MOVIE_API_KEY)
                .build();
        return chain.proceed(requestWithHeaders);
    }
    // creating a header parameter that we I use for each call with the authorization token
}
