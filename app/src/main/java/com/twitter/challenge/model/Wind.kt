package com.twitter.challenge.model

import android.os.Parcel
import android.os.Parcelable

data class Wind(
        var speed: Double,
        var deg: Int
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readDouble(),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeDouble(speed)
        writeInt(deg)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Wind> = object : Parcelable.Creator<Wind> {
            override fun createFromParcel(source: Parcel): Wind = Wind(source)
            override fun newArray(size: Int): Array<Wind?> = arrayOfNulls(size)
        }
    }
}