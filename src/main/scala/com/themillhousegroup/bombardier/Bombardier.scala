package com.themillhousegroup.bombardier

import play.api.libs.json.{ JsValue, Json }

object Bombardier {

  /**
   * Finds weather observations for the given latitude/longitude at the given time.
   * The closest weather station (by numerical comparison) for the lat/long will be
   * chosen.
   * If there is no observation for that exact time, the closest-in-time available
   * observation(s) are returned (as per observationsFor(WeatherStation, Long) )
   */
  def observationsFor(latitude: Double, longitude: Double, dateUtcMillis: Long): Seq[Observation] = {
    val closestStation = WeatherStation.byLatLong(latitude, longitude).head
    observationsFor(closestStation, dateUtcMillis)
  }

  /**
   * Finds weather observations for the given WeatherStation at the given time.
   * If there is no observation for that exact time, the closest-in-time available
   * observation(s) are returned
   */
  def observationsFor(station: WeatherStation, dateUtcMillis: Long): Seq[Observation] = {
    null
  }
}
