package com.httam.thapcamtv.api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.httam.thapcamtv.Application;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static String BASE_URL;
    private static String SECOND_BASE_URL;
    private static final String GITHUB_API_BASE_URL = "https://api.github.com/";

    private static Retrofit githubRetrofit = null;
    private static GitHubApiService githubApiService = null;
    private static FirebaseFirestore firebaseFirestore;

    public static Retrofit getClient(boolean useSecondBaseUrl) {
        if (SECOND_BASE_URL == null || BASE_URL == null) {
            SharedPreferences sharedPreferences = Application.context.getSharedPreferences("thapcamtv", Context.MODE_PRIVATE);
            BASE_URL = sharedPreferences.getString("baseUrl", "https://q.thapcamn.xyz/");
            SECOND_BASE_URL = sharedPreferences.getString("secondaryUrl", "https://api.vebo.xyz/");
        }
        String url = useSecondBaseUrl ? SECOND_BASE_URL : BASE_URL;
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static GitHubApiService getGitHubApiService() {
        if (githubApiService == null) {
            if (githubRetrofit == null) {
                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                githubRetrofit = new Retrofit.Builder()
                        .baseUrl(GITHUB_API_BASE_URL)
                        .client(httpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            githubApiService = githubRetrofit.create(GitHubApiService.class);
        }
        return githubApiService;
    }

    @SuppressLint("CommitPrefEdits")
    public static void initRemoteConfig() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("thapcamtv").document("configs").get()
                .addOnSuccessListener(documentSnapshot -> {
                    String baseUrl = documentSnapshot.getString("baseUrl");
                    SharedPreferences sharedPreferences = Application.context.getSharedPreferences("thapcamtv", Context.MODE_PRIVATE);
                    if (baseUrl != null && !baseUrl.isEmpty()) {
                        BASE_URL = baseUrl;
                        sharedPreferences.edit().putString("baseUrl", baseUrl).apply();
                    }
                    String secondaryUrl = documentSnapshot.getString("secondaryUrl");
                    if (secondaryUrl != null && !secondaryUrl.isEmpty()) {
                        SECOND_BASE_URL = secondaryUrl;
                        sharedPreferences.edit().putString("secondaryUrl", secondaryUrl).apply();
                    }
                })
                .addOnFailureListener(e -> Log.w("Failed to get document", "Error get document", e));

    }

    public static void fetchConfig() {
        firebaseFirestore.collection("thapcamtv").document("configs").get()
                .addOnSuccessListener(documentSnapshot -> {
                    String baseUrl = documentSnapshot.getString("baseUrl");
                    if (baseUrl != null && !baseUrl.isEmpty()) {
                        BASE_URL = baseUrl;
                    }
                    String secondaryUrl = documentSnapshot.getString("secondaryUrl");
                    if (secondaryUrl != null && !secondaryUrl.isEmpty()) {
                        SECOND_BASE_URL = secondaryUrl;
                    }
                })
                .addOnFailureListener(e -> Log.w("Failed to get document", "Error adding document", e));
    }
}