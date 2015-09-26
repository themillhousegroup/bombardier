package com.themillhousegroup.bombardier

object WeatherStation {
  val weatherStationFileName = "IDY2126.dat"
  private val weatherStationSource = scala.io.Source.fromFile(weatherStationFileName)
  private val allStations = weatherStationSource.getLines.drop(1).map(stringToWeatherStation)

  def stringToWeatherStation(s:String):WeatherStation = {
    WeatherStation(1, "name", "code", 1D, 2D, "VIC")
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

