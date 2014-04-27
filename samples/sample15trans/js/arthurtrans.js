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

var fut = literalWrapper(new ArthurSound({"filename": "media/fut__sound__.mp3"}), 'media/fut__sound__.mp3');
var lyric = literalWrapper(new ArthurString('{"str": "And we ain\\u0027t never going back to what we used to do I was gon\\u0027 lie to you but I had to tell the truth I\\u0027m just being honest I\\u0027M JUST BEING HONEST I\\u0027M JUST BEING HONEST", "size": "50.0", "frame": {"x": 10.0, "y": 300.0, "w": -1.0, "h": -1.0}}'), 'media/lyric__string__.json');
var ure = literalWrapper(new ArthurSound({"filename": "media/ure__sound__.mp3"}), 'media/ure__sound__.mp3');
addArthurSound('{"filename": "media/fut__sound__.mp3"}', 'media/fut__sound__.mp3')
addArthurSound('{"filename": "media/ure__sound__.mp3"}', 'media/ure__sound__.mp3')
addArthurString('{"str": "And we ain\\u0027t never going back to what we used to do I was gon\\u0027 lie to you but I had to tell the truth I\\u0027m just being honest I\\u0027M JUST BEING HONEST I\\u0027M JUST BEING HONEST", "size": "50.0", "frame": {"x": 10.0, "y": 300.0, "w": -1.0, "h": -1.0}}', 'media/lyric__string__.json')



function init() {
 fut.set(sound(new ArthurString("honest.mp3")));
 lyric.set(new ArthurString("And we ain't never going back to what we used to do I was gon' lie to you but I had to tell the truth I'm just being honest I'M JUST BEING HONEST I'M JUST BEING HONEST"));
 lyric.size.set(new ArthurNumber(50.0));
 ure.set(lyric.castTo("Sound"));
 ure.set( ure.add(new ArthurNumber(4.0)));
 ure.set( ure.divide(new ArthurNumber(10.0)));
add( fut);
add( ure);
add( lyric, frame(new ArthurNumber(10.0), new ArthurNumber(300.0)));

}

looper();

});

