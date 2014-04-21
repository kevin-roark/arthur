#!/bin/bash

ffmpeg -i $1 -strict -2 -vf 'scale=400:300, setsar=1/1' scaled-$1
ffmpeg -i $2 -strict -2 -vf 'scale=400:300, setsar=1/1' scaled-$2

ffmpeg -i scaled-$1 -i scaled-$2 \
-filter_complex '[0:0] [0:1] [1:0] [1:1] concat=n=2:v=1:a=1 [v] [a]' \
-strict -2 -map '[v]' -map '[a]' $3

rm scaled-$1
rm scaled-$2
