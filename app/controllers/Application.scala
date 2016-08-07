package controllers

import neo4j.service.NeoService
import play.api.libs.json.Json
import com.anchor.json._
import play.api.mvc.{Action, Controller}

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def get(goalId: String) = Action {
    NeoService.find(goalId) match {
      case None => NotFound
      case Some(goal) => Ok(Json.toJson(goal))
    }
  }
}
