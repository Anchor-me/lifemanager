package rules

import com.anchor.model._
import neo4j.NeoService

/**
 * Created by mesfinmebrate on 15/07/2016.
 */
object Organiser {

  def add(backlogItem: BacklogItem): Unit = {
    NeoService.add(backlogItem)
    NeoService.connect(backlogItem.id.id, "is recorded during", backlogItem.yearId.id)
  }

  def add(bufferBlock: BufferBlock): Unit = {
    NeoService.add(bufferBlock)
    bufferBlock.firstTask.foreach(task => NeoService.connect(bufferBlock.id.id, "firstly runs", task.id))
    bufferBlock.secondTask.foreach(task => NeoService.connect(bufferBlock.id.id, "secondly runs", task.id))
  }

  def add(concreteBlock: ConcreteBlock): Unit = {
    NeoService.add(concreteBlock)
    concreteBlock.task.foreach(task => NeoService.connect(concreteBlock.id.id, "only runs", task.id))
  }

  def add(financialTracking: FinancialTracking): Unit = {
    NeoService.add(financialTracking)
    NeoService.connect(financialTracking.id.id, "is recorded during", financialTracking.dayId.id)
  }

  def add(goal: Goal): Unit = {
    NeoService.add(goal)
    NeoService.connect(goal.id.id, "has theme", goal.themeId.id)
    goal.backlogItems.foreach(item => NeoService.connect(goal.id.id, "based on", item.id))
  }

  def add(hobby: Hobby): Unit = {
    NeoService.add(hobby)
    hobby.goalId.foreach(goal => NeoService.connect(hobby.id.id, "originates from", goal.id))
  }

  def add(laserDonut: LaserDonut): Unit = {
    NeoService.add(laserDonut)
    NeoService.connect(laserDonut.id.id, "originates from", laserDonut.goalId.id)
  }

  def add(portion: Portion): Unit = {
    NeoService.add(portion)
    NeoService.connect(portion.id.id, "belongs to", portion.laserDonutId.id)
  }

  def add(receipt: Receipt): Unit = {
    NeoService.add(receipt)
    NeoService.connect(receipt.id.id, "is tracked in", receipt.trackingId.id)
  }

  def add(saturday: Saturday): Unit = {
    NeoService.add(saturday)
    NeoService.connect(saturday.id.id, "occurs during", saturday.weekId.id)
    saturday.threads.foreach(thread => NeoService.connect(saturday.id.id, "contains", thread.id))
    saturday.portion.foreach(portion => NeoService.connect(saturday.id.id, "contains", portion.id))
    saturday.passiveHobby.foreach(hobby => NeoService.connect(saturday.id.id, "contains", hobby.id))
  }

  def add(sunday: Sunday): Unit = {
    NeoService.add(sunday)
    NeoService.connect(sunday.id.id, "occurs in", sunday.weekId.id)
    sunday.threads.foreach(thread => NeoService.connect(sunday.id.id, "contains", thread.id))
    sunday.activeHobby.foreach(hobby => NeoService.connect(sunday.id.id, "contains", hobby.id))
  }

  def add(theme: Theme): Unit = {
    NeoService.add(theme)
    NeoService.connect(theme.id.id, "from year", theme.yearId.id)
  }

  def add(thread: Thread): Unit = {
    NeoService.add(thread)
    thread.goalId.foreach(goal => NeoService.connect(thread.id.id, "originates from", goal.id))
  }

  def add(timetable: Timetable): Unit = {
    NeoService.add(timetable)
    NeoService.connect(timetable.id.id, "is used during", timetable.dayId.id)
    timetable.scheduledItems.foreach(item => NeoService.connect(timetable.id.id, "runs", item.id))
  }

  def add(toDo: ToDo): Unit = {
    NeoService.add(toDo)
    NeoService.connect(toDo.id.id, "belongs to", toDo.portionId.id)
  }

  def add(weave: Weave): Unit = {
    NeoService.add(weave)
    weave.goalId.foreach(goal => NeoService.connect(weave.id.id, "originates from", goal.id))
  }

  def add(week: Week): Unit = {
    NeoService.add(week)
    NeoService.connect(week.id.id, "occurs in", week.yearId.id)
    week.threads.foreach(thread => NeoService.connect(week.id.id, "contains", thread.id))
    week.weaves.foreach(weave => NeoService.connect(week.id.id, "contains", weave.id))
    week.laserDonut.foreach(laserDonut => NeoService.connect(week.id.id, "contains", laserDonut.id))
  }

  def add(weekDay: WeekDay): Unit = {
    NeoService.add(weekDay)
    NeoService.connect(weekDay.id.id, "occurs during", weekDay.weekId.id)
    weekDay.threads.foreach(thread => NeoService.connect(weekDay.id.id, "contains", thread.id))
    weekDay.weaves.foreach(weave => NeoService.connect(weekDay.id.id, "contains", weave.id))
    weekDay.portion.foreach(portion => NeoService.connect(weekDay.id.id, "contains", portion.id))
  }

  def add(year: Year): Unit = {
    NeoService.add(year)
    NeoService.connect(year.id.id, "occurs during", year.epochId.id)
    year.threads.foreach(thread => NeoService.connect(year.id.id, "contains", thread.id))
  }
}