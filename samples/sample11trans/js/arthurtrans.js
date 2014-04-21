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

var lastPhrase = literalWrapper(new ArthurNumber(0.0));
var cmode = literalWrapper(new ArthurNumber(0.0));
var overlay = literalWrapper(new ArthurColor('{"r": 255.0, "g": 0.0, "b": 0.0, "a": 0.5}'), 'media/overlay__color__.json');
addArthurVideo('{"filename": "media/1__video__.mp4"}', 'media/1__video__.mp4')
addArthurSound('{"filename": "media/2__sound__.mp3"}', 'media/2__sound__.mp3')
addArthurSound('{"filename": "media/3__sound__.mp3", "delay": 1.5}', 'media/3__sound__.mp3')
addArthurSound('{"filename": "media/4__sound__.mp3", "delay": 3.0}', 'media/4__sound__.mp3')
addArthurSound('{"filename": "media/5__sound__.mp3", "delay": 7.0}', 'media/5__sound__.mp3')
addArthurSound('{"filename": "media/6__sound__.mp3", "delay": 10.0}', 'media/6__sound__.mp3')
addArthurSound('{"filename": "media/7__sound__.mp3", "delay": 17.0}', 'media/7__sound__.mp3')
addArthurSound('{"filename": "media/8__sound__.mp3", "delay": 25.0}', 'media/8__sound__.mp3')
addArthurColor('{"r": 255.0, "g": 0.0, "b": 0.0, "a": 0.5}', 'media/overlay__color__.json')



function init() {
var glass = new ArthurMedia();
glass.set(sound(new ArthurString("glass.mp3")));
var higlass = new ArthurMedia();
higlass.set( glass.add(new ArthurNumber(60.0)));
var lowglass = new ArthurMedia();
lowglass.set( glass.minus(new ArthurNumber(60.0)));
var repeatGlass = new ArthurMedia();
repeatGlass.set( glass.add( glass).add( glass));
 repeatGlass.set( repeatGlass.multiply(new ArthurNumber(3.0)));
var hello = new ArthurMedia();
hello.set(new ArthurString("Hello My Dear Friends").castTo("Sound"));
 hello.set( hello.divide(new ArthurNumber(6.0)));
var like = new ArthurMedia();
like.set(new ArthurString("Do you like Arthur yet?").castTo("Sound"));
 like.set( like.divide(new ArthurNumber(7.5)));
var please = new ArthurMedia();
please.set(new ArthurString("Please enjoy Arthur today").castTo("Sound"));
 please.set( please.divide(new ArthurNumber(10.0)));
var ball = new ArthurMedia();
ball.set(video(new ArthurString("ball.mp4")));
 lastPhrase.set(new ArthurNumber(0.0));
 overlay.set(new ArthurColor(255, 0, 0, 0.5));
 cmode.set(new ArthurNumber(0.0));
add( ball);
add( glass);
add( higlass, new ArthurNumber(1.5));
add( lowglass, new ArthurNumber(3.0));
add( repeatGlass, new ArthurNumber(7.0));
add( hello, new ArthurNumber(10.0));
add( like, new ArthurNumber(17.0));
add( please, new ArthurNumber(25.0));
add( overlay);

}

function loop() {
if ( cmode.arthurEquals(new ArthurNumber(0.0))) {
 overlay.set( overlay.minus(new ArthurColor(1, 0, 0, 1.0)));
 overlay.set( overlay.add(new ArthurColor(0, 1, 0, 1.0)));
if ( overlay.r.lessThanEquals(new ArthurNumber(0.0))) {
 cmode.set(new ArthurNumber(1.0));
}
}
else if ( cmode.arthurEquals(new ArthurNumber(1.0))) {
 overlay.set( overlay.add(new ArthurColor(1, 0, 0, 1.0)));
 overlay.set( overlay.minus(new ArthurColor(0, 1, 0, 1.0)));
if ( overlay.r.greaterThanEquals(new ArthurNumber(255.0))) {
 cmode.set(new ArthurNumber(0.0));
}
}
if (ms().minus( lastPhrase).lessThan(new ArthurNumber(1000.0))) {
return ;
}
 lastPhrase.set(ms());
var p = new ArthurMedia();
p.set(new ArthurString("ARTHUR LIVES"));
 p.size.set(new ArthurNumber(40.0));
add( p);

}

looper();

});

