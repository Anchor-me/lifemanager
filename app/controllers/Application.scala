package controllers

import org.anormcypher.{Cypher, Neo4jREST}
import play.api.libs.ws.ning
import play.api.mvc._

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def testNeo = Action {

    // Provide an instance of WSClient
    val wsclient = ning.NingWSClient()

    // Setup the Rest Client
    // Need to add the Neo4jConnection type annotation so that the default
    // Neo4jConnection -> Neo4jTransaction conversion is in the implicit scope
    implicit val connection = Neo4jREST("localhost", 7474, "neo4j", "neoistheone")(wsclient)

    // Provide an ExecutionContext
    implicit val ec = scala.concurrent.ExecutionContext.global

    // create some test nodes
    Cypher("""create (anorm:anormcyphertest {name:"AnormCypher"}), (test:anormcyphertest {name:"Test"})""").execute()

    // a simple query
    val req = Cypher("match (n:anormcyphertest) return n.name")

    // get a stream of results back
    val stream = req()

    // get the results and put them into a list
    stream.map(row => {row[String]("n.name")}).toList

    //    // remove the test nodes
    //    Cypher("match (n:anormcyphertest) delete n")()

    // shut down WSClient
    wsclient.close()

    Ok("It works!\n")
  }

}
