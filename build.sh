#!/bin/bash
set -x
if !([[ -d bin ]]) ; then
	mkdir bin
fi
cd src
javac -d ../bin trailFinderMain/*.java utils/*.java
cd ../bin
jar cmf ../trailfinder.mf ../trailFinder.jar trailFinderMain utils
cd ..
