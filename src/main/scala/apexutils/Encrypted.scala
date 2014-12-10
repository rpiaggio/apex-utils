package apexutils

import scala.slick.driver.JdbcDriver.simple.MappedColumnType
import scala.slick.driver.JdbcDriver.simple.stringColumnType
import play.api.libs.Crypto

object Encrypted {
  case class Encrypted[T](value: T) {
    override def toString = value.toString
  }

  implicit def encryptedToValue[T](encrypted: Encrypted[T]) = encrypted.value

  implicit def valueToEncrypted[T](value: T) = Encrypted[T](value)

  implicit def encryptedMapper[T](implicit m: Manifest[T]) =
    MappedColumnType.base[Encrypted[T], String](
      encrypted => Crypto.encryptAES(encrypted.toString),
      string => Encrypted[T](m.runtimeClass.getConstructor(classOf[String]).newInstance(Crypto.decryptAES(string)).asInstanceOf[T])
    )
}