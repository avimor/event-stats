import Stats._
import akka.actor.ActorSystem

import scala.util.{Failure, Success}

object Main extends App {

  println("Starting service...")
  implicit val as = ActorSystem("event-stats")
  implicit val ec = as.dispatcher

  Server.start.onComplete {
    case Success(bound) =>
      println(s"Server online at http://${bound.localAddress.getHostString}:${bound.localAddress.getPort}/")
      Processor.start.onComplete { case _ =>
        println(s"Type Counts: $typeCounts")
        println(s"Word Counts: $wordCounts")
        as.terminate()
      }
    case Failure(ex) =>
      Console.err.println("Server could not start!")
      ex.printStackTrace()
      as.terminate()
  }

}
