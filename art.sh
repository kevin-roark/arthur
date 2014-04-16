#!/bin/bash

PROGRAM=$1;

# unpack jars
jar xf slf4j-api-1.7.7.jar
jar xf xuggle-xuggler-5.4.jar
jar xf gson.jar

make compiler

java arthur/backend/ArthurCompiler $@
