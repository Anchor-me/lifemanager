package controllers

import com.anchor.model._
import neo4j._
import play.api.libs.json.{JsValue, Json}
import com.anchor.json._
import neo4j.NeoService
import play.api.mvc.{Action, Controller}
import rules.Organiser

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def add(spokeName: String) = Action { request =>
    val item = request.body.asJson.get
    SpokeType.withName(spokeName) match {
      case SpokeType.BacklogItem => Organiser.add(item.as[BacklogItem])
      case SpokeType.BufferBlock => Organiser.add(item.as[BufferBlock])
      case SpokeType.ConcreteBlock => Organiser.add(item.as[ConcreteBlock])
      case SpokeType.FinancialTracking => Organiser.add(item.as[FinancialTracking])
      case SpokeType.Goal => Organiser.add(item.as[Goal])
      case SpokeType.Hobby => Organiser.add(item.as[Hobby])
      case SpokeType.LaserDonut => Organiser.add(item.as[LaserDonut])
      case SpokeType.Portion => Organiser.add(item.as[Portion])
      case SpokeType.Receipt => Organiser.add(item.as[Receipt])
      case SpokeType.Saturday => Organiser.add(item.as[Saturday])
      case SpokeType.Sunday => Organiser.add(item.as[Sunday])
      case SpokeType.Theme => Organiser.add(item.as[Theme])
      case SpokeType.Thread => Organiser.add(item.as[Thread])
      case SpokeType.ToDo => Organiser.add(item.as[ToDo])
      case SpokeType.Weave => Organiser.add(item.as[Weave])
      case SpokeType.Week => Organiser.add(item.as[Week])
      case SpokeType.WeekDay => Organiser.add(item.as[WeekDay])
      case SpokeType.Year => Organiser.add(item.as[Year])
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
            case SpokeType.BufferBlock => Json.toJson(item.asBufferBlock)
            case SpokeType.ConcreteBlock => Json.toJson(item.asConcreteBlock)
            case SpokeType.FinancialTracking => Json.toJson(item.asFinancialTracking)
            case SpokeType.Goal => Json.toJson(item.asGoal)
            case SpokeType.Hobby => Json.toJson(item.asHobby)
            case SpokeType.LaserDonut => Json.toJson(item.asLaserDonut)
            case SpokeType.Portion => Json.toJson(item.asPortion)
            case SpokeType.Receipt => Json.toJson(item.asReceipt)
            case SpokeType.Saturday => Json.toJson(item.asSaturday)
            case SpokeType.Sunday => Json.toJson(item.asSunday)
            case SpokeType.Theme => Json.toJson(item.asTheme)
            case SpokeType.Thread => Json.toJson(item.asThread)
            case SpokeType.ToDo => Json.toJson(item.asToDo)
            case SpokeType.Weave => Json.toJson(item.asWeave)
            case SpokeType.Week => Json.toJson(item.asWeek)
            case SpokeType.WeekDay => Json.toJson(item.asWeekDay)
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
