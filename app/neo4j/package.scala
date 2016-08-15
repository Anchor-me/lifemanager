import com.anchor.model._
import org.anormcypher.{CypherResultRow, CypherRow}

/**
 * Created by mesfinmebrate on 15/07/2016.
 */
package object neo4j {

  implicit class RowWrapper(resultRow: CypherResultRow) {
    def asBacklogItem: BacklogItem = {
      resultRow match {
        case CypherRow(row: Map[String, Any]) => BacklogItem(
          id = Id(row.get("id").get.asInstanceOf[String]),
          summary = row.get("summary").get.asInstanceOf[String],
          description = row.get("description").get.asInstanceOf[String],
          typeOf = BacklogItemType.withName(row.get("typeOf").get.asInstanceOf[String])
        )
      }
    }

    def asBufferBlock: BufferBlock = {
      resultRow match {
        case CypherRow(row: Map[String, Any]) => BufferBlock(
          start = row.get("start").get.asInstanceOf[Long],
          finish = row.get("finish").get.asInstanceOf[Long],
          firstTask = row.get("firstTask").map(item => Id(item.asInstanceOf[String])),
          secondTask = row.get("secondTask").map(item => Id(item.asInstanceOf[String]))
        )
      }
    }

    /*Goal (
       id: Id,
       themeId: Id,
       summary: String,
       description: String,
       level: Int,
       priority: Boolean,
       status: GoalStatusType.Value,
       graduation: GraduationType.Value
     )*/

    def asGoal: Goal = {
      resultRow match {
        case CypherRow(row: Map[String, Any]) => Goal(
          id = Id(row.get("id").get.asInstanceOf[String]),
          themeId = Id(row.get("themeId").get.asInstanceOf[String]),
          summary = row.get("summary").get.asInstanceOf[String],
          description = row.get("description").get.asInstanceOf[String],
          level = row.get("level").get.toString.toInt,
          priority = row.get("priority").get.asInstanceOf[Boolean],
          status = GoalStatusType.withName(row.get("status").get.asInstanceOf[String]),
          graduation = GraduationType.withName(row.get("graduation").get.asInstanceOf[String])
        )
      }
    }

    def asTheme: Theme = {
      resultRow match {
        case CypherRow(row: Map[String, Any]) => Theme(
          id = Id(row.get("id").get.asInstanceOf[String]),
          yearId = Id(row.get("yearId").get.asInstanceOf[String]),
          name = row.get("name").get.asInstanceOf[String]
        )
      }
    }
  }
}