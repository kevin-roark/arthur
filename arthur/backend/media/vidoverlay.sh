#!/bin/bash

##this is manually changing the size of the quicktime video to fit the footlocker vid... obv 
##obv it should do that automatically..
ffmpeg -i $2 -vf scale=640:360 output.avi

ffmpeg -i $1 -i output.avi -filter_complex "blend=all_mode='overlay':all_opacity=0.7" $3
echo videos have been merged!!
rm output.avi


