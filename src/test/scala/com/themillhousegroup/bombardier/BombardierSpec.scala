package com.themillhousegroup.bombardier

import org.specs2.mutable.Specification
import org.specs2.mock.Mockito

import scala.concurrent._
import scala.concurrent.duration._
import com.ning.http.client.Response
import play.api.libs.json._
import com.themillhousegroup.bombardier.test._

class BombardierSpec extends Specification with Mockito {

  val w = WeatherStation(29167, "CENTURY MINE", "YCNY", -18.7569, 138.7056, 127, "94261", "QLD")

  def waitFor[T](ft: Future[T]): T = {
    Await.result(ft, Duration(1, "second"))
  }

  "Bombardier object" should {
    "Be able to look up a JSON endpoint from a WeatherStation instance" in {
      Bombardier.weatherStationEndpoint(w) must beEqualTo("http://reg.bom.gov.au/fwo/IDV60801/IDV60801.94261.json")
    }
  }

  "Bombardier class" should {

    def givenAClientThatReturns(responseBody: String): (String, ExecutionContext) => Future[Response] = {
      def resp(s: String, ec: ExecutionContext): Future[Response] = {
        val mockResponse = mock[Response]
        mockResponse.getResponseBody returns responseBody
        Future(mockResponse)
      }

      resp
    }

    def givenABombardierThatReturns(responseBody: String): Bombardier = {
      val c = givenAClientThatReturns(responseBody)
      new Bombardier(c)
    }

    "Return a None if there is no JSON body" in {
      val b = givenABombardierThatReturns("")

      val fMaybeObs = b.observationFor(w)

      fMaybeObs must not beNull

      waitFor(fMaybeObs) must beNone
    }

    "Explode if the JSON body can't be parsed" in {
      val b = givenABombardierThatReturns("{ this { is [not, valid], json }")

      val fMaybeObs = b.observationFor(w)

      waitFor(fMaybeObs) must throwA[com.fasterxml.jackson.core.JsonParseException]
    }

    "Handle a JSON body and find the most-recent entry when given a WeatherStation" in {
      val b = givenABombardierThatReturns(JsonFixtures.fullJsonString)

      val fMaybeObs = b.observationFor(w)

      val obs = waitFor(fMaybeObs)

      obs must beSome[Observation]

      obs.get.dateTimeUtcMillis must beEqualTo(20151002103000L)
    }

    "Handle a JSON body and find the most-recent entry when given a lat/long" in {
      val b = givenABombardierThatReturns(JsonFixtures.fullJsonString)

      val fMaybeObs = b.observationForLatLong(-34.5D, 145.677D)

      val obs = waitFor(fMaybeObs)

      obs must beSome[Observation]

      obs.get.dateTimeUtcMillis must beEqualTo(20151002103000L)
    }

    "Find the desired observation given a WeatherStation and an exact-match time" in {
      val b = givenABombardierThatReturns(JsonFixtures.fullJsonString)

      val fMaybeObs = b.observationFor(w, Some(20151002093000L))

      val obs = waitFor(fMaybeObs)

      obs must beSome[Observation]

      obs.get.dateTimeUtcMillis must beEqualTo(20151002093000L)
    }

  }
}
