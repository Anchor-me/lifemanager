package db.neo4j

import org.anormcypher.Neo4jREST
import play.api.Play
import play.api.Play.current
import play.api.libs.ws.WSClient

/**
 * Created by mesfinmebrate on 15/07/2016.
 */
object NeoConfiguration {

  def apply(wsclient: WSClient): Neo4jREST = {
    Neo4jREST(
      host = Play.application.configuration.getString("db.neo4j.host").get,
      port = Play.application.configuration.getString("db.neo4j.port").get.toInt,
      username = Play.application.configuration.getString("db.neo4j.username").get,
      password = Play.application.configuration.getString("db.neo4j.password").get
    )(wsclient)
  }
}