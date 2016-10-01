package db.jsondb

import com.anchor.json._
import com.anchor.model.Routine
import JsonConfiguration._
import play.api.libs.json.{JsValue, Json}
import scala.io.Source

/**
  * Created by mesfinmebrate on 01/10/2016.
  */
object JsonService {

  def getRoutines: Seq[Routine] = {
    val source: String = Source.fromFile(address).getLines.mkString
    val json: JsValue = Json.parse(source)
    (json \ "routines").as[Seq[Routine]]
  }
}