package com.themillhousegroup.bombardier

import java.io.File

object WeatherStation {
  val weatherStationFileName = "IDY02126.dat"
  private val weatherStationStream = getClass.getResourceAsStream("/" + weatherStationFileName)
  private[bombardier] val weatherStationSource = scala.io.Source.fromInputStream(weatherStationStream)
  private[bombardier] val allStations = weatherStationSource.getLines.drop(1).map(stringToWeatherStation).toSeq

  lazy val byId: Map[Int, WeatherStation] = allStations.map(ws => ws.id -> ws).toMap

  /**
   * Be aware that there ARE duplicate-named weather stations - e.g. "RICHMOND AP" -
   * which exists in both NSW and QLD. For safety, use byNameAndState!
   */
  lazy val byName: Map[String, WeatherStation] = allStations.map(ws => ws.name -> ws).toMap

  lazy val byNameAndState: Map[(String, String), WeatherStation] = allStations.map(ws => (ws.name -> ws.state) -> ws).toMap

  //	private lazy val sortedByLatitude:Seq[WeatherStation] = allStations.sortBy(_.latitude)

  /**
   * Be aware that there are many weather stations that report the code "AAAA" so
   * this map deliberately has those overloaded cases removed - use the ID instead
   */
  lazy val byCode: Map[String, WeatherStation] = allStations.map(ws => ws.code -> ws).toMap - "AAAA"

  /** Returns the `numberToReturn` weather stations closest (numerically) to the target lat/long */
  def byLatLong(latitude: Double, longitude: Double): Seq[WeatherStation] = {
    allStations.sortBy { ws =>
      Math.abs(ws.latitude - latitude) + Math.abs(ws.longitude - longitude)
    }
  }

  private def stringToWeatherStation(s: String): WeatherStation = {
    def tidy(qs: String) = qs.replace(""""""", "").trim

    val parts = s.split(',')

    WeatherStation(
      tidy(parts(0)).toInt,
      tidy(parts(1)),
      tidy(parts(2)),
      parts(3).toDouble,
      parts(4).toDouble,
      tidy(parts(5)).toInt,
      tidy(parts(6)),
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
  heightMetres: Int,
  bomUrlSuffix: String,
  state: String)

