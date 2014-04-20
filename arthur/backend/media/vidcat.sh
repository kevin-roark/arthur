#!/bin/bash

echo file \'$1\' >> catlist.txt
echo file \'$2\' >> catlist.txt

ffmpeg -f concat -i catlist.txt -c copy $3

rm catlist.txt
