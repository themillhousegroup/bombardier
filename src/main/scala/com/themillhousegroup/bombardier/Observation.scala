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
    (JsPath \ "apparent_t").read[Double] and
    (JsPath \ "cloud").read[String] and
    (JsPath \ "cloud_base_m").readNullable[Int] and
    (JsPath \ "cloud_oktas").readNullable[Int] and
    (JsPath \ "air_temp").read[Double] and
    (JsPath \ "dewpt").read[Double] and
    (JsPath \ "press").readNullable[Double] and
    (JsPath \ "rel_hum").read[Int] and
    (JsPath \ "weather").read[String] and
    (JsPath \ "wind_dir").read[String] and
    (JsPath \ "wind_spd_kmh").read[Int] and
    (JsPath \ "wind_spd_kt").read[Int]
  )(Observation.apply _)
}

/**
 * Represents an observation from a BOM weather station
 */
case class Observation(
  dateTimeUtcMillis: Long,
  apparentTemperature: Double,
  cloud: String,
  cloudBaseMetres: Option[Int],
  cloudOktas: Option[Int],
  airTemperature: Double,
  dewPoint: Double,
  barometricPressure: Option[Double],
  relativeHumidity: Int,
  weather: String,
  windDirection: String,
  windSpeedKmh: Int,
  windSpeedKnots: Int)

