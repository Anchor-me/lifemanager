package utils

/**
 * Created by mesfinmebrate on 07/08/2016.
 */
object Utils {

  def formatRelationship(relationship: String): String = {
    relationship.replaceAll(" ", "_").toUpperCase
  }
}