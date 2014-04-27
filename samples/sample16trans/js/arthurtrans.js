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
var rand = arthur.builtins.rand;
var cooler = arthur.builtins.cooler;
var frame = arthur.builtins.frame;
var literalWrapper = arthur.literalWrapper;

var RED = arthur.builtins.RED;
var WHITE = arthur.builtins.WHITE;
var BLACK = arthur.builtins.BLACK;
var BLUE = arthur.builtins.BLUE;
var GREEN = arthur.builtins.GREEN;
var ORANGE = arthur.builtins.ORANGE;
var YELLOW = arthur.builtins.YELLOW;
var PERRYWINKLE = arthur.builtins.PERRYWINKLE;
var ARTHURS_SKIN = arthur.builtins.ARTHURS_SKIN;
var SARCOLINE = arthur.builtins.SARCOLINE;
var COQUELICOT = arthur.builtins.COQUELICOT;
var SMARAGDINE = arthur.builtins.SMARAGDINE;
var ALMOND = arthur.builtins.ALMOND;
var ASPARAGUS = arthur.builtins.ASPARAGUS;
var BURNT_SIENNA = arthur.builtins.BURNT_SIENNA;
var CERULEAN = arthur.builtins.CERULEAN;
var DANDELION = arthur.builtins.DANDELION;
var DENIM = arthur.builtins.DENIM;
var ELECTRIC_LIME = arthur.builtins.ELECTRIC_LIME;
var FUZZY_WUZZY = arthur.builtins.FUZZY_WUZZY;
var GOLDENROD = arthur.builtins.GOLDENROD;
var JAZZBERRY_JAM = arthur.builtins.JAZZBERRY_JAM;
var MAC_AND_CHEESE = arthur.builtins.MAC_AND_CHEESE;
var MAHOGANY = arthur.builtins.MAHOGANY;
var MANGO_TANGO = arthur.builtins.MANGO_TANGO;
var MAUVELOUS = arthur.builtins.MAUVELOUS;
var PURPLE_PIZZAZZ = arthur.builtins.PURPLE_PIZZAZZ;
var RAZZMATAZZ = arthur.builtins.RAZZMATAZZ;
var SALMON = arthur.builtins.SALMON;
var SILVER = arthur.builtins.SILVER;
var TICKLE_ME_PINK = arthur.builtins.TICKLE_ME_PINK;
var WILD_BLUE_YONDER = arthur.builtins.WILD_BLUE_YONDER;
var WISTERIA = arthur.builtins.WISTERIA;
var LASER_LEMON = arthur.builtins.LASER_LEMON;
var EGGPLANT = arthur.builtins.EGGPLANT;
var CHARTREUSE = arthur.builtins.CHARTREUSE;

var updateMedia = arthur.updateMedia;
var hasLoop = typeof loop != 'undefined';
function looper() { requestAnimationFrame(looper); if (hasLoop) loop(); updateMedia(); }
if (typeof key != 'undefined') $(document).keypress(function(ev) {key(new ArthurString(String.fromCharCode(ev.which))); } );
if (typeof click != 'undefined') $(document).click(function(e) { click(new ArthurNumber(e.clientX), new ArthurNumber(e.clientY)); });
if (typeof move != 'undefined') $(document).mousemove(function(e) { move(new ArthurNumber(e.clientX), new ArthurNumber(e.clientY)); });

var cal = literalWrapper(new ArthurColor('{"r": 236.0, "g": 75.0, "b": 0.0, "a": 1.0}'), 'media/cal__color__.json');
var s = literalWrapper(new ArthurString('{"str": "COQUELICOT", "size": "40.0"}'), 'media/s__string__.json');
addArthurColor('{"r": 236.0, "g": 75.0, "b": 0.0, "a": 1.0}', 'media/cal__color__.json')
addArthurString('{"str": "COQUELICOT", "size": "40.0"}', 'media/s__string__.json')



function init() {
 cal.set(cooler());
 s.set(cal.castTo("string"));
 s.size.set(new ArthurNumber(40.0));
add( cal);
add( s);

}

function loop() {
 cal.set(cooler());
 s.set(cal.castTo("string"));
var a = new ArthurMedia();
a.set(s.castTo("num"));
add(a.castTo("string"));

}

looper();

});

