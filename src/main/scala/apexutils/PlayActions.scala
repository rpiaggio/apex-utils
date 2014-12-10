package apexutils

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import play.api.db.slick.CurrentDBAction
import play.api.db.slick.DBSessionRequest
import play.api.db.slick.Database
import play.api.db.slick.MaybeApplication
import play.api.db.slick.PredicatedDBAction
import play.api.db.slick.Session
import play.api.mvc.Action
import play.api.mvc.ActionBuilder
import play.api.mvc.ActionFunction
import play.api.mvc.ActionRefiner
import play.api.mvc.AnyContent
import play.api.mvc.BodyParser
import play.api.mvc.BodyParsers.parse.anyContent
import play.api.mvc.Request
import play.api.mvc.Result
import play.api.mvc.WrappedRequest
import play.api.mvc.Results._
import play.api.Play
import play.api.Play.current
import org.joda.time.DateTime

// WARNING! DB will not be available in Future thread! Only in current thread.
// See https://github.com/playframework/play-slick/blob/master/code/src/main/scala/play/api/db/slick/DBAction.scala

// This is just to make public the protected methods from CurrentDBAction so we can use it as delegate
class OpenCurrentDBAction extends CurrentDBAction {
  val current = db(defaultName, None)

  def isDBAvailable = super.isDBAvailable(current.name)

  val errorPageResult = errorPage
}

trait Authenticator[U] {
  case class AuthenticatedDBSessionRequest[A](user: U,
                                              dbSession: Session,
                                              //                                              dbExecutionContext: ExecutionContext,
                                              request: Request[A]) extends WrappedRequest[A](request)

  // Partial type application, from: http://stackoverflow.com/questions/19343159/scala-type-error-takes-no-type-parameters-expected-one
  // ^^ Not used in the end. Results in messed up type inference.
  trait AuthenticatedDB extends ActionBuilder[AuthenticatedDBSessionRequest] {
    val userIdKey: String

    val loginViewResult: String => Result

    def userRetriever(email: String)(session: Session): Option[U]

    val delegate = new OpenCurrentDBAction

    def invokeBlock[A](request: Request[A], block: AuthenticatedDBSessionRequest[A] => Future[Result]) = {
      implicit val implicitRequest = request

      //      if (Play.isProd && !request.secure) {
      //        // http://stackoverflow.com/questions/19147147/best-way-to-force-playframework-2-to-always-use-ssl
      //        Future.successful(Redirect(s"https://${request.domain}${request.path}", 301))
      //      } else {
      request.session.get("sessionStart").toRight("You must first log in.").right.flatMap({ sessionStart =>
        val endDateTime = Play.current.configuration.getMilliseconds("session.timeout").map(millis => DateTime.parse(sessionStart).plusMillis(millis.toInt))
        if (endDateTime.isDefined && endDateTime.get.isBefore(DateTime.now)) {
          Left("Session expired. Please log in again.")
        } else {
          request.session.get(userIdKey).toRight("You must first log in.").right.flatMap({ userId =>
            if (delegate.isDBAvailable) {
              delegate.current.withSession { session =>
                userRetriever(userId)(session).toRight("Invalid user.").right.map({ user => block(AuthenticatedDBSessionRequest(user, session, request)) })
              }
            } else {
              Right(Future.successful(delegate.errorPageResult))
            }
          })
        }
      }) match {
        case Right(result) => result
        case Left(message) => Future.successful(loginViewResult(message).withSession("uri" -> request.uri))
      }
      //      }
    }
  }

  implicit def dbSessionRequestToSession[A](implicit x: DBSessionRequest[A]) = x.dbSession
  //  implicit def dbSessionRequestToSession[A](implicit x: { def dbSession: Session }) = x.dbSession
  //  implicit def authenticatedDbSessionRequestToSession[A, U](implicit x: AuthenticatedDBSessionRequest[A, U]) = x.dbSession
  implicit def authenticatedDbSessionRequestToSession[A](implicit x: AuthenticatedDBSessionRequest[A]) = x.dbSession
  implicit def authenticatedDbSessionRequestToUser[A](implicit x: AuthenticatedDBSessionRequest[A]) = x.user
  implicit def authenticatedDbSessionRequestToUserOption[A](implicit x: AuthenticatedDBSessionRequest[A]) = Some(x.user)
}
