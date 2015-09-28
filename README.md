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
     "com.themillhousegroup" %% "bombardier" % "0.1.0"
   )

```

### Usage

Once you have __bombardier__ added to your project, you can start using it like this:

```
import com.themillhousegroup.bombardier._

// Get the latest observation:
val fObservation:Future[Observation] = Bombardier.observationFor(-37.23D, 145.67D) 
```


### Still To-Do
Everything.

### Credits

 - The [Bureau of Meteorology](http://reg.bom.gov.au/catalogue/data-feeds.shtml) supplies the data.

