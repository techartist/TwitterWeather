package com.twitter.challenge

import com.twitter.challenge.model.TwitterWeather

public interface MainVP {

    /**
     * View mandatory methods. Available to Presenter
     * Presenter -> View
     */
    interface RequiredViewOps {
        fun dismissProgressDialog()
        fun updateCurrentWeather(weather : TwitterWeather)
        fun updateStandardDeviation(standardDeviation : Double)

    }

    /**
     * Operations offered from Presenter to View
     * View -> Presenter
     */
    interface PresenterOps {
        fun onDestroy()
        fun getCurrentTemperature()
        fun get5DaysOfWeatherAndCalculateStandardDeviation()
    }
}


