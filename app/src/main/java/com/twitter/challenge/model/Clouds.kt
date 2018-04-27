package com.twitter.challenge.model

import android.os.Parcel
import android.os.Parcelable

data class Clouds(
        var cloudiness: Int
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(cloudiness)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Clouds> = object : Parcelable.Creator<Clouds> {
            override fun createFromParcel(source: Parcel): Clouds = Clouds(source)
            override fun newArray(size: Int): Array<Clouds?> = arrayOfNulls(size)
        }
    }
}