package controllers

import com.anchor.model.{Spoke, Goal, Theme}
import neo4j.service.NeoService
import play.api.libs.json.Json
import com.anchor.json._
import play.api.mvc.{AnyContent, Request, Action, Controller}
import rules.Organiser

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def addGoal = Action { request =>
    add(request.body.asJson.get.as[Goal])
    Ok
  }

  def addTheme = Action { request =>
    add(request.body.asJson.get.as[Theme])
    Ok
  }

  def add(item: Spoke): Unit = {
    item match {
      case goal:Goal => Organiser.add(goal)
      case theme: Theme => Organiser.add(theme)
    }
  }

  def connect(id1: String, relationship: String, id2: String) = Action {
    NeoService.connect(id1, relationship, id2)
    Ok
  }

  def getGoal(goalId: String) = Action {
    NeoService.findGoal(goalId) match {
      case None => NotFound
      case Some(goal) => Ok(Json.toJson(goal) + "\n")
    }
  }

  def getTheme(themeId: String) = Action {
    NeoService.findTheme(themeId) match {
      case None => NotFound
      case Some(theme) => Ok(Json.toJson(theme) + "\n")
    }
  }

  def delete(id: String) = Action {
    NeoService.delete(id)
    Ok
  }
}
