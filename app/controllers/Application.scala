package controllers

import com.anchor.model._
import neo4j._
import play.api.libs.json.{JsValue, Json}
import com.anchor.json._
import db.DatabaseLayer
import neo4j.NeoService
import play.api.mvc.{Action, Controller}

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def add(spokeName: String) = Action { request =>
    val item = request.body.asJson.get
    SpokeType.withName(spokeName) match {
      case SpokeType.BacklogItem => DatabaseLayer.add(item.as[BacklogItem])
      case SpokeType.Epoch => DatabaseLayer.add(item.as[Epoch])
      case SpokeType.FinancialTracking => DatabaseLayer.add(item.as[FinancialTracking])
      case SpokeType.Goal => DatabaseLayer.add(item.as[Goal])
      case SpokeType.Hobby => DatabaseLayer.add(item.as[Hobby])
      case SpokeType.LaserDonut => DatabaseLayer.add(item.as[LaserDonut])
      case SpokeType.Portion => DatabaseLayer.add(item.as[Portion])
      case SpokeType.Receipt => DatabaseLayer.add(item.as[Receipt])
      case SpokeType.Theme => DatabaseLayer.add(item.as[Theme])
      case SpokeType.Thread => DatabaseLayer.add(item.as[Thread])
      case SpokeType.ToDo => DatabaseLayer.add(item.as[ToDo])
      case SpokeType.Weave => DatabaseLayer.add(item.as[Weave])
      case SpokeType.Year => DatabaseLayer.add(item.as[Year])
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
            case SpokeType.BacklogItem => Json.toJson(item.asBacklogItem)
            case SpokeType.Epoch => Json.toJson(item.asEpoch)
            case SpokeType.FinancialTracking => Json.toJson(item.asFinancialTracking)
            case SpokeType.Goal => Json.toJson(item.asGoal)
            case SpokeType.Hobby => Json.toJson(item.asHobby)
            case SpokeType.LaserDonut => Json.toJson(item.asLaserDonut)
            case SpokeType.Portion => Json.toJson(item.asPortion)
            case SpokeType.Receipt => Json.toJson(item.asReceipt)
            case SpokeType.Theme => Json.toJson(item.asTheme)
            case SpokeType.Thread => Json.toJson(item.asThread)
            case SpokeType.Timetable => Json.toJson(item.asTimetable)
            case SpokeType.ToDo => Json.toJson(item.asToDo)
            case SpokeType.Weave => Json.toJson(item.asWeave)
            case SpokeType.Year => Json.toJson(item.asYear)
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
