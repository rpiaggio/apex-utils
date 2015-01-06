package apexutils

import scala.slick.driver.H2Driver
import scala.slick.driver.PostgresDriver
import scala.slick.driver.MySQLDriver
import play.api.Play
import play.api.Play.current

// Inspired by http://stackoverflow.com/a/19061993
object DynamicDriver {
	val simple = profile.simple

	lazy val profile = {
		val driver = Play.configuration.getString("db.default.driver").get
		if (driver.contains("mysql"))
			MySQLDriver
		else if (driver.contains("postgres"))
			PostgresDriver
		else
			H2Driver
	}
}
