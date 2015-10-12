package com.themillhousegroup.bombardier

import play.api.libs.json.{ JsValue, Json }

object Observations {
  import ObservationReads._
  def fromJsonString(jsonString: String): Seq[Observation] = {
    if (jsonString.trim.isEmpty) {
      Nil
    } else {
      fromJson(Json.parse(jsonString))
    }
  }

  def fromJson(json: JsValue): Seq[Observation] = {
    (json \ "observations" \ "data").as[Seq[Observation]]
  }
}
