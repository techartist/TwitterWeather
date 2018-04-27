package com.twitter.challenge.model

import android.os.Parcel
import android.os.Parcelable


data class Weather(
        var temp: Double,
        var pressure: Long,
        var humidltiy: Int


) : Parcelable {
    constructor(source: Parcel) : this(
            source.readDouble(),
            source.readLong(),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeDouble(temp)
        writeLong(pressure)
        writeInt(humidltiy)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Weather> = object : Parcelable.Creator<Weather> {
            override fun createFromParcel(source: Parcel): Weather = Weather(source)
            override fun newArray(size: Int): Array<Weather?> = arrayOfNulls(size)
        }
    }
}