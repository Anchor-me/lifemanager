package rules

import com.anchor.model._
import com.anchor.model.TimetableType._
import db.DatabaseLayer
import java.time._

/**
  * Created by mesfinmebrate on 25/09/2016.
  */
object Organiser {

  val WeekdaysSeq = Seq(Monday, Tuesday, Wednesday, Thursday, Friday, Weekdays, Daily)
  val WeekendsSeq = Seq(Saturday, Sunday, Weekends, Daily)

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
    else if (timetable.typeOf.id == getDayOfWeek(dateTime)) {
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

  def getDayOfWeek(dateTime: DateTime): Int = {
    new org.joda.time.DateTime (
      dateTime.year,
      dateTime.month,
      dateTime.day,
      dateTime.hours,
      dateTime.minutes,
      dateTime.seconds
    ).dayOfWeek.get
  }

  def isWeekday(dateTime: DateTime): Boolean = {
    WeekdaysSeq.map(_.id).contains(getDayOfWeek(dateTime))
  }

  def isWeekend(dateTime: DateTime): Boolean = {
    WeekendsSeq.map(_.id).contains(getDayOfWeek(dateTime))
  }

  def fallsOnWeekday(timetable: Timetable): Boolean = {
    WeekdaysSeq.map(_.id).contains(timetable.typeOf.id)
  }

  def fallsOnWeekend(timetable: Timetable): Boolean = {
    WeekendsSeq.map(_.id).contains(timetable.typeOf.id)
  }
}