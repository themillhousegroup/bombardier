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
      val expected = WeatherStation(1006, "WYNDHAM AIRPORT", "YWYM", -15.51D, 128.1503D, 4, "95214", "WA")
      WeatherStation.allStations.head must beEqualTo(expected)
    }

    "be able to look up a weather station by its ID" in {
      val expected = WeatherStation(9021, "PERTH AIRPORT", "YPPH", -31.9275, 115.9764, 15, "94610", "WA")
      WeatherStation.byId.get(9021) must beSome(expected)
    }

    "be able to look up a weather station by its name" in {
      val expected = WeatherStation(29167, "CENTURY MINE", "YCNY", -18.7569, 138.7056, 127, "94261", "QLD")
      WeatherStation.byName("CENTURY MINE") must beEqualTo(expected)
    }

    "be able to look up a weather station by its name and state" in {
      val expectedNsw = WeatherStation(67105, "RICHMOND AP", "YSRI", -33.6004, 150.7761, 19, "95753", "NSW")
      WeatherStation.byNameAndState("RICHMOND AP" -> "NSW") must beEqualTo(expectedNsw)

      val expectedQld = WeatherStation(30161, "RICHMOND AP", "YRMD", -20.7017, 143.1167, 206, "94341", "QLD")
      WeatherStation.byNameAndState("RICHMOND AP" -> "QLD") must beEqualTo(expectedQld)
    }

    "be able to look up a weather station by its code" in {
      val expected = WeatherStation(33208, "ROSSLYN BAY NTC", "ROSS", -23.161, 150.7901, 0, "95298", "QLD")
      WeatherStation.byCode("ROSS") must beEqualTo(expected)
    }

    "be unable to look up a weather station by its code if that code is the overloaded 'AAAA'" in {
      WeatherStation.byCode.get("AAAA") must beNone
    }

    "be able to find the closest WeatherStation for an exact lat-long match" in {
      val expected = WeatherStation(76031, "MILDURA", "YMIA", -34.2358, 142.0867, 50, "94693", "VIC")
      WeatherStation.byLatLong(-34.2358, 142.0867).head must beEqualTo(expected)
    }

    "be able to find the closest WeatherStation for a very close lat-long difference" in {
      val expected = WeatherStation(86351, "BUNDOORA", "BUND", -37.7163, 145.0453, 83, "95873", "VIC")
      WeatherStation.byLatLong(-37.7540674, 145.0012255).head must beEqualTo(expected)
    }

    "Correctly order the closest WeatherStations for a lat-long" in {
      val expectedFirst = WeatherStation(86351, "BUNDOORA", "BUND", -37.7163, 145.0453, 83, "95873", "VIC")
      val expectedSecond = WeatherStation(86338, "MELBOURNE (OLYM", "MBOP", -37.8255, 144.9816, 8, "95936", "VIC")
      val expectedThird = WeatherStation(86068, "VIEWBANK AWS", "VBK", -37.7408, 145.0972, 66, "95874", "VIC")
      WeatherStation.byLatLong(-37.7540674, 145.0012255).take(3) must beEqualTo(Seq(expectedFirst, expectedSecond, expectedThird))
    }

    "Correctly calculate the most distant WeatherStations from a lat-long" in {
      val expectedFirst = WeatherStation(66212, "SYDNEY OLYMPIC", "HOM", -33.8338, 151.0718, 4, "95765", "NSW")
      val expectedLast = WeatherStation(300001, "MAWSON", "MAWS", -67.6017, 62.8753, 10, "89564", "TAS")
      val stations = WeatherStation.byLatLong(-33.8338, 151.0718)
      stations.head must beEqualTo(expectedFirst)
      stations.last must beEqualTo(expectedLast)
    }
  }
}
