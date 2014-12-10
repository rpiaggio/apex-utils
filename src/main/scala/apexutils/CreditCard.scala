package apexutils

class CreditCard private (val number: String) {
  val cleanNumber = number.replaceAll("""[ -]""", "")

  override def toString = {
    cleanNumber.grouped(4).mkString(" ")
  }
}

object CreditCard {
  def apply(number: String): CreditCard = {
    val cleanNumber = number.replaceAll("""[ -]""", "")
    if (cleanNumber.matches("""\d{16}""")) new CreditCard(cleanNumber) else throw new Exception("Invalid Credit Card Number: " + number)
  }

}