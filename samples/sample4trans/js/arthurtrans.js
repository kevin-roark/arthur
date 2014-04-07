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
var ms = arthur.builtins.ms;
var literalWrapper = arthur.literalWrapper;
var updateMedia = arthur.updateMedia;
function looper() { requestAnimationFrame(looper); loop(); updateMedia(); }

var bg = literalWrapper(new ArthurColor('{"r": 255.0, "g": 0.0, "b": 255.0, "a": 0.5}'), 'media/bg__color__.json');
var dyl2 = literalWrapper(new ArthurImage({"filename": "media/dyl2__image__.jpg", "frame": {"x": 250.0, "y": 100.0, "w": 700.0, "h": 500.0}, "murk": 0.6}), 'media/dyl2__image__.jpg');
var left = literalWrapper(new ArthurNumber(0.0));
addArthurImage('{"filename": "media/1__image__.jpg", "frame": {"x": 0.0, "y": 0.0, "w": -1.0, "h": -1.0}}', 'media/1__image__.jpg')
addArthurImage('{"filename": "media/2__image__.jpg"}', 'media/2__image__.jpg')
addArthurImage('{"filename": "media/3__image__.jpg"}', 'media/3__image__.jpg')
addArthurImage('{"filename": "media/4__image__.jpg", "frame": {"x": 0.0, "y": 500.0, "w": -1.0, "h": 100.0}}', 'media/4__image__.jpg')
addArthurImage('{"filename": "media/5__image__.jpg", "frame": {"x": 0.0, "y": 600.0, "w": -1.0, "h": 100.0}}', 'media/5__image__.jpg')
addArthurImage('{"filename": "media/6__image__.jpg", "frame": {"x": 100.0, "y": 50.0, "w": 1000.0, "h": 600.0}}', 'media/6__image__.jpg')
addArthurImage('{"filename": "media/dyl2__image__.jpg", "frame": {"x": 250.0, "y": 100.0, "w": 700.0, "h": 500.0}, "murk": 0.6}', 'media/dyl2__image__.jpg')
addArthurColor('{"r": 255.0, "g": 0.0, "b": 255.0, "a": 0.5}', 'media/bg__color__.json')



function init() {
 bg.set(new ArthurColor(255, 0, 255, 0.5));
 left.set(new ArthurNumber(0.0));
var starwars = new ArthurMedia();
starwars.set(image(new ArthurString("starwars.jpg")));
var dyl1 = new ArthurMedia();
dyl1.set(image(new ArthurString("dyl1.jpg")));
 dyl2.set(image(new ArthurString("dyl2.jpg")));
var flames = new ArthurMedia();
flames.set(image(new ArthurString("flames.jpg")));
var gold = new ArthurMedia();
gold.set(image(new ArthurString("gold.jpg")));
var katana = new ArthurMedia();
katana.set(image(new ArthurString("katana.jpg")));
 katana.set( katana.multiply(new ArthurNumber(0.1)));
var goldtana = new ArthurMedia();
goldtana.set( katana.divide( gold).divide(new ArthurNumber(4.0)));
var supertana = new ArthurMedia();
supertana.set( goldtana);
var i = new ArthurMedia();
i.set(new ArthurNumber(0.0));
while ( i.lessThan(new ArthurNumber(4.0))) {
 supertana.set( supertana.add( katana));
 supertana.set( supertana.add( goldtana));
 i.set( i.add(new ArthurNumber(1.0)));
}
var superflames = new ArthurMedia();
superflames.set( flames.multiply(new ArthurNumber(1.5)));
 superflames.set( superflames.add( superflames).add( superflames).add( superflames));
 dyl1.set( dyl1.divide( gold).divide( flames.divide(new ArthurNumber(3.0))));
var bottomflames = new ArthurMedia();
bottomflames.set( superflames.multiply(new ArthurNumber(1.0)));
var bottomtana = new ArthurMedia();
bottomtana.set( supertana.multiply(new ArthurNumber(1.0)));
 dyl2.murk.set(new ArthurNumber(0.6));
add( starwars, frame(new ArthurString("fill")));
add( superflames);
add( supertana);
add( bottomflames, frame(new ArthurNumber(0.0), new ArthurNumber(500.0), new ArthurNumber(-1.0), new ArthurNumber(100.0)));
add( bottomtana, frame(new ArthurNumber(0.0), new ArthurNumber(600.0), new ArthurNumber(-1.0), new ArthurNumber(100.0)));
add( dyl1, frame(new ArthurNumber(100.0), new ArthurNumber(50.0), new ArthurNumber(1000.0), new ArthurNumber(600.0)));
add( dyl2, frame(new ArthurNumber(250.0), new ArthurNumber(100.0), new ArthurNumber(700.0), new ArthurNumber(500.0)));
add( bg);

}

function bgcolor() {
if ( bg.r.greaterThan(new ArthurNumber(0.0))) {
 bg.set( bg.minus(new ArthurColor(1, 1, 1, 1.0)));
}
else if ( bg.r.arthurEquals(new ArthurNumber(0.0))) {
 bg.set(new ArthurColor(255, 0, 255, 0.5));
}

}

function dylanpos() {
if ( left.lessThan(new ArthurNumber(1.0))) {
 dyl2.frame.x.set( dyl2.frame.x.add(new ArthurNumber(1.0)));
if ( dyl2.frame.x.greaterThan(new ArthurNumber(800.0))) {
 left.set(new ArthurNumber(2.0));
}
}
else if ( left.greaterThan(new ArthurNumber(1.0))) {
 dyl2.frame.x.set( dyl2.frame.x.minus(new ArthurNumber(1.0)));
if ( dyl2.frame.x.lessThan(new ArthurNumber(1.0))) {
 left.set(new ArthurNumber(0.0));
}
}

}

function loop() {
bgcolor();
dylanpos();

}

looper();

});

