#!/bin/bash

# gotta go up some levels..
cd ../../

echo $PWD
echo $CLASSPATH

java arthur/frontend/yacctest samples/sample1.art
java arthur/frontend/yacctest samples/sample2.art
java arthur/frontend/yacctest samples/sample3.art
