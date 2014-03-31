
arthur: flex yacc

flex:
	jflex arthur.flex; ./compile_flex.sh;

yacc:
	jyacc -J arthur.yacc; ./compile_yacc.sh;

test:
	./compile_tests.sh
	./run_tests.sh

clean:
	cd ../../; ./supercleaner.sh

all: clean arthur test
