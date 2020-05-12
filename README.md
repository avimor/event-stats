Event stats service
=============

Small, simple and reactive event stats service, written in Scala using Akka Streams & HTTP

### Installation & Execution

Install JDK

http://adoptopenjdk.net

Install sbt

http://www.scala-sbt.org/download.html

Clone the app

`git clone https://github.com/avimor/event-stats.git`

assuming you have the generator binary, chdir and run with pipe

`cd event-stats`

`generator-<os>-amd64 | sbt run`

### Endpoints & API

Events are json lines containing `event_type` & `data` fields, consumed from console (STDIN)

#### `GET /event/stats`

Display the stats so far as json containing count of events by type & count of words appearances found in each event's data field

Example:

`curl -X GET http://localhost:8080/event/stats -H 'cache-control: no-cache' -H 'content-type: application/json;charset=UTF-8'`

Response:

`
{
	"types": {
		"bar": 21,
		"foo": 11,
		"baz": 13
	},
	"words": {
		"dolor": 12,
		"lorem": 12,
		"amet": 3,
		"ipsum": 9,
		"sit": 9
	}
}
`

### Future improvements
* Use light & fast external storage system to store the counts. Might require CAS support. Redis + Redisson's RMap sound like a simple & good fit.  
* Push the events to external queue & stream the events from there (e.g. Kafka/Redis/SQS) 
* Enable parallelism. Might imply proper grouping or CAS operations.
* Tests, error handling & packages...
