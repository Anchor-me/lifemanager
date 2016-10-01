package db.neo4j

import com.anchor.model._
import org.anormcypher.{CypherResultRow, CypherRow}

/**
  * Created by mesfinmebrate on 15/07/2016.
  */
object NeoConversions {

  implicit class RowWrapper(resultRow: CypherResultRow) {
    def asBacklogItem: BacklogItem = {
      resultRow match {
        case CypherRow(row: Map[String, Any]) => BacklogItem(
          id = Id(row.get("id").get.asInstanceOf[String]),
          yearId = Id(row.get("yearId").get.asInstanceOf[String]),
          summary = row.get("summary").get.asInstanceOf[String],
          description = row.get("description").get.asInstanceOf[String],
          typeOf = BacklogItemType.withName(row.get("typeOf").get.asInstanceOf[String])
        )
      }
    }

    def asEpoch: Epoch = {
      resultRow match {
        case CypherRow(row: Map[String, Any]) => Epoch(
          id = Id(row.get("id").get.asInstanceOf[String]),
          name = row.get("id").get.asInstanceOf[String],
          totem = row.get("totem").get.asInstanceOf[String],
          question = row.get("question").get.asInstanceOf[String]
        )
      }
    }

    def asFinancialTracking: FinancialTracking = {
      resultRow match {
        case CypherRow(row: Map[String, Any]) => FinancialTracking(
          id = Id(row.get("id").get.asInstanceOf[String]),
          dayId = Id(row.get("dayId").get.asInstanceOf[String]),
          currentAmount = row.get("currentAmount").get.toString.toDouble,
          goalAmount = row.get("goalAmount").get.toString.toDouble,
          paidIn = row.get("paidIn").get.toString.toDouble,
          paidOut = row.get("paidOut").get.toString.toDouble,
          progress = FinancialProgressType.withName(row.get("progress").get.asInstanceOf[String])
        )
      }
    }

    def asGoal: Goal = {
      resultRow match {
        case CypherRow(row: Map[String, Any]) => Goal(
          id = Id(row.get("id").get.asInstanceOf[String]),
          themeId = Id(row.get("themeId").get.asInstanceOf[String]),
          backlogItems = row.get("backlogItems").get.asInstanceOf[Seq[String]].map(id => Id(id)),
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
          goalId = row.get("goalId").map(item => Id(item.asInstanceOf[String])),
          summary = row.get("summary").get.asInstanceOf[String],
          description = row.get("description").get.asInstanceOf[String],
          typeOf = HobbyType.withName(row.get("typeOf").get.asInstanceOf[String]),
          frequency = HobbyFrequency.withName(row.get("frequency").get.asInstanceOf[String]),
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
          order = row.get("order").get.toString.toInt,
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
          order = row.get("order").get.toString.toInt,
          status = StatusType.withName(row.get("status").get.asInstanceOf[String])
        )
      }
    }

    def asReceipt: Receipt = {
      resultRow match {
        case CypherRow(row: Map[String, Any]) => Receipt(
          id = Id(row.get("id").get.asInstanceOf[String]),
          trackingId = Id(row.get("trackingId").get.asInstanceOf[String]),
          purchasedItem = row.get("purchasedItem").get.asInstanceOf[String],
          expenditure = row.get("expenditure").get.toString.toDouble,
          nameOfEstablishment = row.get("nameOfEstablishment").get.asInstanceOf[String]
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
          goalId = row.get("goalId").map(item => Id(item.asInstanceOf[String])),
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
          order = row.get("order").get.toString.toInt,
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
          goalId = row.get("goalId").map(item => Id(item.asInstanceOf[String])),
          status = StatusType.withName(row.get("status").get.asInstanceOf[String]),
          typeOf = WeaveType.withName(row.get("typeOf").get.asInstanceOf[String])
        )
      }
    }

    def asYear: Year = {
      resultRow match {
        case CypherRow(row: Map[String, Any]) => Year(
          id = Id(row.get("id").get.asInstanceOf[String]),
          epochId = Id(row.get("epochId").get.asInstanceOf[String]),
          startDate = DateTime.fromString(row.get("startDate").get.toString),
          finishDate = DateTime.fromString(row.get("finishDate").get.toString)
        )
      }
    }
  }
}