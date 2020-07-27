package com.example.androidpromoteroad.retrofithttp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * author: WentaoKing
 * created on: 2020/7/25
 * description:
 */
interface GithubService {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String?): Call<List<Repo>>
}