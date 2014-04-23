#!/bin/bash

##this is manually changing the size of the quicktime video to fit the footlocker vid... obv
##obv it should do that automatically..
##scale it by the size of the first vid
ffmpeg -i $2 -vf "scale=$4, setsar=1/1" -strict -2 output.avi

ffmpeg -i $1 -vf "scale=$4, setsar=1/1" -strict -2 onesar.mp4

ffmpeg -i onesar.mp4 -i output.avi -strict -2 -filter_complex "blend=all_mode='overlay':all_opacity=0.7" $3

echo videos have been merged!!
rm output.avi
rm onesar.mp4
