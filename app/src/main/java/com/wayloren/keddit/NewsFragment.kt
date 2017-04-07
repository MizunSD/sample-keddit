package com.wayloren.keddit

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wayloren.keddit.R.id.news_list
import com.wayloren.keddit.adapter.NewsAdapter
import com.wayloren.keddit.commons.InfiniteScrollListener
import com.wayloren.keddit.commons.RxBaseFragment
import com.wayloren.keddit.commons.inflate
import com.wayloren.keddit.features.news.NewsManager
import com.wayloren.keddit.model.RedditNews

import kotlinx.android.synthetic.main.news_fragment.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject


class NewsFragment : RxBaseFragment() {

    companion object {
        private val KEY_REDDIT_NEWS = "redditNews"
    }

    @Inject
    lateinit var newsManager : NewsManager

    private var redditNews: RedditNews? = null

    private val newsList: RecyclerView by lazy {
        news_list.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener({requestNews()}, linearLayout))
        }
        news_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KedditApp.newsComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.news_fragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initAdapter()

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_REDDIT_NEWS)) {
            redditNews = savedInstanceState.get(KEY_REDDIT_NEWS) as RedditNews
            (newsList.adapter as NewsAdapter).clearAndAddNews(redditNews!!.news)
        } else {
            requestNews()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val news = (newsList.adapter as NewsAdapter).getNews()
        if (redditNews != null && news.size > 0) {
            outState.putParcelable(KEY_REDDIT_NEWS, redditNews?.copy(news = news))
        }
    }

    override fun onPause() {
        super.onPause()
    }

    private fun requestNews() {
        val subscription = newsManager.getNews(redditNews?.after ?: "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                {
                    retrievedNews ->
                    redditNews = retrievedNews
                    (newsList.adapter as NewsAdapter).addNews(retrievedNews.news)
                },
                {
                    e -> Snackbar.make(news_list, e.message ?: "", Snackbar.LENGTH_LONG).show()
                }
        )
        subscriptions.add(subscription)
    }

    private fun initAdapter() {
        if (newsList.adapter == null)
            newsList.adapter = NewsAdapter()
    }
}