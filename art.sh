#!/bin/bash

PROGRAM=$1;

make compiler

java arthur/backend/ArthurCompiler $1
