package com.themillhousegroup.bombardier

import java.io.File

object WeatherStation {
  val weatherStationFileName = "IDY02126.dat"
  private val weatherStationStream = getClass.getResourceAsStream("/" + weatherStationFileName)
  private[bombardier] val weatherStationSource = scala.io.Source.fromInputStream(weatherStationStream)
  private[bombardier] val allStations = weatherStationSource.getLines.drop(1).map(stringToWeatherStation).toSeq

  private def stringToWeatherStation(s: String): WeatherStation = {
    def tidy(qs: String) = qs.replace(""""""", "").trim

    val parts = s.split(',')
    WeatherStation(
      tidy(parts(0)).toInt,
      tidy(parts(1)),
      tidy(parts(2)),
      parts(3).toDouble,
      parts(4).toDouble,
      tidy(parts(7)))
  }
}

/**
 * Represents a BOM weather station that can
 * supply weather observation data.
 */
case class WeatherStation(
  id: Int,
  name: String,
  code: String,
  latitude: Double,
  longitude: Double,
  state: String)

