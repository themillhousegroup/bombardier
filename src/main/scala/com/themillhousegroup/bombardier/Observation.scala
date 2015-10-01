package com.themillhousegroup.bombardier

import java.io.File

/**
 * Represents an observation from a BOM weather station
 */
case class Observation(
  dateTimeUtcMillis: Long,
  apparentTemperature: Double,
  cloud: String,
  cloudBaseMetres: Int,
  cloudOktas: Int,
  airTemperature: Double,
  dewPoint: Double,
  barometricPressure: Double,
  relativeHumidity: Int,
  weather: String,
  windDirection: String,
  windSpeedKmh: Int,
  windSpeedKnots: Int)

object Observation {
  def fromJson(jsonString: String): Observation = {
    null
  }
}
