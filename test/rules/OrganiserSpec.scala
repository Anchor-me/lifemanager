package rules

import com.anchor.model._
import java.time.DayOfWeek._
import org.scalatest.{FlatSpec, Matchers}
import Organiser._

/**
  * Created by mesfinmebrate on 01/10/2016.
  */

class OrganiserSpec extends FlatSpec with Matchers {

  val Monday = DateTime(2016, 10, 3, 0, 0, 0)
  val Saturday = DateTime(2016, 10, 8, 0, 0, 0)
  val Today = DateTime(2016, 10, 1, 0, 0, 0)

  val TodayTimetable = Timetable(
    date = Some(Today),
    scheduledItems = Nil,
    typeOf = TimetableType.OneOff
  )
  val MondayTimetable = Timetable(
    date = None,
    scheduledItems = Nil,
    typeOf = TimetableType.Monday
  )
  val WeekdayTimetable = Timetable(
    date = None,
    scheduledItems = Nil,
    typeOf = TimetableType.Weekdays
  )
  val WeekendTimetable = Timetable(
    date = None,
    scheduledItems = Nil,
    typeOf = TimetableType.Weekends
  )
  val DailyTimetable = Timetable(
    date = None,
    scheduledItems = Nil,
    typeOf = TimetableType.Daily
  )

  val routine1 = Routine(
    id = Id("ROUTINE1"),
    name = "Lean month",
    timetables = Seq(TodayTimetable, MondayTimetable, WeekdayTimetable, WeekendTimetable, DailyTimetable),
    isCurrent = true
  )

  val routine2 = Routine(
    id = Id("ROUTINE2"),
    name = "Normalcy",
    timetables = Nil,
    isCurrent = false
  )

  val routines = Seq(routine2, routine1)

  "getTimetable" should "work" in {
    getTimetable(routines, Monday) should be (Some(MondayTimetable))
    getTimetable(routines, Saturday) should be (Some(WeekendTimetable))
    getTimetable(routines, Today) should be (Some(TodayTimetable))
  }

  "getCurrentTimetable" should "work" in {
    getCurrentRoutine(routines) should be (Some(routine1))
  }

  "getAppropriateTimetable" should "work" in {
    getAppropriateTimetable(routine1, Monday) should be (Some(MondayTimetable))
    getAppropriateTimetable(routine1, Saturday) should be (Some(WeekendTimetable))
    getAppropriateTimetable(routine1, Today) should be (Some(TodayTimetable))
  }

  "labelImportance" should "work" in {
    labelImportance(TodayTimetable, Monday) should be ((1000, TodayTimetable))
    labelImportance(TodayTimetable, Saturday) should be ((1000, TodayTimetable))
    labelImportance(TodayTimetable, Today) should be ((0, TodayTimetable))

    labelImportance(MondayTimetable, Monday) should be ((1, MondayTimetable))
    labelImportance(MondayTimetable, Saturday) should be ((1000, MondayTimetable))
    labelImportance(MondayTimetable, Today) should be ((1000, MondayTimetable))

    labelImportance(WeekdayTimetable, Monday) should be ((2, WeekdayTimetable))
    labelImportance(WeekdayTimetable, Saturday) should be ((1000, WeekdayTimetable))
    labelImportance(WeekdayTimetable, Today) should be ((1000, WeekdayTimetable))

    labelImportance(WeekendTimetable, Monday) should be ((1000, WeekendTimetable))
    labelImportance(WeekendTimetable, Saturday) should be ((2, WeekendTimetable))
    labelImportance(WeekendTimetable, Today) should be ((2, WeekendTimetable))

    labelImportance(DailyTimetable, Monday) should be ((2, DailyTimetable))
    labelImportance(DailyTimetable, Saturday) should be ((2, DailyTimetable))
    labelImportance(DailyTimetable, Today) should be ((2, DailyTimetable))
  }

  "isWeekday" should "work" in {
    isWeekday(Monday) shouldBe (true)
    isWeekday(Saturday) shouldBe (false)
    isWeekday(Today) shouldBe (false)
  }

  "isWeekend" should "work" in {
    isWeekend(Monday) shouldBe (false)
    isWeekend(Saturday) shouldBe (true)
    isWeekend(Today) shouldBe (true)
  }

  "getDayOfWeek" should "work" in {
    getDayOfWeek(Monday) should be (1)
    getDayOfWeek(Saturday) should be (6)
    getDayOfWeek(Today) should be (6)
  }

  "fallsOnWeekday" should "work" in {
    fallsOnWeekday(TodayTimetable) shouldBe (false)
    fallsOnWeekday(MondayTimetable) shouldBe (true)
    fallsOnWeekday(WeekdayTimetable) shouldBe (true)
    fallsOnWeekday(WeekendTimetable) shouldBe (false)
    fallsOnWeekday(DailyTimetable) shouldBe (true)
  }

  "fallsOnWeekend" should "work" in {
    fallsOnWeekend(TodayTimetable) shouldBe (false) // One-off timetable should come with a date.
    fallsOnWeekend(MondayTimetable) shouldBe (false)
    fallsOnWeekend(WeekdayTimetable) shouldBe (false)
    fallsOnWeekend(WeekendTimetable) shouldBe (true)
    fallsOnWeekend(DailyTimetable) shouldBe (true)
  }
}