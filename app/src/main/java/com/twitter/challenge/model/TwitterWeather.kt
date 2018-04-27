package com.twitter.challenge.model

import android.os.Parcel
import android.os.Parcelable

data class TwitterWeather(
        var coord: Coordinates,
        var weather: Weather,
        var wind: Wind,
        var rain: Rain,
        var clouds: Clouds,
        var name: String
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readParcelable<Coordinates>(Coordinates::class.java.classLoader),
            source.readParcelable<Weather>(Weather::class.java.classLoader),
            source.readParcelable<Wind>(Wind::class.java.classLoader),
            source.readParcelable<Rain>(Rain::class.java.classLoader),
            source.readParcelable<Clouds>(Clouds::class.java.classLoader),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(coord, 0)
        writeParcelable(weather, 0)
        writeParcelable(wind, 0)
        writeParcelable(rain, 0)
        writeParcelable(clouds, 0)
        writeString(name)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TwitterWeather> = object : Parcelable.Creator<TwitterWeather> {
            override fun createFromParcel(source: Parcel): TwitterWeather = TwitterWeather(source)
            override fun newArray(size: Int): Array<TwitterWeather?> = arrayOfNulls(size)
        }
    }
}