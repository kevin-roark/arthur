(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);throw new Error("Cannot find module '"+o+"'")}var f=n[o]={exports:{}};t[o][0].call(f.exports,function(e){var n=t[o][1][e];return s(n?n:e)},f,f.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){

var types = require('./types');
var ArthurNumber = require('./arthur-number');
var ArthurMedia = require('./arthur-media');
var ArthurFrame = require('./arthur-frame');

var canvas = document.getElementById('canvas');
var context = canvas.getContext('2d');

module.exports = ArthurColor;

/* assumes either all arthurnumbers, or all js numbers or one string*/
function ArthurColor(r, g, b, a, frame) {
  this.type = types.COLOR;
  if (frame) {
    this.frame = new ArthurFrame(frame);
  }
  if (typeof r == 'object') {
    this.r = r;
    this.g = g;
    this.b = b;
    this.a = a;
  } else if (typeof r == 'string') {
    var ob = JSON.parse(r);
    this.r = new ArthurNumber(ob.r);
    this.g = new ArthurNumber(ob.g);
    this.b = new ArthurNumber(ob.b);
    this.a = new ArthurNumber(ob.a);
    this.frame = ob.frame;
  } else {
    this.r = new ArthurNumber(r);
    this.g = new ArthurNumber(g);
    this.b = new ArthurNumber(b);
    this.a = new ArthurNumber(a);
  }
  this.medfile = null;
}

ArthurColor.prototype.__proto__ = ArthurMedia.prototype;

ArthurColor.prototype.rgba = function() {
  var rgb = 'rgba(' + this.r.int() + ', ';
  rgb += this.g.int() + ', ';
  rgb += this.b.int() + ', ';
  rgb += this.a.val + ')';
  return rgb;
}

ArthurColor.prototype.draw = function() {
  context.fillStyle = this.rgba();
  if (this.frame) {
    context.fillRect(this.frame.x, this.frame.y, this.frame.w, this.frame.h);
  } else {
    context.fillRect(0, 0, canvas.width, canvas.height);
  }
}

ArthurColor.prototype.add = function(color) {
  var r = Math.min(this.r.val + color.r.val, 255);
  var g = Math.min(this.g.val + color.g.val, 255);
  var b = Math.min(this.b.val + color.b.val, 255);
  var a = Math.min(this.a.val + color.a.val, 1.0);
  return new ArthurColor(r, g, b, a);
}

ArthurColor.prototype.minus = function(color) {
  var r = Math.max(this.r.val - color.r.val, 0);
  var g = Math.max(this.g.val - color.g.val, 0);
  var b = Math.max(this.b.val - color.b.val, 0);
  var a = this.a.val;
  return new ArthurColor(r, g, b, a);
}

},{"./arthur-frame":2,"./arthur-media":4,"./arthur-number":5,"./types":11}],2:[function(require,module,exports){

module.exports = ArthurFrame;

function ArthurFrame(frameob) {
  this.x = frameob.x;
  this.y = frameob.y;
  if (frameob.w < 0) {
    this.w = $(window).width();
  } else {
    this.w = frameob.w;
  }
  if (frameob.h < 0) {
    this.h = $(window).height();
  } else {
    this.h = frameob.h;
  }
}

},{}],3:[function(require,module,exports){

var types = require('./types');
var ArthurMedia = require('./arthur-media');
var ArthurFrame = require('./arthur-frame');

var canvas = document.getElementById('canvas');
var context = canvas.getContext('2d');

module.exports = ArthurImage;

function ArthurImage(json) {
  var ob = JSON.parse(json);

  this.type = types.IMAGE;
  this.medfile = ob.filename;
  if (ob.frame) {
    this.frame = new ArthurFrame(ob.frame);
  }

  var img = $('<img class="arthur-image" id="' + this.medfile + '">');
  img.attr('src', this.medfile);
  this.img = img;

  var dom = this.img.get(0);
  this.width = dom.naturalWidth;
  this.height = dom.naturalHeight;

  console.log(this);
}

ArthurImage.prototype.__proto__ = ArthurMedia.prototype;

ArthurImage.prototype.dom = function() {
  var el = $('#' + this.filename);
  return el;
}

ArthurImage.prototype.draw = function() {
  if (!this.width) {
    var dom = this.img.get(0);
    this.width = dom.naturalWidth;
    this.height = dom.naturalHeight;
  }

  if (this.frame) {
    context.drawImage(this.img.get(0), this.frame.x, this.frame.y, this.frame.w, this.frame.h);
  } else {
    context.drawImage(this.img.get(0), 0, 0);
  }
}

},{"./arthur-frame":2,"./arthur-media":4,"./types":11}],4:[function(require,module,exports){

module.exports = ArthurMedia;

function ArthurMedia() {
  this.type = -1;
  this.medfile = null;
  this.glob = false;
  this.active = false;
  this.aid = -1;
}

ArthurMedia.prototype.set = function(media) {
  var g = this.glob;
  var ac = this.active;
  for (var key in media) {
    this[key] = media[key];
  }
  this.glob = g;
  this.active = ac;
  return this;
}

},{}],5:[function(require,module,exports){

var types = require('./types');
var ArthurMedia = require('./arthur-media');

module.exports = ArthurNumber;

ArthurNumber.prototype.__proto__ = ArthurMedia.prototype;

function ArthurNumber(num) {
  this.type = types.NUMBER;
  this.val = num;
}

ArthurNumber.prototype.int = function() {
  return parseInt(this.val);
}

ArthurNumber.prototype.add = function(num) {
  return new ArthurNumber(this.val + num.val);
}

ArthurNumber.prototype.minus = function(num) {
  return new ArthurNumber(this.val - num.val);
}

ArthurNumber.prototype.multiply = function(num) {
  return new ArthurNumber(this.val * num.val);
}

ArthurNumber.prototype.divide = function(num) {
  return new ArthurNumber(this.val / num.val);
}

ArthurNumber.prototype.lessThan = function(num) {
  return (this.val < num.val);
}

ArthurNumber.prototype.greaterThan = function(num) {
  return (this.val > num.val);
}

ArthurNumber.prototype.arthurEquals = function(num) {
  return (this.int() == num.int());
}

},{"./arthur-media":4,"./types":11}],6:[function(require,module,exports){

var types = require('./types');
var ArthurMedia = require('./arthur-media');

module.exports = ArthurSound;

function ArthurSound() {
  this.type = types.SOUND;
}

ArthurSound.prototype.__proto__ = ArthurMedia.prototype;

},{"./arthur-media":4,"./types":11}],7:[function(require,module,exports){

var types = require('./types');
var ArthurMedia = require('./arthur-media');

module.exports = ArthurString;

function ArthurString(text) {
  this.type = types.STRING;
  this.str = text;
}

ArthurString.prototype.__proto__ = ArthurMedia.prototype;

ArthurString.prototype.add = function(s) {
  return new ArthurString(this.str + s.str);
}

ArthurString.prototype.subtract = function(s) {
  return new ArthurString(this.str.replace(s.str, ""));
}

ArthurString.prototype.multiply = function(s) {
  // find longer string
  var longer, shorter;
  if (this.str.length > s.str.length) {
    longer = one.str;
    shorter = two.str;
  } else {
    longer = two.str;
    shorter = one.str;
  }

  // make shorter same length as longer
  var diff = longer.length - shorter.length;
  while(diff >= shorter.length) {
    shorter += shorter;
    diff = longer.length - shorter.length;
  }
  if (diff != 0) {
    shorter += shorter.substring(diff);
  }

  // average the strings
  var product = "";
  for (var i = 0; i < shorter.length(); i++) {
    var avg = (longer.charCodeAt(i) + shorter.charCodeAt(i)) / 2;
    product += String.fromCharCode(avg);
  }

  return new ArthurString(product);
}

ArthurString.prototype.divide = function(s) {
  var reversed = s.str.split("").reverse().join("");
  return this.multiply(new ArthurString(reversed));
}

},{"./arthur-media":4,"./types":11}],8:[function(require,module,exports){

var types = require('./types');
var ArthurMedia = require('./arthur-media');

module.exports = ArthurVideo;

function ArthurVideo() {
  this.type = types.VIDEO;
}

ArthurVideo.prototype.__proto__ = ArthurMedia.prototype;

},{"./arthur-media":4,"./types":11}],9:[function(require,module,exports){

var ArthurNumber = require('./arthur-number');

var startTime = Date.now();
module.exports.start = startTime;

module.exports.ms = function () {
  return new ArthurNumber((Date.now() - startTime));
}

},{"./arthur-number":5}],10:[function(require,module,exports){

// get window.requestAnimationFrame looking good
require('../lib/anim-shim');

// all the nice types
var ArthurMedia = module.exports.ArthurMedia = require('./arthur-media');
var ArthurColor = module.exports.ArthurColor = require('./arthur-color');
var ArthurNumber = module.exports.ArthurNumber = require('./arthur-number');
var ArthurString = module.exports.ArthurString = require('./arthur-string');
var ArthurImage = module.exports.ArthurImage = require('./arthur-image');
var ArthurSound = module.exports.ArthurSound = require('./arthur-sound');
var ArthurVideo = module.exports.ArthurVideo = require('./arthur-video');

// builtin functions like ms
var builtins = module.exports.builtins = require('./builtins');

var canvas = document.getElementById('canvas');
var context = canvas.getContext('2d');
module.exports.canvas = canvas;

var globals = [];
var globalMap = {};
var activeMedia = [];

module.exports.literalWrapper = function(media, filename) {
  media.glob = true;
  if (filename) {
    media.medfile = filename;
    globalMap[filename] = media;
  }
  globals.push(media);
  return media;
}

function resize() {
  canvas.width = $(window).width();
  canvas.height = $(window).height();
}
$(window).resize(resize);
resize();

function clearCanvas() {
  context.clearRect(0, 0, canvas.width, canvas.height);
}

module.exports.updateMedia = function() {
  clearCanvas();
  for (var i = 0; i < activeMedia.length; i++) {
    var media = activeMedia[i];
    if (media.active)
      media.draw();
  }
}

module.exports.addArthurColor = function(json, filename) {
  if (filename) {
    var med = globalMap[filename];
    if (med) {
      med.active = true;
      activeMedia.push(med);
      console.log(med);
      return;
    }
  }

  var ob = JSON.parse(json);

  var color = new ArthurColor(ob.r, ob.g, ob.b, ob.a, ob.frame);
  color.active = true;
  activeMedia.push(color);
}

module.exports.addArthurNumber = function(json) {

}

module.exports.addArthurString = function(filename, frame) {

}

module.exports.addArthurImage = function(json, filename) {
  var med = globalMap[filename];
  if (med) {
    med.active = true;
    activeMedia.push(med);
    return;
  }
  
  var ai = new ArthurImage(json);
  ai.active = true;
  activeMedia.push(ai);
}

module.exports.addArthurSound = function(filename, frame) {

}

module.exports.addArthurVideo = function(filename, frame) {

}

module.exports.add = function(media, frame) {
  // implement later plz vry cool
}

},{"../lib/anim-shim":13,"./arthur-color":1,"./arthur-image":3,"./arthur-media":4,"./arthur-number":5,"./arthur-sound":6,"./arthur-string":7,"./arthur-video":8,"./builtins":9}],11:[function(require,module,exports){

module.exports.COLOR = 0;
module.exports.NUMBER = 1;
module.exports.STRING = 2;
module.exports.IMAGE = 3;
module.exports.SOUND = 4;
module.exports.VIDEO = 5;

},{}],12:[function(require,module,exports){
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
addArthurImage('{"filename": "media/1__image__.jpg", "frame": {"x": 0.0, "y": 0.0, "w": -1.0, "h": -1.0}}', 'media/1__image__.jpg')
addArthurImage('{"filename": "media/2__image__.jpg"}', 'media/2__image__.jpg')
addArthurImage('{"filename": "media/3__image__.jpg"}', 'media/3__image__.jpg')
addArthurImage('{"filename": "media/4__image__.jpg", "frame": {"x": 0.0, "y": 500.0, "w": -1.0, "h": 100.0}}', 'media/4__image__.jpg')
addArthurImage('{"filename": "media/5__image__.jpg", "frame": {"x": 0.0, "y": 600.0, "w": -1.0, "h": 100.0}}', 'media/5__image__.jpg')
addArthurImage('{"filename": "media/6__image__.jpg", "frame": {"x": 100.0, "y": 50.0, "w": 1000.0, "h": 600.0}}', 'media/6__image__.jpg')
addArthurImage('{"filename": "media/7__image__.jpg", "frame": {"x": 250.0, "y": 100.0, "w": 700.0, "h": 500.0}}', 'media/7__image__.jpg')
addArthurColor('{"r": 255.0, "g": 0.0, "b": 255.0, "a": 0.5}', 'media/bg__color__.json')



function init() {
 bg.set(new ArthurColor(255, 0, 255, 0.5));
var starwars = new ArthurMedia();
starwars.set(image(new ArthurString("starwars.jpg")));
var dyl1 = new ArthurMedia();
dyl1.set(image(new ArthurString("dyl1.jpg")));
var dyl2 = new ArthurMedia();
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
add( starwars, frame(new ArthurString("fill")));
add( superflames);
add( supertana);
add( bottomflames, frame(new ArthurNumber(0.0), new ArthurNumber(500.0), new ArthurNumber(-1.0), new ArthurNumber(100.0)));
add( bottomtana, frame(new ArthurNumber(0.0), new ArthurNumber(600.0), new ArthurNumber(-1.0), new ArthurNumber(100.0)));
add( dyl1, frame(new ArthurNumber(100.0), new ArthurNumber(50.0), new ArthurNumber(1000.0), new ArthurNumber(600.0)));
add( dyl2, frame(new ArthurNumber(250.0), new ArthurNumber(100.0), new ArthurNumber(700.0), new ArthurNumber(500.0)));
add( bg);

}

function loop() {
if ( bg.r.greaterThan(new ArthurNumber(0.0))) {
 bg.set( bg.minus(new ArthurColor(1, 1, 1, 1.0)));
}
else if ( bg.r.arthurEquals(new ArthurNumber(0.0))) {
 bg.set(new ArthurColor(255, 0, 255, 0.5));
}

}

looper();

});


},{"./arthur/index":10}],13:[function(require,module,exports){
// from Paul Irish, http://www.paulirish.com/2011/requestanimationframe-for-smart-animating/
(function() {
    var lastTime = 0;
    var vendors = ['webkit', 'moz'];
    for(var x = 0; x < vendors.length && !window.requestAnimationFrame; ++x) {
        window.requestAnimationFrame = window[vendors[x]+'RequestAnimationFrame'];
        window.cancelAnimationFrame =
          window[vendors[x]+'CancelAnimationFrame'] || window[vendors[x]+'CancelRequestAnimationFrame'];
    }

    if (!window.requestAnimationFrame)
        window.requestAnimationFrame = function(callback, element) {
            var currTime = new Date().getTime();
            var timeToCall = Math.max(0, 16 - (currTime - lastTime));
            var id = window.setTimeout(function() { callback(currTime + timeToCall); },
              timeToCall);
            lastTime = currTime + timeToCall;
            return id;
        };

    if (!window.cancelAnimationFrame)
        window.cancelAnimationFrame = function(id) {
            clearTimeout(id);
        };
}());

},{}]},{},[12])