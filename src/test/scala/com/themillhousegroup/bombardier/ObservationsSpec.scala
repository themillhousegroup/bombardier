package com.themillhousegroup.bombardier

import org.specs2.mutable.Specification
import play.api.libs.json._
import com.themillhousegroup.bombardier.test._

class ObservationsSpec extends Specification {

  "Observations" should {

    "be able to parse an empty string to an empty list of Observations" in {
      Observations.fromJsonString("") must haveLength(0)
    }

    "be able to trim a blank string to an empty list of Observations" in {
      Observations.fromJsonString("      ") must haveLength(0)
    }

    "be able to parse a full BOM JSON file" in {
      Observations.fromJsonString(JsonFixtures.fullJsonString) must haveLength(3)
    }

    "be able to parse entries in the JSON file" in {
      val firstOb = Observations.fromJsonString(JsonFixtures.fullJsonString).head

      firstOb.windSpeedKmh must beEqualTo(17)
      firstOb.windDirection must beEqualTo("WSW")
    }
  }
}
