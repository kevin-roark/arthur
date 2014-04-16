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

var s = literalWrapper(new ArthurString('{"str": "cool * ", "size": "20.0", "wrap": "true", "frame": {"x": 10.0, "y": 20.0, "w": -1.0, "h": -1.0}}'), 'media/s__string__.json');
var n = literalWrapper(new ArthurNumber(1.0));
var start = literalWrapper(new ArthurNumber(0.0));
var time = literalWrapper(new ArthurNumber(0.0));
var counter = literalWrapper(new ArthurNumber(0.0));
addArthurString('{"str": "cool * ", "size": "20.0", "wrap": "true", "frame": {"x": 10.0, "y": 20.0, "w": -1.0, "h": -1.0}}', 'media/s__string__.json')



function init() {
 n.set(new ArthurNumber(1.0));
 time.set(ms());
 start.set( time);
 counter.set(new ArthurNumber(0.0));
 s.set(new ArthurString("cool * "));
 s.wrap.set(true);
 s.size.set(new ArthurNumber(20.0));
add( s, frame(new ArthurNumber(10.0), new ArthurNumber(20.0)));

}

function loop() {
var cur = new ArthurMedia();
cur.set(ms());
if ( cur.minus( time).lessThan(new ArthurNumber(100.0))) {
return ;
}
 time.set(ms());
if ( counter.lessThan(new ArthurNumber(1.0)) &&  cur.minus( start).greaterThan(new ArthurNumber(5000.0))) {
 s.set( s.multiply(new ArthurString("lol")));
 counter.set( counter.add(new ArthurNumber(1.0)));
}
if ( counter.lessThan(new ArthurNumber(2.0)) &&  cur.minus( start).greaterThan(new ArthurNumber(10000.0))) {
 s.set( s.multiply(new ArthurString("wow")));
 counter.set( counter.add(new ArthurNumber(1.0)));
}
if ( counter.lessThan(new ArthurNumber(3.0)) &&  cur.minus( start).greaterThan(new ArthurNumber(15000.0))) {
 s.set( s.multiply(new ArthurString("ha ha ha")));
 counter.set(new ArthurNumber(0.0));
 start.set(ms());
}
for(var ___idx = 0; ___idx < 3.0; ___idx++) {
 n.set( n.add(new ArthurNumber(1.0)));

}
 s.set( s.add( n));

}

looper();

});

