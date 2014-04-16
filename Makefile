
compiler:	.FORCE
	javac arthur/backend/ArthurCompiler.java

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
