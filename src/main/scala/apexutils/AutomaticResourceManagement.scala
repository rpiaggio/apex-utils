package apexutils

//import scala.util.continuations._

// http://stackoverflow.com/questions/2207425/what-automatic-resource-management-alternatives-exists-for-scala
object AutomaticResourceManagement {

  def using[T <: { def close() }](resource: T)(block: T => Unit) {
    try {
      block(resource)
    } finally {
      if (resource != null) resource.close()
    }
  }

  //  trait ContextType[B]
  //  def forceContextType[B]: ContextType[B] = null
  //
  //  // A DC version of 'using'
  //  def resource[X <: { def close() }, B: ContextType](res: X): X @cps[B, B] = shift(using[X, B](res))
  //
  //  // some sugar for reset
  //  def withResources[A](x: => A @cps[A, A]) = reset { x }
}