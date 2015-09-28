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
      val expected = WeatherStation(1006, "WYNDHAM AIRPORT", "YWYM", -15.51D, 128.1503D, "WA")
      WeatherStation.allStations.head must beEqualTo(expected)
    }

    "be able to look up a weather station by its ID" in {
      val expected = WeatherStation(9021, "PERTH AIRPORT", "YPPH", -31.9275, 115.9764, "WA")
      WeatherStation.byId.get(9021) must beSome(expected)
    }

  }
}
