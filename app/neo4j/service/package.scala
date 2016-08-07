package neo4j

import com.anchor.model.{Goal, Id}

/**
 * Created by mesfinmebrate on 15/07/2016.
 */
package object service {

  implicit class RowWrapper(row: Map[String, Any]) {
    def asGoal: Goal = {
      Goal (
        id = Id(row.get("id").get.asInstanceOf[String]),
        themeId = Id(row.get("themeId").get.asInstanceOf[String]),
        summary = row.get("summary").get.asInstanceOf[String],
        description = row.get("description").get.asInstanceOf[String],
        level = row.get("level").get.asInstanceOf[String].toInt,
        priority = row.get("priority").get.asInstanceOf[Boolean]
      )
    }
  }
}