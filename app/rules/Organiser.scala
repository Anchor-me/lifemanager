package rules

import com.anchor.model.{Goal, Theme}
import neo4j.NeoService

/**
 * Created by mesfinmebrate on 15/07/2016.
 */
object Organiser {

  def add(goal: Goal): Unit = {
    NeoService.add(goal)
    NeoService.connect(goal.id.id, "has theme", goal.themeId.id)
  }

  def add(theme: Theme): Unit = {
    NeoService.add(theme)
  }
}