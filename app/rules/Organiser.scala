package rules

import com.anchor.model._
import db.DatabaseLayer
import java.sql.Date
import java.time.DayOfWeek._
import java.time._

/**
  * Created by mesfinmebrate on 25/09/2016.
  */
object Organiser {

  val Weekdays = Seq(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY)
  val Weekends = Seq(SATURDAY, SUNDAY)

  def getTimetable(dateTime: DateTime): Option[Timetable] = {
    getCurrentRoutine.map(getAppropriateTimetable(_, dateTime)).getOrElse(None)
  }

  def getAppropriateTimetable(routine: Routine, dateTime: DateTime): Option[Timetable] = {
    routine.timetables.map { timetable =>
      labelImportance(timetable, dateTime)
    }.sortBy(_._1).map(_._2).headOption
  }

  def labelImportance(timetable: Timetable, dateTime: DateTime): (Int, Timetable) = {
    if (timetable.date.nonEmpty && timetable.date.get == dateTime) {
      (0, timetable)
    }
    else if (timetable.typeOf == getDayOfWeek(dateTime)) {
      (1, timetable)
    }
    else if (
      isWeekday(dateTime) && fallsOnWeekday(timetable) ||
      isWeekend(dateTime) && fallsOnWeekend(timetable)
    ) {
      (2, timetable)
    }
    else {
      (1000, timetable)
    }
  }

  def getCurrentRoutine: Option[Routine] = {
    DatabaseLayer.getRoutines.filter(_.isCurrent).headOption
  }

  def getDayOfWeek(dateTime: DateTime): DayOfWeek = {
    val localDate: LocalDate = Instant.ofEpochMilli(dateTime.toMillis).atZone(ZoneId.systemDefault).toLocalDate
    localDate.getDayOfWeek
  }

  def isWeekday(dateTime: DateTime): Boolean = {
    Weekdays.map(_.getValue).contains(getDayOfWeek(dateTime).getValue)
  }

  def isWeekend(dateTime: DateTime): Boolean = {
    Weekends.map(_.getValue).contains(getDayOfWeek(dateTime).getValue)
  }

  def fallsOnWeekday(timetable: Timetable): Boolean = {
    Weekdays.map(_.getValue).contains(timetable.typeOf.id)
  }

  def fallsOnWeekend(timetable: Timetable): Boolean = {
    Weekends.map(_.getValue).contains(timetable.typeOf.id)
  }
}