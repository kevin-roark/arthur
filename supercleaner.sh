#!/bin/bash

rm -f arthur/frontend/*.class;
rm -f arthur/frontend/Lexer.java~

rm -f arthur/backend/*.class;
rm -f arthur/backend/builtins/java/*.class;
rm -f arthur/backend/translator/*.class;
rm -f arthur/backend/media/*.class;
rm -f arthur/backend/whisperer/*.class;
rm -f arthur/backend/translator/java/*.class;
rm -f arthur/backend/translator/js/*.class;

rm -rf ./*.class

rm -r buster
