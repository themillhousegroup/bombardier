package com.themillhousegroup.bombardier

import org.specs2.mutable.Specification
import org.specs2.mock.Mockito

import scala.concurrent.{ ExecutionContext, Future }
import com.ning.http.client.Response
import play.api.libs.json._

class BombardierSpec extends Specification with Mockito {

  "Bombardier object" should {
    "Be able to look up a JSON endpoint from a WeatherStation instance" in {
      val w = WeatherStation(29167, "CENTURY MINE", "YCNY", -18.7569, 138.7056, 127, "94261", "QLD")
      Bombardier.weatherStationEndpoint(w) must beEqualTo("http://reg.bom.gov.au/fwo/IDV60801/IDV60801.94261.json")
    }
  }

  "Bombardier class" should {

    def givenAClientThatReturns(responseBody: String): (String, ExecutionContext) => Future[Response] = {
      def resp(s: String, ec: ExecutionContext): Future[Response] = {
        val mockResponse = mock[Response]
        mockResponse.getResponseBody returns responseBody
        Future.successful(mockResponse)
      }

      resp
    }

  }
}
