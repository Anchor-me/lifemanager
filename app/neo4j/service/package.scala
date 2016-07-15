package neo4j

import com.anchor.model.{Goal, Id}
import org.anormcypher.NeoNode

/**
 * Created by mesfinmebrate on 15/07/2016.
 */
package object service {

  implicit class NeoNodeWrapper(node: NeoNode) {
    def asGoal: Goal = {
      Goal (
        id = Id(node.props.get("id").asInstanceOf[String]),
        themeId = Id(node.props.get("themeId").asInstanceOf[String]),
        summary = node.props.get("summary").asInstanceOf[String],
        description = node.props.get("description").asInstanceOf[String],
        level = node.props.get("level").asInstanceOf[Int],
        priority = node.props.get("priority").asInstanceOf[Boolean]
      )
    }
  }
}