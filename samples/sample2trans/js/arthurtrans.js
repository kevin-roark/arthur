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


addArthurImage('media/1__image__.jpg');
addArthurImage('media/2__image__.jpg');


function init() {
var white = new ArthurMedia();
white.set(new ArthurColor(0, 0, 0, 1.0));
var mystery = new ArthurMedia();
mystery.set(new ArthurColor(127, 127, 30, 1.0));
var dog = new ArthurMedia();
dog.set(image(new ArthurString("whitepoodle.jpg")));
var rect = new ArthurMedia();
rect.set(image(new ArthurString("justawhiterectangle.jpg")));
 rect.set( rect.multiply(new ArthurNumber(2.0)));
var dog2 = new ArthurMedia();
dog2.set( dog.divide(new ArthurNumber(10.0)));
 rect.set( rect.divide(new ArthurNumber(15.0)));
 dog.set( dog2.divide( dog));
 dog.set( dog.divide( rect));
add( rect);
add( dog);

}

function loop() {

}

function make_it_blue_and_z(vid, z) {
return  vid.add( BLUE).add( z);

}

looper();

});

