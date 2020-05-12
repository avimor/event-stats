import Stats._
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport

object Server extends PlayJsonSupport {

  def start(implicit as: ActorSystem) = {
    val statsRoute = path("event" / "stats") {
      get {
        complete(Map("types" -> typeCounts, "words" -> wordCounts))
      }
    }
    Http().bindAndHandle(statsRoute, "0.0.0.0", 8080)
  }

}
