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


addArthurSound('{"filename": "media/1__sound__.mp3"}', 'media/1__sound__.mp3')


function init() {
var out = new ArthurMedia();
out.set(sound(new ArthurString("output.mp3")));
var glass = new ArthurMedia();
glass.set(sound(new ArthurString("glass.mp3")));
var combo = new ArthurMedia();
combo.set( out.add( glass));
add( combo);

}

function loop() {

}

looper();

});

