#!/bin/bash

# prepend package to parserval
echo "package arthur.frontend;"|cat - ParserVal.java > /tmp/out && mv /tmp/out ParserVal.java

javac Color.java Function.java Identifier.java Num.java StringLit.java \
      SymbolTable.java Token.java Tokens.java Value.java Var.java \
      ParseNode.java ParserVal.java Parser.java Lexer.java
