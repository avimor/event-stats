import scala.collection.mutable

object Stats {

  val typeCounts = mutable.Map[String, Long]()
  val wordCounts = mutable.Map[String, Long]()

  def incrementType(typ: String, by: Int = 1) = typeCounts(typ) = typeCounts.getOrElseUpdate(typ, 0) + by

  def incrementWord(word: String, by: Int = 1) = wordCounts(word) = wordCounts.getOrElseUpdate(word, 0) + by

  def incrementWords(words: Map[String, Int]) = words.foreachEntry(incrementWord)

}
