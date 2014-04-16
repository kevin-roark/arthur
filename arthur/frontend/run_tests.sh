#!/bin/bash
c=0
# gotta go up some levels..
cd ../../

echo $PWD
echo $CLASSPATH

# clear log
> arthur/frontend/frontend.log

echo "ASTs" >> arthur/frontend/frontend.log

for i in samples/sample*; do
	a=`echo $i | cut -c 9-`
	java arthur/frontend/yacctest $i > samples/suite/X_$a
	DIFF=$(diff samples/suite/X_$a samples/golden/G_$a)
	if [ "$DIFF" == "" ]
	then
		echo $i ".......... OK" >> arthur/frontend/frontend.log
	else
		echo $i ".......... NONO" >> arthur/frontend/frontend.log
	fi
	rm -f samples/suite/X_$a
done


for i in samples/suite/test*; do
	a = `echo $i | cut -c 19-`
# TODO::: java compile actual tests

done 

echo "check frontend.log!!"
