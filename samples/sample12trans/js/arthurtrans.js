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


addArthurVideo('{"filename": "media/1__video__.mp4"}', 'media/1__video__.mp4')
addArthurVideo('{"filename": "media/2__video__.mp4", "frame": {"x": 50.0, "y": 200.0, "w": -1.0, "h": -1.0}, "delay": 10.0}', 'media/2__video__.mp4')


function init() {
var vid = new ArthurMedia();
vid.set(video(new ArthurString(sysargs[0])));
var s = new ArthurMedia();
s.set(sound(new ArthurString(sysargs[1])));
 vid.set( vid.divide(new ArthurNumber(4.0)));
 s.set( s.add(new ArthurColor(180, 127, 255, 1.0)));
var mantra = new ArthurMedia();
mantra.set(new ArthurString("the future is now - Arthur").castTo("Video"));
 vid.set( vid.add( s));
add( vid);
add( mantra, frame(new ArthurNumber(50.0), new ArthurNumber(200.0)), new ArthurNumber(10.0));

}

function loop() {

}

looper();

});

