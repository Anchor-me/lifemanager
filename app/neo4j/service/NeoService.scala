package neo4j.service

import com.anchor.model.Goal
import org.anormcypher.{NeoNode, CypherRow, Cypher}
import play.api.libs.ws.ning

/**
 * Created by mesfinmebrate on 15/07/2016.
 */
object NeoService {
  val wsclient = ning.NingWSClient()

  implicit val connection = NeoConfiguration(wsclient)

  implicit val executionContext = scala.concurrent.ExecutionContext.global


  def add(goal: Goal): Unit = {
    Cypher(
    s"""
      |CREATE (goal:Goal {
      |id: ${goal.id.id},
      |themeId: ${goal.themeId.id},
      |summary: ${goal.summary},
      |description: ${goal.description},
      |level: ${goal.level},
      |priority: ${goal.priority}
      |})
    """.stripMargin
    )
  }

  def find(id: String): Option[Goal] = {
    Cypher(
    s"""
      |MATCH (goal {id: ${id}}) return goal
    """.stripMargin
    )().headOption.map {
      case CypherRow(name: String, node: NeoNode) => node.asGoal
    }
  }

  def delete(id: String): Unit = {

  }
}
