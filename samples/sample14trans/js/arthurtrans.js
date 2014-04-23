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
var updateMedia = arthur.updateMedia;
var hasLoop = typeof loop != 'undefined';
function looper() { requestAnimationFrame(looper); if (hasLoop) loop(); updateMedia(); }
if (typeof key != 'undefined') $(document).keypress(function(ev) {key(new ArthurString(String.fromCharCode(ev.which))); } );
if (typeof click != 'undefined') $(document).click(function(e) { click(new ArthurNumber(e.clientX), new ArthurNumber(e.clientY)); });
if (typeof move != 'undefined') $(document).mousemove(function(e) { move(new ArthurNumber(e.clientX), new ArthurNumber(e.clientY)); });

var overlay = literalWrapper(new ArthurColor('{"r": 0.0, "g": 0.0, "b": 0.0, "a": 1.0}'), 'media/overlay__color__.json');
var str = literalWrapper(new ArthurString('{"str": "x", "size": "16.0"}'), 'media/str__string__.json');
addArthurVideo('{"filename": "media/1__video__.mp4"}', 'media/1__video__.mp4')
addArthurSound('{"filename": "media/2__sound__.mp3"}', 'media/2__sound__.mp3')
addArthurSound('{"filename": "media/3__sound__.mp3", "delay": 20.0}', 'media/3__sound__.mp3')
addArthurSound('{"filename": "media/4__sound__.mp3"}', 'media/4__sound__.mp3')
addArthurVideo('{"filename": "media/5__video__.mp4", "frame": {"x": 600.0, "y": 100.0, "w": 500.0, "h": 500.0}, "delay": 12.0, "murk": 0.6}', 'media/5__video__.mp4')
addArthurColor('{"r": 0.0, "g": 0.0, "b": 0.0, "a": 1.0}', 'media/overlay__color__.json')
addArthurString('{"str": "x", "size": "16.0"}', 'media/str__string__.json')



function init() {
 overlay.set(cooler());
 str.set(new ArthurString("x"));
 str.size.set(new ArthurNumber(16.0));
var v = new ArthurMedia();
v.set(video(new ArthurString("ZERO.mp4")));
 v.set( v.multiply(new ArthurNumber(2.0)));
 v.set( v.divide(new ArthurNumber(2.0)));
 v.set( v.add( RED));
var c = new ArthurMedia();
c.set(sound(new ArthurString("club.mp3")));
 c.set( c.multiply(new ArthurNumber(1.3)));
 c.set( c.divide(new ArthurNumber(0.8)));
var f = new ArthurMedia();
f.set(sound(new ArthurString("future.mp3")));
var f2 = new ArthurMedia();
f2.set( f.multiply(new ArthurNumber(0.25)));
 f.set( f.divide( f2));
 f.set( f.divide(new ArthurNumber(0.85)));
var text = new ArthurMedia();
text.set(new ArthurString("THE FUTURE IS NOW. BELIEVE THAT, HUMAN.").castTo("Sound"));
 text.set( text.divide(new ArthurNumber(13.0)));
 text.set( text.minus(new ArthurNumber(2.0)));
 text.set( text.add(new ArthurColor(200, 127, 127, 1.0)));
 text.set( text.multiply(new ArthurNumber(0.8)));
var words = new ArthurMedia();
words.set(new ArthurString("THE FUTURE IS NOW AND I AM NOT AFRAID.").castTo("Video"));
 words.murk.set(new ArthurNumber(0.6));
add( v);
add( f);
add( c, new ArthurNumber(20.0));
add( text);
add( words, frame(new ArthurNumber(600.0), new ArthurNumber(100.0), new ArthurNumber(500.0), new ArthurNumber(500.0)), new ArthurNumber(12.0));
add( overlay);
add( str);

}

function move(x, y) {
 overlay.set(cooler());
 overlay.a.set(new ArthurNumber(0.42));

}

function click(x, y) {
 str.set( str.add(new ArthurString("x")));
 str.frame.x.set( str.frame.x.add(new ArthurNumber(5.0).multiply(rand()).minus(new ArthurNumber(2.5))));
 str.frame.y.set( str.frame.y.add(new ArthurNumber(5.0).multiply(rand()).minus(new ArthurNumber(2.5))));

}

looper();

});

