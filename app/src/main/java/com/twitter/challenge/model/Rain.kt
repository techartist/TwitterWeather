package com.twitter.challenge.model

import android.os.Parcel
import android.os.Parcelable

data class Rain(
        var threeHour: Int
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(threeHour)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Rain> = object : Parcelable.Creator<Rain> {
            override fun createFromParcel(source: Parcel): Rain = Rain(source)
            override fun newArray(size: Int): Array<Rain?> = arrayOfNulls(size)
        }
    }
}