# Fun with spirals
Interactive command line interface to calculate the Manhattan distance between a given location and the center in a two-dimensional spiral matrix.

### Description
Fill a two-dimensional NxN grid with NxN numbers (N is odd and the numbers are monotone increasing).
Start in the middle/center  and walk counter-clockwise to the outside. The middle square starts with 1.

```
17  16  15  14  13
18   5   4   3  12
19   6   1   2  11
20   7   8   9  10
21  22  23---> ...
```

Now given a location (one of the cell values), calculate the [Manhattan distance](https://en.wikipedia.org/wiki/Taxicab_geometry) to the center.

For example:

From square/location 1 the distance is 0.  
From location 12 the distance is 3 (down, left, left).  
From location 23 the distance is only 2 (up twice).  
From location 1024 the distance is 31.  
From location 368078 the distance is 371.

### Requirements
- Java 1.8
- Git

### Get started
Clone repo
```sh
$ git clone [git-repo-url] fun-with-spirals
$ cd fun-with-spirals
```
Compile and assemble a JAR archive
```sh
$ ./gradlew jar
```
Execute runnable JAR
```sh
$ java -jar build/libs/fun-with-spirals-1.0.0.jar
```
Run tests
```sh
$ ./gradlew test
```
Run using Gradle
```sh
$ ./gradlew run
```