package apexutils

trait ApexExecutionContext {
  implicit val context = play.api.libs.concurrent.Execution.Implicits.defaultContext
}