package neo4j

import com.anchor.model.{Theme, Goal, Id}
import org.anormcypher.{CypherRow, CypherResultRow}

/**
 * Created by mesfinmebrate on 15/07/2016.
 */
package object service {

  implicit class RowWrapper(resultRow: Option[CypherResultRow]) {
    def asGoal: Option[Goal] = {
      resultRow.map {
        case CypherRow(row: Map[String, Any]) => Goal(
          id = Id(row.get("id").get.asInstanceOf[String]),
          themeId = Id(row.get("themeId").get.asInstanceOf[String]),
          summary = row.get("summary").get.asInstanceOf[String],
          description = row.get("description").get.asInstanceOf[String],
          level = row.get("level").get.toString.toInt,
          priority = row.get("priority").get.asInstanceOf[Boolean]
        )
      }
    }

    def asTheme: Option[Theme] = {
      resultRow.map {
        case CypherRow(row: Map[String, Any]) => Theme(
          id = Id(row.get("id").get.asInstanceOf[String]),
          yearId = Id(row.get("yearId").get.asInstanceOf[String]),
          name = row.get("name").get.asInstanceOf[String]
        )
      }
    }
  }
}