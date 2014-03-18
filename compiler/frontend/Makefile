
arthur: flex yacc

flex:
	jflex arthur.flex; javac Lexer.java;

yacc:
	jyacc -J arthur.yacc; javac Parser.java;

lextest:
	javac lextest.java

yacctest:
	javac yacctest.java

clean:
	rm -f *.class; rm -f Lexer.j*; rm -f Parser.j*; rm -f ParserVal.j*
