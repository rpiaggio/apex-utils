package apexutils

import java.util.UUID
import java.nio.ByteBuffer
import org.joda.time.ReadablePartial
import org.joda.time.ReadableInstant
import org.joda.time.ReadableDuration

object ApexUtils {
  val LONG_BYTES = 8
  val UUID_BYTES = LONG_BYTES * 2

  def generateUUID = UUID.randomUUID().toString()

  // Random generator
  val random = new scala.util.Random

  def randomString(alphabet: String)(n: Int): String =
    Stream.continually(random.nextInt(alphabet.size)).map(alphabet).take(n).mkString

  val upperCase = randomString(('A' to 'Z').mkString)_
  val lowerCase = randomString(('a' to 'z').mkString)_
  val numbers = randomString(('0' to '9').mkString)_

  def generatePassword = upperCase(1) + lowerCase(1) + numbers(4)
  def generateToken = upperCase(4) + numbers(2)

  implicit class UuidOps(uuid: UUID) {
    def toByteArray = {
      val buffer = ByteBuffer.allocate(UUID_BYTES)
      buffer.putLong(uuid.getMostSignificantBits)
      buffer.putLong(uuid.getLeastSignificantBits)
      buffer.array()
    }

    def toAscii85 = Ascii85Coder.encodeBytesToAscii85(toByteArray)

    def toPlainString = uuid.toString.replaceAll("-", "")
  }

  implicit class StringUuidOps(string: String) {
    def toByteArrayAsAscii85 = Ascii85Coder.decodeAscii85StringToBytes(string)

    def toUUIDAsAscii85 = {
      val buffer = ByteBuffer.allocate(UUID_BYTES)
      buffer.put(toByteArrayAsAscii85)
      buffer.position(0)
      new UUID(buffer.getLong(), buffer.getLong())
    }
  }

  implicit def stringToUuid(string: String) = UUID.fromString(string)

  implicit def stringToOption(string: String) = Some(string)

  implicit class ReadablePartialOps[T <: ReadablePartial](val c: T) extends Ordered[T] {
    def compare(that: T) = c.compareTo(that)
  }

  implicit class ReadableInstantOps[T <: ReadableInstant](val c: T) extends Ordered[T] {
    def compare(that: T) = c.compareTo(that)
  }

  implicit class ReadableDurationOps[T <: ReadableDuration](val c: T) extends Ordered[T] {
    def compare(that: T) = c.compareTo(that)
  }

}