package com.example.newsly

import ModelClass.Article
import ModelClass.DataClass

import NewsAdapter.NewslyAdapter
import Retrofit.NewsInterface
import Retrofit.RetrofitHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {
    lateinit var adapter: NewslyAdapter
    var articles = mutableListOf<Article>()
    var pageNum = 1
    var totalResults = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getNews()

        adapter= NewslyAdapter(this@MainActivity,articles)
        newsItem.adapter = adapter
        newsItem.layoutManager = LinearLayoutManager(this@MainActivity)


    }

    private fun getNews() {
        val news = RetrofitHelper.newsInstance.getHeadline("in", pageNum)
        //Enqueuing the pages of news
        news.enqueue(object : retrofit2.Callback<DataClass> {

            override fun onFailure(call: Call<DataClass>, t: Throwable) {
                Log.d("News", "Error occurred", t)
            }

            override fun onResponse(call: Call<DataClass>, response: Response<DataClass>) {
                val news = response.body()
               // Toast.makeText(this@MainActivity,"App Started",Toast.LENGTH_LONG).show()
                if (news != null) {
                    Log.d("News", news.toString())
                    totalResults = news.totalResults // here , we get the total no. of pages there in the api
                    articles.addAll(news.articles)   // adding articles to above mutable list of  Article
                    adapter.notifyDataSetChanged()  // telling the adapter that item is changed

                    Log.d("result","totalResult - ${totalResults}-\n total article - ${articles.size}")
                    // articlesLength = news.articles.size
                    Toast.makeText(this@MainActivity,"App Started reached",Toast.LENGTH_LONG).show()
                    if (totalResults > articles.size ){


                        pageNum++
                        getNews()
                    }


                }

            }


        })
    }
//    private fun getNews() {
//
//        val news = RetrofitHelper.newsInstance.getHeadline("in",pageNum)
//        news.enqueue(object : retrofit2.Callback<News>{
//            override fun onResponse(call: Call<News>, response: Response<News>) {
//
//                val news = response.body()
//                if (news!= null){
//                    totalResults = news.totalResults
//                    articles.addAll(news.articles)
//                    Log.d("result","totalResult - ${totalResults}-\n total article - ${articles.size}")
//                    if (totalResults > articles.size){
//                        pageNum ++
//                        getNews()
//                    }
//                }
//
//            }
//
//            override fun onFailure(call: Call<News>, t: Throwable) {
//                Log.d("NewsError","Some Error Has Occurred")
//                Toast.makeText(this@MainActivity,"Error has occurred",Toast.LENGTH_SHORT).show()
//            }
//
//        }
//
//
//        )
//
//    }
}