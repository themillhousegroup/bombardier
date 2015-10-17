bombardier
============================

Fetches weather observations from the Australian Bureau of Metorology.


### Installation

Bring in the library by adding the following to your ```build.sbt```. 

  - The release repository: 

```
   resolvers ++= Seq(
     "Millhouse Bintray"  at "http://dl.bintray.com/themillhousegroup/maven"
   )
```
  - The dependency itself: 

```
   libraryDependencies ++= Seq(
     "com.themillhousegroup" %% "bombardier" % "0.1.170"
   )

```

### Usage

Once you have __bombardier__ added to your project, you can start using it like this:

```
import com.themillhousegroup.bombardier._
import scala.concurrent.ExecutionContext.Implicits.global

// Get the latest observation - this is an async op:
val fMaybeObs:Future[Option[Observation]] = Bombardier.observationForLatLong(-37.23D, 145.67D) 

// Do something with it:

fMaybeObs.foreach { maybeObservation =>
	maybeObservation.fold(println("No observation found")) { ob =>
		println("Most recent observation was $ob")
	}
}
```


### Still To-Do
Document how to plug in an alternative HTTP client.

### Credits

 - The [Bureau of Meteorology](http://reg.bom.gov.au/catalogue/data-feeds.shtml) supplies the data.
 
 - The default HTTP client is [Dispatch](http://dispatch.databinder.net/Dispatch.html) although you can supply another if you prefer 

