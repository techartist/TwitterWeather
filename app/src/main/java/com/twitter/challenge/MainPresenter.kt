package com.twitter.challenge

import android.util.Log
import com.google.gson.Gson
import com.squareup.okhttp.HttpUrl
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.twitter.challenge.model.TwitterWeather
import com.twitter.challenge.util.Constants
import com.twitter.challenge.util.StandardDeviation
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit


class MainPresenter : MainVP.PresenterOps {

    constructor(mView : MainVP.RequiredViewOps) {
        this.mView = mView
    }

    fun calculateStandardDeviation(array : List<Double>) {
        val arrayDouble = array.toDoubleArray()
        val standardDeviation = StandardDeviation.calculateStandardDeviation(arrayDouble)
        mView?.updateStandardDeviation(standardDeviation)
    }

    // Layer View reference
    private var compositeDisposable : CompositeDisposable? = null
    private var TAG = "MainPresenter"
    private var mView: MainVP.RequiredViewOps? = null


    override fun onDestroy() {
        compositeDisposable?.dispose()
        mView = null
    }

    override fun getCurrentTemperature() {
        Single.fromCallable{getWeather(0)}
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<String> {
                    override fun onSuccess(string: String) {
                        val gson = Gson()
                        val weather = gson.fromJson(string,TwitterWeather::class.java)
                        mView?.updateCurrentWeather(weather)

                    }

                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable?.add(d)
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, e.message)
                        e.printStackTrace()

                    }
                })

    }

    override fun get5DaysOfWeatherAndCalculateStandardDeviation() {

        Observable.fromIterable(listOf(1, 2, 3, 4, 5))
                .flatMap { t ->
                    Observable.fromCallable {
                        val string = getWeather(t)
                        val gson = Gson()
                        val weather = gson.fromJson(string, TwitterWeather::class.java)
                        weather.weather.temp
                    }.subscribeOn(Schedulers.io())
                }
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ temperatures ->
                    calculateStandardDeviation(temperatures)
                    mView?.dismissProgressDialog()
                }, {
                    e->Log.e(TAG,e.localizedMessage)
                })


    }



    @Throws(IOException::class)
    private fun getWeather(future : Int): String {
        var twitterURL = Constants.API_URL
        if (future > 0) {
            twitterURL += "future_$future.json"
        } else {
            twitterURL += "current.json"
        }
            val urlBuilder = HttpUrl.parse(twitterURL).newBuilder()
            val url = urlBuilder.build().toString()

            val request = Request.Builder()
                    .url(url)
                    .build()
            val client = OkHttpClient()
            client.setConnectTimeout(60, TimeUnit.SECONDS); // connect timeout
            client.setReadTimeout(60, TimeUnit.SECONDS);
            client.setWriteTimeout(60, TimeUnit.SECONDS)
            val response = client.newCall(request).execute()
            val body = response.body()
            if (response.code() == HttpURLConnection.HTTP_OK) {
                val string = body.string();
                response.body().close()
                return string
            } else {

                throw IOException("Bad Request: Server Response" + response.code().toString() + " " + response.message())
            }


    }


}