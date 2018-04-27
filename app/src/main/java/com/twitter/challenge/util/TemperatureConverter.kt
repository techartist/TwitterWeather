package com.twitter.challenge.util

object TemperatureConverter {
    /**
     * Converts temperature in Celsius to temperature in Fahrenheit.
     *
     * @param temperatureInCelsius Temperature in Celsius to convert.
     * @return Temperature in Fahrenheit.
     */
    fun celsiusToFahrenheit(temperatureInCelsius: Double): Double {
        return temperatureInCelsius * 1.8f + 32
    }
}
