#!/bin/bash

# gotta go up some levels..
cd ../../../../

D=arthur/backend/translator/java

java $D/JavaTranslatorTester samples/sample1.art
java $D/JavaTranslatorTester samples/sample2.art
java $D/JavaTranslatorTester samples/sample3.art
