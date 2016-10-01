package db

import com.anchor.model._
import db.jsondb.JsonService
import neo4j.NeoService

/**
 * Created by mesfinmebrate on 15/07/2016.
 */
object DatabaseLayer {

  def add(backlogItem: BacklogItem): Unit = {
    NeoService.add(backlogItem)
    NeoService.connect(backlogItem.id.id, "is recorded during", backlogItem.yearId.id)
  }

  def add(epoch: Epoch): Unit = {
    NeoService.add(epoch)
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

  def add(theme: Theme): Unit = {
    NeoService.add(theme)
    NeoService.connect(theme.id.id, "from year", theme.yearId.id)
  }

  def add(thread: Thread): Unit = {
    NeoService.add(thread)
    thread.goalId.foreach(goal => NeoService.connect(thread.id.id, "originates from", goal.id))
  }

  def add(toDo: ToDo): Unit = {
    NeoService.add(toDo)
    NeoService.connect(toDo.id.id, "belongs to", toDo.portionId.id)
  }

  def add(weave: Weave): Unit = {
    NeoService.add(weave)
    weave.goalId.foreach(goal => NeoService.connect(weave.id.id, "originates from", goal.id))
  }

  def add(year: Year): Unit = {
    NeoService.add(year)
    NeoService.connect(year.id.id, "occurs during", year.epochId.id)
  }

  def getRoutines: Seq[Routine] = {
    JsonService.getRoutines
  }
}