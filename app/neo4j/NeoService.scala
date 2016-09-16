package neo4j

import com.anchor.model.{Spoke, Theme, Goal}
import com.anchor.cypher._
import org.anormcypher.{CypherResultRow, CypherRow, Cypher}
import play.api.libs.ws.ning
import utils.Utils._

/**
 * Created by mesfinmebrate on 15/07/2016.
 */
object NeoService {
  val wsclient = ning.NingWSClient()

  implicit val connection = NeoConfiguration(wsclient)

  implicit val executionContext = scala.concurrent.ExecutionContext.global

  def add(spoke: Spoke): Unit = {
    val query = s"""
                   |CREATE ${spoke.toCypher}
      """.stripMargin

    println(query + "\n")

    Cypher (
      query
    )()
  }

  def connect(id1: String, relationship: String, id2: String): Unit = {
    Cypher (
    s"""
      |MATCH (a {id: "${id1}"}), (b {id: "${id2}"})
      |CREATE (a)-[:${formatRelationship(relationship)}]->(b)
    """.stripMargin
    )()
  }

  def find(id: String): Option[CypherResultRow] = {
    Cypher (
    s"""
      |MATCH (item {id: "${id}"})
      |RETURN item
    """.stripMargin
    )().headOption
  }

  def delete(id: String): Unit = {
    deleteNodeRelationships(id)
    deleteNode(id)
  }

  def deleteNode(id: String): Unit = {
    Cypher (
      s"""
         |MATCH (item {id: "${id}"}) DELETE item
    """.stripMargin
    )()
  }

  def deleteNodeRelationships(id: String): Unit = {
    Cypher (
      s"""
         |MATCH (item {id: "${id}"})-[r]-(n) DELETE r
    """.stripMargin
    )()
  }
}
