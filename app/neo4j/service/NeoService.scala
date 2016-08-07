package neo4j.service

import com.anchor.model.Goal
import org.anormcypher.{CypherResultRow, NeoNode, CypherRow, Cypher}
import play.api.libs.ws.ning

/**
 * Created by mesfinmebrate on 15/07/2016.
 */
object NeoService {
  val wsclient = ning.NingWSClient()

  implicit val connection = NeoConfiguration(wsclient)

  implicit val executionContext = scala.concurrent.ExecutionContext.global


  def add(goal: Goal): Unit = {
    val result = Cypher (
    s"""
      |CREATE (goal:Goal {
      |id: "${goal.id.id}",
      |themeId: "${goal.themeId.id}",
      |summary: "${goal.summary}",
      |description: "${goal.description}",
      |level: ${goal.level},
      |priority: ${goal.priority}
      |})
    """.stripMargin
    )()
    println(result)
  }

  def connect(id1: String, id2: String): Unit = {
    val result = Cypher (
    s"""
      |MATCH (a {id: "${id1}"}), (b {id: "${id2}"})
      |CREATE (a)-[:CONNECTED_TO]->(b)
    """.stripMargin
    )()
    println(result)
  }

  def find(id: String): Option[Goal] = {
    Cypher (
    s"""
      |MATCH (goal {id: "${id}"})
      |RETURN goal
    """.stripMargin
    )().headOption.map {
      case CypherRow(row: Map[String, Any]) => row.asGoal
    }
  }

  def delete(id: String): Unit = {
    Cypher (
    s"""
      |MATCH (item {id: "${id}"})-[r]-(n) DELETE r
    """.stripMargin
    )()
    Cypher (
    s"""
      |MATCH (item {id: "${id}"}) DELETE item
    """.stripMargin
    )()
  }
}
