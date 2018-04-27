package com.twitter.challenge.util


object StandardDeviation {

    fun calculateStandardDeviation(arr: DoubleArray) : Double{
        var sum = 0.0
        var mean = 0.0
        var i = 0
        mean = sum / arr.size
        sum = 0.0
        val temp = DoubleArray(arr.size)
        while (i < arr.size)
        //calculate standard deviation
        {
            temp[i] = Math.pow(arr[i] - mean, 2.0)
            sum += temp[i]
            i++
        }
        mean = sum / arr.size - 1
        return Math.sqrt(mean)
    }
}