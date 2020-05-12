import Stats._
import akka.actor.ActorSystem
import akka.stream.ActorAttributes.supervisionStrategy
import akka.stream.Supervision.resumingDecider
import akka.stream.scaladsl.StreamConverters._
import akka.stream.scaladsl.{Flow, Framing}
import akka.util.ByteString
import play.api.libs.json.{JsValue, Json}

object Processor {

  def start(implicit as: ActorSystem) = {
    println("Capturing STDIN...")
    fromInputStream(() => System.in)
      .via(Framing.delimiter(ByteString("\n"), Int.MaxValue, true))
      .map(line => Json.parse(line.toArray))
      .map(processType)
      .withAttributes(supervisionStrategy(resumingDecider))
      .runForeach(processData)
  }

  def processType(json: JsValue) = {
    incrementType((json \ "event_type").as[String])
    (json \ "data").as[String]
  }

  def processData(text: String) = {
    text.split(" ")
      .filter(s => s.trim.nonEmpty && s.matches("\\w+"))
      .map(_.toLowerCase.trim)
      .foldLeft(Map.empty[String, Int]) { (counts, word) => counts + (word -> (counts.getOrElse(word, 0) + 1)) }
      .foreachEntry(incrementWord)
  }

  val countingFlow: Flow[String, (String, Int), _] = Flow[String]
    .groupBy(512, identity)
    .map(_ -> 1)
    .reduce((l, r) => (l._1, l._2 + r._2))
    .mergeSubstreams

}
