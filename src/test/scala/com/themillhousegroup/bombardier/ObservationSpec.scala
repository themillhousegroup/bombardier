package com.themillhousegroup.bombardier

import org.specs2.mutable.Specification

class ObservationSpec extends Specification {

  val rawJson = """
{
				"sort_order": 0,
				"wmo": 94884,
				"name": "Benalla",
				"history_product": "IDV60801",
				"local_date_time": "01/09:00am",
				"local_date_time_full": "20151001090000",
				"aifstime_utc": "20150930230000",
				"lat": -36.6,
				"lon": 146.0,
				"apparent_t": 10.5,
				"cloud": "Mostly clear",
				"cloud_base_m": null,
				"cloud_oktas": 1,
				"cloud_type": "-",
				"cloud_type_id": 30,
				"delta_t": 2.4,
				"gust_kmh": null,
				"gust_kt": null,
				"air_temp": 12.0,
				"dewpt": 7.0,
				"press": 1027.5,
				"press_msl": 1027.5,
				"press_qnh": null,
				"press_tend": "-",
				"rain_trace": "0.0",
				"rel_hum": 71,
				"sea_state": "-",
				"swell_dir_worded": "-",
				"swell_height": null,
				"swell_period": null,
				"vis_km": "45",
				"weather": "Fine",
				"wind_dir": "SE",
				"wind_spd_kmh": 4,
				"wind_spd_kt": 2
			}
"""

  "Observation companion object" should {
    "be able to convert a JSON string to an Observation instance" in {
      Observation.fromJson(rawJson) must not beNull
    }

  }
}
