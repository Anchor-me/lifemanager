package controllers

import com.anchor.model.{SpokeType, Spoke, Goal, Theme}
import neo4j.service._
import neo4j.service.NeoService
import play.api.libs.json.{JsValue, Json}
import com.anchor.json._
import play.api.mvc.{AnyContent, Request, Action, Controller}
import rules.Organiser

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def add(spokeName: String) = Action { request =>
    val item = request.body.asJson.get
    SpokeType.withName(spokeName) match {
      case SpokeType.Goal => Organiser.add(item.as[Goal])
      case SpokeType.Theme => Organiser.add(item.as[Theme])
    }
    Ok
  }

  def connect(id1: String, relationship: String, id2: String) = Action {
    NeoService.connect(id1, relationship, id2)
    Ok
  }

  def get(id: String, spokeName: String) = Action {
    NeoService.find(id) match {
      case None => NotFound
      case Some(item) => {
        val answer: JsValue = {
          SpokeType.withName(spokeName) match {
            case SpokeType.Goal => Json.toJson(item.asGoal)
            case SpokeType.Theme => Json.toJson(item.asTheme)
          }
        }
        Ok(answer + "\n")
      }
    }
  }

  def delete(id: String) = Action {
    NeoService.delete(id)
    Ok
  }
}
