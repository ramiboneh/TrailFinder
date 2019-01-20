if not exist "bin" mkdir bin
cd src
javac -d ..\bin trailFinderMain\*.java utils\*.java
cd ..\bin
jar cmf ..\trailfinder.mf ..\trailFinder.jar trailFinderMain utils
cd..
