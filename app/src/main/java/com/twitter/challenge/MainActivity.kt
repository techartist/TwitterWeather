package com.twitter.challenge

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.twitter.challenge.model.TwitterWeather
import com.twitter.challenge.util.Constants
import com.twitter.challenge.util.TemperatureConverter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainVP.RequiredViewOps {

    override fun updateCurrentWeather(twitterWeather: TwitterWeather) {
        this.twitterWeather = twitterWeather
        temperature.text = getString(R.string.temperature, twitterWeather.weather.temp, TemperatureConverter.celsiusToFahrenheit(twitterWeather.weather.temp))
        windSpeed.text = getString(R.string.wind,twitterWeather.wind.speed)
        if (twitterWeather.clouds.cloudiness > 50) {
            cloud.visibility = View.VISIBLE
        }
    }

    override fun updateStandardDeviation(standardDeviation: Double) {
        this.standardDeviation = standardDeviation
        standard_deviation.text = getString(R.string.deviation,standardDeviation)
        llStandardDeviation.visibility = View.VISIBLE

    }

    lateinit var mPresenter: MainVP.PresenterOps
    lateinit var progressDialog: ProgressDialog
    lateinit var twitterWeather : TwitterWeather
    private var standardDeviation: Double = 0.0

    private fun setUpProgressDialog() {
        progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage(getString(R.string.progress_message))
        progressDialog.setMax(100)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(java.lang.Boolean.FALSE)
    }

    private fun showProgressDialog() {
        progressDialog.show()
    }

    override fun dismissProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpProgressDialog()

        mPresenter = MainPresenter(this)

        if (savedInstanceState != null) {
            twitterWeather = savedInstanceState.getParcelable(Constants.BUNDLE_TWIITER_WEATHER)
            updateCurrentWeather(twitterWeather)
            standardDeviation = savedInstanceState.getDouble(Constants.BUNDLE_STANDARD_DEVIATION, 0.0)
            if (standardDeviation > 0.0) {
                llStandardDeviation.visibility = View.VISIBLE
                updateStandardDeviation(standardDeviation)
            }

        } else {
            mPresenter.getCurrentTemperature()
        }
        btnGetStandardDeviation.setOnClickListener{
            showProgressDialog()
            mPresenter.get5DaysOfWeatherAndCalculateStandardDeviation()}


    }

    override fun onSaveInstanceState(outState: Bundle?) {
        if (twitterWeather != null) {
            outState?.putParcelable(Constants.BUNDLE_TWIITER_WEATHER, twitterWeather)
        }
        if (standardDeviation > 0.0) {
            outState?.putDouble(Constants.BUNDLE_STANDARD_DEVIATION, standardDeviation)
        }
        super.onSaveInstanceState(outState)
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
        if (progressDialog != null && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

}
