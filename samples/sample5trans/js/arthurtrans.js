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

var lol = literalWrapper(new ArthurString('{"str": "lol lol lol ", "color": {"r": 255.0, "g": 0.0, "b": 0.0, "a": 1.0}, "size": "20.0"}'), 'media/lol__string__.json');
addArthurString('{"str": "lol lol lol ", "color": {"r": 255.0, "g": 0.0, "b": 0.0, "a": 1.0}, "size": "20.0"}', 'media/lol__string__.json')



function init() {
 lol.set(new ArthurString('{"str": "lol "}'));
 lol.set( lol.multiply(new ArthurNumber(3.0)));
 lol.color.set( RED);
 lol.size.set(new ArthurNumber(20.0));
add( lol);

}

function loop() {
 lol.size.set( lol.size.add(new ArthurNumber(0.2)));
var temp = new ArthurMedia();
temp.set(new ArthurString('{"str": "cool"}').multiply( lol));
add( temp);

}

looper();

});

