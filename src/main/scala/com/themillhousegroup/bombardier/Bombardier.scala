package com.themillhousegroup.bombardier

import play.api.libs.json.{ JsValue, Json }
import dispatch._, Defaults._
import com.ning.http.client.Response
import scala.concurrent.{ ExecutionContext, Future }
import scala.collection.immutable.NumericRange

object Bombardier extends Bombardier(DispatchQuery.apply) {
  val bomEndpoint = "http://reg.bom.gov.au/fwo/IDV60801/IDV60801"

  def weatherStationEndpoint(station: WeatherStation): String = {
    s"${bomEndpoint}.${station.bomUrlSuffix}.json"
  }
}

object DispatchQuery {
  def apply(endpointUrl: String, ec: ExecutionContext): Future[Response] = {
    val req: Req = url(endpointUrl)
    Http(req)(ec)
  }
}

class Bombardier(fQuery: (String, ExecutionContext) => Future[Response]) {

  /**
   * Finds weather observations for the given latitude/longitude at the given time.
   * The closest weather station (by numerical comparison) for the lat/long will be
   * chosen.
   * If there is no observation for that exact time, the closest-in-time available
   * observation is returned (as per observationsFor(WeatherStation, Option[Long]) )
   */
  def observationForLatLong(latitude: Double, longitude: Double, dateUtcMillis: Option[Long] = None)(implicit ec: ExecutionContext): Future[Option[Observation]] = {
    val closestStation = WeatherStation.byLatLong(latitude, longitude).head
    observationFor(closestStation, dateUtcMillis)(ec)
  }

  /**
   * Finds a weather observation for the given WeatherStation at the given time (if supplied).
   * If there is no observation for that exact time, the closest-in-time available
   * observation is returned
   */
  def observationFor(station: WeatherStation, dateUtcMillis: Option[Long] = None)(implicit ec: ExecutionContext): Future[Option[Observation]] = {
    val endpointUrl = Bombardier.weatherStationEndpoint(station)
    fQuery(endpointUrl, ec).map { response =>
      val obs = Observations.fromJsonString(response.getResponseBody).sortBy(_.dateTimeUtcMillis)

      dateUtcMillis.fold(obs.lastOption) { date =>
        findExactMatchDate(obs, date).orElse(findClosestMatchDate(obs, date))
      }
    }
  }

  private def findExactMatchDate(obs: Seq[Observation], date: Long): Option[Observation] = obs.find(_.dateTimeUtcMillis == date)

  private def findClosestMatchDate(obs: Seq[Observation], date: Long): Option[Observation] = {
    def distanceToDate(o: Observation) = Math.abs(date - o.dateTimeUtcMillis)
    obs.sortWith((a, b) =>
      distanceToDate(a) < distanceToDate(b)
    ).headOption
  }

  /**
   * Finds weather observations for the given latitude/longitude in the given time window (inclusive).
   * If there is no observation in that window, the returned Seq will be empty.
   * The observations will be in ascending time order (i.e. the same order that the range was specified in)
   */
  def observationsForLatLong(latitude: Double, longitude: Double, utcDateRange: NumericRange[Long])(implicit ec: ExecutionContext): Future[Seq[Observation]] = {
    val closestStation = WeatherStation.byLatLong(latitude, longitude).head
    observationsFor(closestStation, utcDateRange)(ec)
  }

  /**
   * Finds weather observations for the given WeatherStation in the given time window (inclusive).
   * If there is no observation in that window, the returned Seq will be empty.
   * The observations will be in ascending time order (i.e. the same order that the range was specified in)
   */
  def observationsFor(station: WeatherStation, utcDateRange: NumericRange[Long])(implicit ec: ExecutionContext): Future[Seq[Observation]] = {

    val endpointUrl = Bombardier.weatherStationEndpoint(station)
    fQuery(endpointUrl, ec).map { response =>
      val obs = Observations.fromJsonString(response.getResponseBody)
      obs.filter(ob => utcDateRange.contains(ob.dateTimeUtcMillis)).sortBy(_.dateTimeUtcMillis)
    }
  }
}
