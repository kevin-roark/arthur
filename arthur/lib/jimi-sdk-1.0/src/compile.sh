#!/bin/sh
# Compile script for JDK1.2
javac -classpath $CLASSPATH:. -d classes `cat main_classes.txt`
