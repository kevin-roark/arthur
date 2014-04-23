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

var overlay = literalWrapper(new ArthurColor('{"r": 0.0, "g": 255.0, "b": 0.0, "a": 0.0}'), 'media/overlay__color__.json');
addArthurImage('{"filename": "media/1__image__.jpg", "frame": {"x": 0.0, "y": 0.0, "w": -1.0, "h": -1.0}}', 'media/1__image__.jpg')
addArthurColor('{"r": 0.0, "g": 255.0, "b": 0.0, "a": 0.0}', 'media/overlay__color__.json')



function init() {
 overlay.set(new ArthurColor(0, 255, 0, 0.0));
var wow = new ArthurMedia();
wow.set(image(new ArthurString("flames.jpg")));
add( wow, frame(new ArthurString("fill")));
add( overlay);

}

function key(c) {
 c.tint.set(cooler());
 c.size.set(new ArthurNumber(72.0));
add( c);

}

function move(x, y) {
var cool = new ArthurMedia();
cool.set(new ArthurString("cool"));
 cool.tint.set(cooler());
 cool.size.set(new ArthurNumber(24.0));
add( cool, frame( x,  y, new ArthurNumber(40.0), new ArthurNumber(40.0)));

}

function click(x, y) {
if ( overlay.a.arthurEquals(new ArthurNumber(0.5))) {
 overlay.set(new ArthurColor(0, 255, 0, 0.0));
}
else {
 overlay.set(new ArthurColor(0, 255, 0, 0.5));
}

}

looper();

});

