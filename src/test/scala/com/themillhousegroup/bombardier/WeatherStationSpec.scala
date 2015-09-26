package com.themillhousegroup.bombardier

import org.specs2.mutable.Specification

class WeatherStationSpec extends Specification {

  "WeatherStation companion object" should {
    "find the BOM data file" in {
      WeatherStation.weatherStationSource must not beNull
    }

    "read the full list of stations from the BOM data file" in {
      WeatherStation.allStations must haveLength(744)
    }

    "correctly interpret records from the BOM data file" in {
      val expected = WeatherStation(1006, "WYNDHAM AIR  PORT", "YWYM", -15.51D, 128.1503D, "WA")
      WeatherStation.allStations.head must beEqualTo(expected)
    }
  }
}
