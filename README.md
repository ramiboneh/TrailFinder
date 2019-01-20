The purpose of this application is to find the best matching trail for a user according to his preference. 
The data of the trails is loaded directly from the Boulder Colorado web site to get the most updated data.
In case it fails to load from the web site it takes the data from a local file that was taken offline from this site.
The file is under resources/OSMPTrailheads.csv

The program wes written in java using Eclipse and has the following classes:
Launcher: The entry point for the program and it runs the main flow of the application, it is a singleton
GetUserData: Gets the user preferences for the trail. For each trail attributes it asks the user to give a score of importance
0-5. The interface in this application is simple CLI. It can be easily changed to a rest API. 
TrailsManager: A manager for the trails, it loads the data, parses it and chooses the best trails for the user  
Trail: Holds the data of one trail, and calculates its score according to the user preference.
TrailWantedFeaturs: A simple POJO that holds the user preference for a trail.
LOG: A simple logger implementation. Logs the application errors and debug lines in to trailFinder.log

Code documentation: You can find under the doc directory (created by javadoc) and comments in the code.

To build the application:
1. Install java jdk 1.8 and make sure it is on your path.
2. On Windows under the program main directory run build.bat and on MacOS/Linux under the program main directory run ./build.sh 
The build will create the file trailFinder.jar that can be distributed

To run the application (After building) under the program main directory run: 
java -jar trailFinder.jar

