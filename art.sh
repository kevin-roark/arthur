#!/bin/bash

PROGRAM=$1;

# unpack jars
make jars

make compiler

java arthur/backend/ArthurCompiler $@
