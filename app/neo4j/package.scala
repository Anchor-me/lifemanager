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
          goalId = Id(row.get("id").get.asInstanceOf[String]),
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

    def asPortion: Portion = {
      resultRow match {
        case CypherRow(row: Map[String, Any]) => Portion(
          id = Id(row.get("id").get.asInstanceOf[String]),
          laserDonutId = Id(row.get("laserDonutId").get.asInstanceOf[String]),
          summary = row.get("summary").get.asInstanceOf[String],
          order = row.get("order").asInstanceOf[Int],
          status = StatusType.withName(row.get("status").get.asInstanceOf[String])
        )
      }
    }

    def asReceipt: Receipt = {
      resultRow match {
        case CypherRow(row: Map[String, Any]) => Receipt(
          purchasedItem = row.get("purchasedItem").get.asInstanceOf[String],
          expenditure = row.get("expenditure").get.asInstanceOf[Double],
          nameOfEstablishment = row.get("nameOfEstablishment").get.asInstanceOf[String]
        )
      }
    }

    def asSaturday: Saturday = {
      resultRow match {
        case CypherRow(row: Map[String, Any]) => Saturday(
          id = Id(row.get("id").get.asInstanceOf[String]),
          weekId = Id(row.get("weekId").get.asInstanceOf[String]),
          date = row.get("date").get.asInstanceOf[Long],
          threads = row.get("threads").asInstanceOf[Seq[String]].map(id => Id(id)),
          portion = row.get("portion").map(item => Id(item.asInstanceOf[String])),
          passiveHobby = row.get("passiveHobby").map(item => Id(item.asInstanceOf[String])),
          financialTracking = row.get("financialTracking").map(item => Id(item.asInstanceOf[String]))
        )
      }
    }

    def asSunday: Sunday = {
      resultRow match {
        case CypherRow(row: Map[String, Any]) => Sunday(
          id = Id(row.get("id").get.asInstanceOf[String]),
          weekId = Id(row.get("weekId").get.asInstanceOf[String]),
          date = row.get("date").get.asInstanceOf[Long],
          threads = row.get("threads").asInstanceOf[Seq[String]].map(id => Id(id)),
          activeHobby = row.get("activeHobby").map(item => Id(item.asInstanceOf[String])),
          financialTracking = row.get("financialTracking").map(item => Id(item.asInstanceOf[String]))
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

    def asThread: Thread = {
      resultRow match {
        case CypherRow(row: Map[String, Any]) => Thread(
          id = Id(row.get("id").get.asInstanceOf[String]),
          summary = row.get("summary").get.asInstanceOf[String],
          description = row.get("description").get.asInstanceOf[String],
          goalId = Id(row.get("goalId").get.asInstanceOf[String]),
          status = StatusType.withName(row.get("status").get.asInstanceOf[String])
        )
      }
    }

    def asToDo: ToDo = {
      resultRow match {
        case CypherRow(row: Map[String, Any]) => ToDo(
          id = Id(row.get("id").get.asInstanceOf[String]),
          portionId = Id(row.get("portionId").get.asInstanceOf[String]),
          description = row.get("id").get.asInstanceOf[String],
          order = row.get("order").get.asInstanceOf[Int],
          status = StatusType.withName(row.get("status").get.asInstanceOf[String])
        )
      }
    }

    def asWeave: Weave = {
      resultRow match {
        case CypherRow(row: Map[String, Any]) => Weave(
          id = Id(row.get("id").get.asInstanceOf[String]),
          summary = row.get("summary").get.asInstanceOf[String],
          description = row.get("description").get.asInstanceOf[String],
          goalId = Id(row.get("goalId").get.asInstanceOf[String]),
          status = StatusType.withName(row.get("status").get.asInstanceOf[String]),
          typeOf = WeaveType.withName(row.get("typeOf").get.asInstanceOf[String])
        )
      }
    }

    def asWeek: Week = {
      resultRow match {
        case CypherRow(row: Map[String, Any]) => Week(
          id = Id(row.get("id").get.asInstanceOf[String]),
          startDate = row.get("startDate").get.asInstanceOf[Long],
          finishDate = row.get("finishDate").get.asInstanceOf[Long],
          weave = row.get("weave").map(item => Id(item.asInstanceOf[String])),
          laserDonut = row.get("laserDonut").map(item => Id(item.asInstanceOf[String]))
        )
      }
    }

    def asWeekDay: WeekDay = {
      resultRow match {
        case CypherRow(row: Map[String, Any]) => WeekDay(
          id = Id(row.get("id").get.asInstanceOf[String]),
          weekId = Id(row.get("weekId").get.asInstanceOf[String]),
          date = row.get("date").get.asInstanceOf[Long],
          threads = row.get("threads").asInstanceOf[Seq[String]].map(id => Id(id)),
          weave = row.get("weave").map(item => Id(item.asInstanceOf[String])),
          portion = row.get("portion").map(item => Id(item.asInstanceOf[String])),
          financialTracking = row.get("financialTracking").map(item => Id(item.asInstanceOf[String]))
        )
      }
    }

    def asYear: Year = {
      resultRow match {
        case CypherRow(row: Map[String, Any]) => Year(
          id = Id(row.get("id").get.asInstanceOf[String]),
          startDate = row.get("startDate").get.asInstanceOf[Long],
          finishDate = row.get("finishDate").get.asInstanceOf[Long]
        )
      }
    }
  }
}