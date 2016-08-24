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
  }

  def add(concreteBlock: ConcreteBlock): Unit = {
    NeoService.add(concreteBlock)
  }

  def add(financialTracking: FinancialTracking): Unit = {
    NeoService.add(financialTracking)
    NeoService.connect(financialTracking.id.id, "is recorded during", financialTracking.yearId.id)
  }

  def add(goal: Goal): Unit = {
    NeoService.add(goal)
    goal.backlogItems.foreach(itemId => NeoService.connect(goal.id.id, "based on", itemId.id))
    NeoService.connect(goal.id.id, "has theme", goal.themeId.id)
  }

  def add(hobby: Hobby): Unit = {
    NeoService.add(hobby)
    NeoService.connect(hobby.id.id, "originates from", hobby.goalId.id)
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
    NeoService.connect(saturday.id.id, "occurs in", saturday.weekId.id)
    saturday.threads.foreach(thread => NeoService.connect(saturday.id.id, "contains", thread.id))
    saturday.portion.foreach(portion => NeoService.connect(saturday.id.id, "contains", portion.id))
    saturday.passiveHobby.foreach(hobby => NeoService.connect(saturday.id.id, "contains", hobby.id))
    saturday.financialTracking.foreach(tracking => NeoService.connect(saturday.id.id, "contains", tracking.id))
  }

  def add(sunday: Sunday): Unit = {
    NeoService.add(sunday)
    NeoService.connect(sunday.id.id, "occurs in", sunday.weekId.id)
    sunday.threads.foreach(thread => NeoService.connect(sunday.id.id, "contains", thread.id))
    sunday.activeHobby.foreach(hobby => NeoService.connect(sunday.id.id, "contains", hobby.id))
    sunday.financialTracking.foreach(tracking => NeoService.connect(sunday.id.id, "contains", tracking.id))
  }

  def add(theme: Theme): Unit = {
    NeoService.add(theme)
    NeoService.connect(theme.id.id, "from year", theme.yearId.id)
  }

  def add(thread: Thread): Unit = {
    NeoService.add(thread)
    NeoService.connect(thread.id.id, "originates from", thread.goalId.id)
  }

//  def add(timeTable: TimeTable): Unit = {
//    NeoService.add(timeTable)
//    NeoService.connect(timeTable.dayId.id)
//  }

  def add(toDo: ToDo): Unit = {
    NeoService.add(toDo)
    NeoService.connect(toDo.id.id, "belongs to", toDo.portionId.id)
  }

  def add(weave: Weave): Unit = {
    NeoService.add(weave)
    NeoService.connect(weave.id.id, "originates from", weave.goalId.id)
  }

  def add(week: Week): Unit = {
    NeoService.add(week)
    NeoService.connect(week.id.id, "occurs in", week.yearId.id)
  }

  def add(weekDay: WeekDay): Unit = {
    NeoService.add(weekDay)
    NeoService.connect(weekDay.id.id, "occurs in", weekDay.weekId.id)
  }

  def add(year: Year): Unit = {
    NeoService.add(year)
  }
}