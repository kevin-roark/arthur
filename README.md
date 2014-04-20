arthur
======

Arthur is a great programming language

### EXTERNAL DEPENDENCIES
* you need ffmpeg installed on your computer with libmp3lame (http://www.ffmpeg.org/download.html)
* you also need SoX installed on your computer. (http://sourceforge.net/projects/sox/files/sox/14.4.1/)
  * install lame as well (http://sourceforge.net/projects/lame/files/lame/3.99/lame-3.99.5.tar.gz/download)
  * and mad (http://sourceforge.net/projects/mad/files/libmad/0.15.1b/)
* jflex
* byaccj

### Links to stuff we're using
the lexer: http://jflex.de/
the parser: http://byaccj.sourceforge.net/

### How to test lex and parser stuff
1. Translate lex via the jflex gui
2. Run the byacc compile thing to translate yacc
3. Compile ur java tester code, which references the Lexer produced from
   yacc.
4. Use it!!!


### Latex Files:

LRM: https://www.writelatex.com/849859spdhtc
Tutorial: https://www.writelatex.com/874610nwktjt
