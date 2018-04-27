package com.twitter.challenge.model

import android.os.Parcel
import android.os.Parcelable

data class Coordinates(
        var lat: Double,
        var long: Double
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readDouble(),
            source.readDouble()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeDouble(lat)
        writeDouble(long)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Coordinates> = object : Parcelable.Creator<Coordinates> {
            override fun createFromParcel(source: Parcel): Coordinates = Coordinates(source)
            override fun newArray(size: Int): Array<Coordinates?> = arrayOfNulls(size)
        }
    }
}
