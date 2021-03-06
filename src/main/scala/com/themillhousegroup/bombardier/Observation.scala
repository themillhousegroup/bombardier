package com.themillhousegroup.bombardier

import java.io.File
import play.api.libs.json._

object Observation {
  import ObservationReads._
  def fromJsonString(jsonString: String): Observation = {
    fromJson(Json.parse(jsonString))
  }

  def fromJson(json: JsValue): Observation = {
    json.as[Observation]
  }
}

object ObservationReads {
  import play.api.libs.functional.syntax._

  object LongFromString extends Reads[Long] {
    def reads(json: JsValue): JsResult[Long] = {
      json.validate[String].map(_.toLong)
    }
  }

  implicit val observationReads: Reads[Observation] = (
    (JsPath \ "aifstime_utc").read[Long](LongFromString) and
    (JsPath \ "apparent_t").readNullable[Double] and
    (JsPath \ "cloud").read[String] and
    (JsPath \ "cloud_base_m").readNullable[Int] and
    (JsPath \ "cloud_oktas").readNullable[Int] and
    (JsPath \ "air_temp").readNullable[Double] and
    (JsPath \ "dewpt").readNullable[Double] and
    (JsPath \ "press").readNullable[Double] and
    (JsPath \ "rel_hum").readNullable[Int] and
    (JsPath \ "weather").read[String] and
    (JsPath \ "wind_dir").read[String] and
    (JsPath \ "wind_spd_kmh").read[Int] and
    (JsPath \ "wind_spd_kt").read[Int]
  )(Observation.apply _)
}

/**
 * Represents an observation from a BOM weather station
 */
final case class Observation(
  dateTimeUtcMillis: Long,
  apparentTemperature: Option[Double],
  cloud: String,
  cloudBaseMetres: Option[Int],
  cloudOktas: Option[Int],
  airTemperature: Option[Double],
  dewPoint: Option[Double],
  barometricPressure: Option[Double],
  relativeHumidity: Option[Int],
  weather: String,
  windDirection: String,
  windSpeedKmh: Int,
  windSpeedKnots: Int)

