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

var time = literalWrapper(new ArthurNumber(0.0));
var screenColor = literalWrapper(new ArthurColor(new ArthurNumber(255.0),new ArthurNumber(0.0),new ArthurNumber(0.0),new ArthurNumber(1.0)), 'media/screenColor__color__.json');
addArthurColor('media/screenColor__color__.json');



function init() {
 screenColor.set( RED);
 time.set(ms());
add( screenColor);

}

function loop() {
if (ms().minus( time).lessThan(new ArthurNumber(20.0))) {
return ;
}
var x = new ArthurMedia();
x.set(new ArthurString("lol"));
if ( screenColor.b.lessThan(new ArthurNumber(255.0)) &&  screenColor.r.arthurEquals(new ArthurNumber(255.0))) {
 screenColor.set( screenColor.add(new ArthurColor(0, 0, 1, 1.0)));
}
else if ( screenColor.b.arthurEquals(new ArthurNumber(255.0)) &&  screenColor.r.greaterThan(new ArthurNumber(0.0))) {
 screenColor.set( screenColor.minus(new ArthurColor(1, 0, 0, 1.0)));
}
else if ( screenColor.r.arthurEquals(new ArthurNumber(0.0)) &&  screenColor.b.greaterThan(new ArthurNumber(0.0))) {
 screenColor.set( screenColor.minus(new ArthurColor(0, 0, 1, 1.0)));
}
else if ( screenColor.b.arthurEquals(new ArthurNumber(0.0)) &&  screenColor.r.lessThan(new ArthurNumber(255.0))) {
 screenColor.set( screenColor.add(new ArthurColor(1, 0, 0, 1.0)));
}
 time.set(ms());

}

looper();

});

