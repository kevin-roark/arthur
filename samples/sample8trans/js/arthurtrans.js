/**
 * An automatically generated translation from arthur to javascript.
 */

$(function() {

var arthur = require('./arthur/index');
var ArthurMedia = arthur.ArthurMedia;
var ArthurColor = arthur.ArthurColor;
var ArthurNumber = arthur.ArthurNumber;
var ArthurString = arthur.ArthurString;
var ArthurImage = arthur.ArthurImage;
var ArthurSound = arthur.ArthurSound;
var ArthurVideo = arthur.ArthurVideo;
var addArthurColor = arthur.addArthurColor;
var addArthurImage = arthur.addArthurImage;
var addArthurNumber = arthur.addArthurNumber;
var addArthurString = arthur.addArthurString;
var addArthurSound = arthur.addArthurSound;
var addArthurVideo = arthur.addArthurVideo;
var add = arthur.add;
var ms = arthur.builtins.ms;
var literalWrapper = arthur.literalWrapper;
var updateMedia = arthur.updateMedia;
function looper() { requestAnimationFrame(looper); loop(); updateMedia(); }

var source = literalWrapper(new ArthurImage({"filename": "media/source__image__.jpg", "frame": {"x": 0.0, "y": 0.0, "w": -1.0, "h": -1.0}, "murk": 0.5}), 'media/source__image__.jpg');
addArthurImage('{"filename": "media/source__image__.jpg", "frame": {"x": 0.0, "y": 0.0, "w": -1.0, "h": -1.0}, "murk": 0.5}', 'media/source__image__.jpg')
addArthurString('{"str": "@@@@@@@@@@@@@@@@@##@@@@@@@@@@@@@@@@                                            \\n@@@@@@@@@@@@@@@@^L.\\\\@@@@@@@@@@@@@@@                                            \\n@@@@@@@@@@@@@@@hd 7q@# , #@@@@@@@@@                   _                        \\n@@@@@@@@@@@@@@@#v^\\u003d##T] }q@@@@@@@@@         ^` _    .\\u0027                         \\n@^#@@@@F\\"@@@##PT      6  g@@@\\"#@@@@           \\u003e..   -                          \\n@ #@@@@L{@@@F    pmm  \\u003d #@@@@ @@@@@           \\\\   ] \\u0027                          \\n@ @@@@@L{@@F  , @\\" `@   @@@@@ @@@@            \\u003c`L ] .                          \\n@ ##*\\"  {@@4  N L _ @   #*\\"\\"  @@@\\"             ^    ^                          \\n@ _gp@@ ]@@     ! _#@m, _gA@@ @@L _           ,      `                         \\n@ @@@@@ #@F      ##\\" \\"| @@@@@ @@ q@               ! \\u0027                          \\n@ @@@@@L]@_ \\\\         , @@@@@ @k_@@                   ,                        \\n@M@@@@@@@@@     j    q@@@@@@@@@@@@@           \\u0027   A ;                          \\n@@@@@@@@@@@@ \\u003c.-   _~@@@@@@@@@@@@@@            `_   .[                         \\n@@@@@@@@@@@@@g___,- / #@@@@@@@@@@@@           \\u003c l _4.^ (                       \\n@@@@@@@@@@@@F/.\\\\^  /   7@@@@@@@@@@@          {   \\" {L \\\\ .                      \\n@@@@@@@@@@@F \\u0027@kTa_     d@@@@@@@@@@                \\"     -                     \\n@@@@@@@@@@@    +^        #@@@@@@@@@         /  T       .   ,                   \\n@@@@@@@@@@#              H@@@@@@@@@           q        \\\\\\\\  /v                  \\n@@@@@@@@@@P          [   d@@@@@@@@@        l  `        , \\"#\\u0027.r                 \\n@@@@@@@@@# q         L    @@@@@@@@@       -~ l  pqcqp%4]  \\u0027``                  \\n@@@@@@@@#  q         V    @@@@@@@@@       -. ^^ t* v]  #                       \\n@@@@@@@@   q        ~F   q@@@@@@@@@                m@p  }                      \\n@@@@@@@#Wgqg        q    q@@@@@@@@@              ,  ]W  U                      \\n@@@@@@@F  #@q_    _,dg___#@@@@@@@@@                 ] @ `q                     \\n@@@@@@@]] @@@i[__lqa**dl@@@@@@@@@@@              @  @ \\\\  d                     \\n@@@@@@[[[W@@\\"      _,,  #@@@@@@@@@@              (     _  \\\\                    \\n@@@@@@@@d@@@     _ q__ l@@@@@@@@@@@                  L `  p5_                  \\n@@@@@@@@@@@@  k \\u0027+T +[@@@@@@@@@@@@@              v(M#|  #@M/                   \\n@@@@@@@@@@@@   p     [#@@@@@@@@@@@@            ,,.#+_$  -  ~--                 \\n@@@@@@@@@@@@   [     L#@@@@@@@@@@@@            `^\\u0027                             \\n@@@@@@@@@@@@   [     k#@@@@@@@@@@@@EEEEEEEEEEEEEEEEEEEEEEEEWWEEEEEEEEEEEEEEEEE|\\n@@@@@@@@@@@@   M     dd@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@|\\n@@@@@@@@@@@@_  L     f#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@|\\n@@@@@@@@@@@@k  ]T    b#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@|\\n@@@@@@@@@@@@M  ]      l@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@|\\n@@@@@@@@@@#@m_ ]      q@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@|\\n@@@@@@@@@* \\"#_TEaq_   q@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@|\\n@@@@@@@@L    \\"  %%l@W##@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@|\\n@@@@@@@@L\\u0027~q4    t@@E##@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@|\\n@@@@@@@@@@@@@_P~-{__g#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@|\\n@@@@@@@@@@@@@@@@E@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@|\\n##############################################################################!\\n", "color": {"r": 255.0, "g": 0.0, "b": 0.0, "a": 1.0}, "size": "18.0", "frame": {"x": 150.0, "y": 0.0, "w": -1.0, "h": -1.0}}', 'media/2__string__.json')



function asc_compare(i) {
var ascii = new ArthurMedia();
ascii.set(i.castTo("string"));
 ascii.size.set(new ArthurNumber(18.0));
 ascii.tint.set( RED);
 i.murk.set(new ArthurNumber(0.5));
add( i, frame(new ArthurNumber(0.0), new ArthurNumber(0.0)));
add( ascii, frame(new ArthurNumber(150.0), new ArthurNumber(0.0)));

}

function init() {
 source.set(image(new ArthurString("output1.jpg")));
 source.set( source.add(new ArthurString("HA HA HA")));
 source.set( source.add(new ArthurColor(0, 255, 200, 1.0)));
asc_compare( source);

}

function loop() {

}

looper();

});

