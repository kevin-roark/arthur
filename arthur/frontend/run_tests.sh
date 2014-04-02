#!/bin/bash
c=0
# gotta go up some levels..
cd ../../

echo $PWD
echo $CLASSPATH

java arthur/frontend/yacctest samples/sample1.art > ./samples/golden/sample1G.txt 2>&1
java arthur/frontend/yacctest samples/sample2.art
java arthur/frontend/yacctest samples/sample3.art
java arthur/frontend/yacctest samples/sample4.art



