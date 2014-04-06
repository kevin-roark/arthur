(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);throw new Error("Cannot find module '"+o+"'")}var f=n[o]={exports:{}};t[o][0].call(f.exports,function(e){var n=t[o][1][e];return s(n?n:e)},f,f.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){

var types = require('./types');
var ArthurNumber = require('./arthur-number');
var ArthurMedia = require('./arthur-media');

var canvas = document.getElementById('canvas');
var context = canvas.getContext('2d');

module.exports = ArthurColor;

/* assumes either all arthurnumbers, or all js numbers */
function ArthurColor(r, g, b, a, frame) {
  this.type = types.COLOR;
  if (frame) {
    this.frame = frame;
  }
  if (typeof r == 'object') {
    this.r = r;
    this.g = g;
    this.b = b;
    this.a = a;
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
  rgb += this.a.int() + ')';
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

},{"./arthur-media":3,"./arthur-number":4,"./types":10}],2:[function(require,module,exports){

var types = require('./types');
var ArthurMedia = require('./arthur-media');

var canvas = document.getElementById('canvas');
var context = canvas.getContext('2d');

module.exports = ArthurImage;

function ArthurImage(filename, frame) {
  this.type = types.IMAGE;
  this.medfile = filename;
  if (frame) {
    this.frame = frame;
  }

  var img = $('<img class="arthur-image" id="' + filename + '">');
  img.attr('src', filename);
  this.img = img;

  var dom = this.img.get(0);
  this.width = dom.naturalWidth;
  this.height = dom.naturalHeight;
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
    context.drawImage(this.img.get(0), frame.x, frame.y, frame.w, frame.h);
  } else {
    context.drawImage(this.img.get(0), 0, 0, this.width, this.height);
  }
}

},{"./arthur-media":3,"./types":10}],3:[function(require,module,exports){

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
  var a = this.active;
  for (var key in media) {
    this[key] = media[key];
  }
  this.glob = g;
  this.active = a;
  return this;
}

},{}],4:[function(require,module,exports){

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

},{"./arthur-media":3,"./types":10}],5:[function(require,module,exports){

var types = require('./types');
var ArthurMedia = require('./arthur-media');

module.exports = ArthurSound;

function ArthurSound() {
  this.type = types.SOUND;
}

ArthurSound.prototype.__proto__ = ArthurMedia.prototype;

},{"./arthur-media":3,"./types":10}],6:[function(require,module,exports){

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

},{"./arthur-media":3,"./types":10}],7:[function(require,module,exports){

var types = require('./types');
var ArthurMedia = require('./arthur-media');

module.exports = ArthurVideo;

function ArthurVideo() {
  this.type = types.VIDEO;
}

ArthurVideo.prototype.__proto__ = ArthurMedia.prototype;

},{"./arthur-media":3,"./types":10}],8:[function(require,module,exports){

var ArthurNumber = require('./arthur-number');

var startTime = Date.now();
module.exports.start = startTime;

module.exports.ms = function () {
  return new ArthurNumber((Date.now() - startTime));
}

},{"./arthur-number":4}],9:[function(require,module,exports){

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

module.exports.addArthurColor = function(filename, frame) {
  var med = globalMap[filename];
  if (med) {
    med.active = true;
    activeMedia.push(med);
    return;
  }

  $.ajax({
    url: filename,
    success: function(cj) {
      var color = new ArthurColor(cj.r, cj.g, cj.b, cj.a, frame);
      color.active = true;
      activeMedia.push(color);
    }
  });
}

module.exports.addArthurNumber = function(filename, frame) {

}

module.exports.addArthurString = function(filename, frame) {

}

module.exports.addArthurImage = function(filename, frame) {
  var med = globalMap[filename];
  if (med) {
    med.active = true;
    activeMedia.push(med);
    return;
  }

  var ai = new ArthurImage(filename, frame);
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

},{"../lib/anim-shim":12,"./arthur-color":1,"./arthur-image":2,"./arthur-media":3,"./arthur-number":4,"./arthur-sound":5,"./arthur-string":6,"./arthur-video":7,"./builtins":8}],10:[function(require,module,exports){

module.exports.COLOR = 0;
module.exports.NUMBER = 1;
module.exports.STRING = 2;
module.exports.IMAGE = 3;
module.exports.SOUND = 4;
module.exports.VIDEO = 5;

},{}],11:[function(require,module,exports){
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


},{"./arthur/index":9}],12:[function(require,module,exports){
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

},{}]},{},[11])