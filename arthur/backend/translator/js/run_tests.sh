#!/bin/bash

# gotta go up some levels..
cd ../../../../

D=arthur/backend/translator/js

java $D/JsTranslatorTester samples/sample1.art
java $D/JsTranslatorTester samples/sample2.art
java $D/JsTranslatorTester samples/sample3.art
