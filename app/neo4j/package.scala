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

    def asConcreteBlock: ConcreteBlock = {
      resultRow match {
        case CypherRow(row: Map[String, Any]) => ConcreteBlock(
          start = row.get("start").asInstanceOf[Long],
          finish = row.get("finish").asInstanceOf[Long],
          task = row.get("task").map(item => Id(item.asInstanceOf[String]))
        )
      }
    }

    def asFinancialTracking: FinancialTracking = {
      resultRow match {
        case CypherRow(row: Map[String, Any]) => FinancialTracking(
          id = Id(row.get("id").get.asInstanceOf[String]),
          currentAmount = row.get("currentAmount").get.asInstanceOf[Double],
          goalAmount = row.get("goalAmount").get.asInstanceOf[Double],
          paidIn = row.get("paidIn").get.asInstanceOf[Double],
          paidOut = row.get("paidOut").get.asInstanceOf[Double],
          progress = FinancialProgressType.withName(row.get("progress").get.asInstanceOf[String])
        )
      }
    }

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

    def asHobby: Hobby = {
      resultRow match {
        case CypherRow(row: Map[String, Any]) => Hobby(
          id = Id(row.get("id").get.asInstanceOf[String]),
          summary = row.get("summary").get.asInstanceOf[String],
          description = row.get("description").get.asInstanceOf[String],
          typeOf = HobbyType.withName(row.get("typeOf").get.asInstanceOf[String]),
          status = StatusType.withName(row.get("status").get.asInstanceOf[String])
        )
      }
    }

    def asLaserDonut: LaserDonut = {
      resultRow match {
        case CypherRow(row: Map[String, Any]) => LaserDonut(
          id = Id(row.get("id").get.asInstanceOf[String]),
          summary = row.get("summary").get.asInstanceOf[String],
          description = row.get("description").get.asInstanceOf[String],
          goalId = Id(row.get("goalId").get.asInstanceOf[String]),
          status = StatusType.withName(row.get("status").get.asInstanceOf[String]),
          milestone = row.get("milestone").get.asInstanceOf[String],
          order = row.get("order").get.asInstanceOf[Int],
          typeOf = DonutType.withName(row.get("typeOf").get.asInstanceOf[String])
        )
      }
    }

    /*LaserDonut (
       id: Id,
       summary: String,
       description: String,
       goalId: Id,
       status: StatusType.Value,
       milestone: String,
       order: Int,
       typeOf: DonutType.Value
    )*/

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