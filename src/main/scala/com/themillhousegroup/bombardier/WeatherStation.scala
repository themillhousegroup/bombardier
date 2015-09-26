package com.themillhousegroup.bombardier

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

