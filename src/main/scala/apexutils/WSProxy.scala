package apexutils

import java.net.URL
import play.api.libs.ws.DefaultWSProxyServer
import play.api.Play
import ApexUtils._
import play.api.libs.ws.WSRequestHolder

object WSProxy {

  val proxyServer = Play.current.configuration.getString("ws.proxyURL").map { urlString =>
    val url = new URL(urlString)
    val (principal, password) = url.getUserInfo.split(":") match { case Array(u, p) => (u, p) }
    DefaultWSProxyServer(url.getHost, url.getPort, url.getProtocol, principal, password)
  }

  implicit class ProxiedWS(ws: WSRequestHolder) {
    def withProxy = proxyServer.map(ws.withProxyServer(_)).getOrElse(ws)
  }
}