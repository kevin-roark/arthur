
compiler:	.FORCE
	javac arthur/backend/ArthurCompiler.java

compile: arthur/backend/ArthurCompiler.class

arthur/backend/ArthurCompiler.class:
	javac arthur/backend/ArthurCompiler.java

jars:
	jar xf slf4j-api-1.7.7.jar
	jar xf xuggle-xuggler-5.4.jar
	jar xf gson.jar

jsbuild:
	./node_modules/browserify/bin/cmd.js buster/js/arthurtrans.js > buster/js/artprog.js
	rm -rf buster/js/arthur
	rm -f buster/js/lib/wrap.js
	rm -f buster/js/lib/anim-shim.js

dirtybuild:
	./node_modules/browserify/bin/cmd.js buster/js/arthurtrans.js > buster/js/artprog.js

clean:
	./supercleaner.sh

.PHONY: .FORCE
