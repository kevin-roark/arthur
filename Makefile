
compiler:	.FORCE
	javac arthur/backend/ArthurCompiler.java

jsbuild:
	./node_modules/browserify/bin/cmd.js buster/js/arthurtrans.js > buster/js/artprog.js

clean:
	./supercleaner.sh

.PHONY: .FORCE
