#!/bin/bash

rm -f arthur/frontend/*.class;
rm -f arthur/frontend/Lexer.java~

rm -f arthur/backend/translator/*.class;
rm -f arthur/backend/translator/java/*.class;
rm -f arthur/backend/translator/js/*.class;

rm -rf arthur/*.class;
rm -rf ./*.class

rm -f ArthurTranslation.java
