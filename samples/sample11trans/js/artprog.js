(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);throw new Error("Cannot find module '"+o+"'")}var f=n[o]={exports:{}};t[o][0].call(f.exports,function(e){var n=t[o][1][e];return s(n?n:e)},f,f.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){

var types = require('./types');
var ArthurNumber = require('./arthur-number');
var ArthurMedia = require('./arthur-media');
var ArthurFrame = require('./arthur-frame');

var canvas = document.getElementById('canvas');
var context = canvas.getContext('2d');

module.exports = ArthurColor;

/* assumes either all arthurnumbers, or all js numbers or one string*/
function ArthurColor(r, g, b, a, frame, delay) {
  this.type = types.COLOR;
  if (frame) {
    this.frame = new ArthurFrame(frame);
  }
  if (delay) {
    this.delay = new ArthurNumber(delay);
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
    if (ob.delay) {
      this.delay = new ArthurNumber(ob.delay);
    }
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
    context.fillRect(this.frame.x.int(), this.frame.y.int(), this.frame.w.int(), this.frame.h.int());
  } else {
    context.fillRect(0, 0, canvas.width, canvas.height);
  }
}

ArthurColor.prototype.add = function(color) {
  var r = Math.min(this.r.val + color.r.val, 255);
  var g = Math.min(this.g.val + color.g.val, 255);
  var b = Math.min(this.b.val + color.b.val, 255);
  var a = this.a.val;
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

var ArthurNumber = require('./arthur-number');
var types = require('./types');

module.exports = ArthurFrame;

function ArthurFrame(frameob) {
  this.type = types.FRAME;
  this.x = new ArthurNumber(frameob.x);
  this.y = new ArthurNumber(frameob.y);
  if (frameob.w < 0) {
    this.w = new ArthurNumber($(window).width() - 10);
  } else {
    this.w = new ArthurNumber(frameob.w);
  }
  if (frameob.h < 0) {
    this.h = new ArthurNumber($(window).height() - 10);
  } else {
    this.h = new ArthurNumber(frameob.h);
  }
}

ArthurFrame.prototype.rand = function() {
  var f = {
    x: Math.floor(Math.random() * $(window).width()),
    y: Math.floor(Math.random() * $(window).height()),
    w: Math.floor(Math.random() * $(window).width()),
    h: Math.floor(Math.random() * $(window).height()),
  };
  return new ArthurFrame(f);
}

},{"./arthur-number":5,"./types":11}],3:[function(require,module,exports){

var types = require('./types');
var ArthurMedia = require('./arthur-media');
var ArthurFrame = require('./arthur-frame');
var ArthurNumber = require('./arthur-number');

var canvas = document.getElementById('canvas');
var context = canvas.getContext('2d');

module.exports = ArthurImage;

function ArthurImage(json) {
  var ob;
  if (typeof json == 'string')
    ob = JSON.parse(json);
  else
    ob = json;

  this.type = types.IMAGE;
  this.medfile = ob.filename;
  if (ob.frame) {
    this.frame = new ArthurFrame(ob.frame);
  }
  if (ob.murk) {
    this.murk = new ArthurNumber(ob.murk);
  }
  if (ob.delay) {
    this.delay = new ArthurNumber(ob.delay);
  }

  var img = $('<img class="arthur-image" id="' + this.medfile + '">');
  img.attr('src', this.medfile);
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

  if (this.murk) {
    context.globalAlpha = this.murk.val;
  }

  if (this.frame) {
    context.drawImage(this.img.get(0), this.frame.x.int(), this.frame.y.int(), this.frame.w.int(), this.frame.h.int());
  } else {
    context.drawImage(this.img.get(0), 0, 0);
  }
  context.globalAlpha = 1.0;
}

},{"./arthur-frame":2,"./arthur-media":4,"./arthur-number":5,"./types":11}],4:[function(require,module,exports){

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
  if (typeof num === 'object') {
    return new ArthurNumber(num.val);
  }

  this.type = types.NUMBER;
  this.val = parseFloat(num) || 0;
}

ArthurNumber.prototype.int = function() {
  return Math.round(this.val);
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

ArthurNumber.prototype.lessThanEquals = function(num) {
  return (this.val <= num.val);
}

ArthurNumber.prototype.greaterThan = function(num) {
  return (this.val > num.val);
}

ArthurNumber.prototype.greaterThanEquals = function(num) {
  return (this.val >= num.val);
}

ArthurNumber.prototype.arthurEquals = function(num) {
  return (this.int() == num.int());
}

},{"./arthur-media":4,"./types":11}],6:[function(require,module,exports){

var types = require('./types');
var ArthurMedia = require('./arthur-media');
var ArthurNumber = require('./arthur-number');

module.exports = ArthurSound;

function ArthurSound(json) {
  var ob;
  if (typeof json == 'string')
    ob = JSON.parse(json);
  else
    ob = json;

  this.type = types.SOUND;
  this.medfile = ob.filename;

  if (ob.delay) {
    this.delay = new ArthurNumber(ob.delay);
  }

  var audio = $('<audio class="arthur-audio" id="' + this.medfile + '" preload loop>');
  var dom = audio.get(0);

  var source = $('<source src="' + this.medfile + '" type="audio/mpeg">');
  audio.append(source);

  $('body').append(audio);
  this.audio = dom;
}

ArthurSound.prototype.__proto__ = ArthurMedia.prototype;

ArthurSound.prototype.draw = function() {
  if (this.audio.paused)
    this.play();
}

ArthurSound.prototype.play = function() {
  this.audio.play();
}

ArthurSound.prototype.pause = function() {
  this.audio.pause();
}

},{"./arthur-media":4,"./arthur-number":5,"./types":11}],7:[function(require,module,exports){

var types = require('./types');
var ArthurMedia = require('./arthur-media');
var ArthurColor = require('./arthur-color');
var ArthurFrame = require('./arthur-frame');
var ArthurNumber = require('./arthur-number');

var canwrap = require('../lib/wrap');

var canvas = document.getElementById('canvas');
var context = canvas.getContext('2d');

module.exports = ArthurString;

function ArthurString(json, raw) {

  this.type = types.STRING;

  if (raw) {
    this.str = json;
    return this;
  }

  if (typeof json == 'string') {
    if (json.substring(0, 1) == '{') {
      console.log(json);
      var ob = JSON.parse(json);
    } else {
      var ob = {str: json};
    }
    return new ArthurString(ob);
  }

  if (typeof json.str == 'string')
    this.str = json.str;
  else
    this.str = json.str.str;

  if (json.color) {
    var color = json.color;
    if (color.type == types.COLOR)
      this.color = color;
    else
      this.color = new ArthurColor(color.r, color.g, color.b, color.a);
  }

  if (json.size) {
    if (typeof json.size == 'object')
      this.size = json.size;
    else
      this.size = new ArthurNumber(json.size);
  } else {
    this.size = new ArthurNumber(14);
  }

  if (json.wrap) {
    this.wrap = json.wrap;
  } else {
    this.wrap = false;
  }

  if (json.frame) {
    if (json.frame.type == types.FRAME)
      this.frame = json.frame;
    else
      this.frame = new ArthurFrame(json.frame);
  } else {
    this.frame = ArthurFrame.prototype.rand();
  }
}

ArthurString.prototype.__proto__ = ArthurMedia.prototype;

ArthurString.prototype.fill = function(s) {
  s.color = this.color;
  s.size = this.size;
  s.frame = this.frame;
  s.wrap = this.wrap;
}

ArthurString.prototype.len = function() {
  return new ArthurNumber(this.str.length);
}

ArthurString.prototype.add = function(s) {
  if (s.type == types.STRING)
    var res = new ArthurString(this.str + s.str, true);
  else if (s.type == types.NUMBER)
    var res = new ArthurString(this.str + s.val, true);
  else
    var res = this;

  this.fill(res);
  return res;
}

ArthurString.prototype.subtract = function(s) {
  if (s.type == types.STRING)
    var res = new ArthurString(this.str.replace(s.str, ""), true);
  else if (s.type == types.NUMBER)
    var res = new ArthurString(this.str.replace(s.val + "", ""), true);
  else
    var res = this;

  this.fill(res);
  return res;
}

ArthurString.prototype.multiply = function(s) {
  if (s.type == types.STRING)
    var st = s.str;
  else if (s.type == types.NUMBER)
    var st = s.val + '';
  else
    var st = "";

  // find longer string
  var longer, shorter;
  if (this.str.length > st.length) {
    longer = this.str;
    shorter = st;
  } else {
    longer = st;
    shorter = this.str;
  }

  // make shorter same length as longer
  var diff = longer.length - shorter.length;
  var orig = shorter;
  while(diff >= orig.length) {
    shorter += orig;
    diff = longer.length - shorter.length;
  }
  if (diff != 0) {
    shorter += shorter.substring(diff);
  }

  // average the strings
  var product = "";
  for (var i = 0; i < shorter.length; i++) {
    var avg = (longer.charCodeAt(i) + shorter.charCodeAt(i)) / 2;
    product += String.fromCharCode(avg);
  }

  var res = new ArthurString(product, true);
  this.fill(res);
  return res;
}

ArthurString.prototype.divide = function(s) {
  if (s.type == types.STRING)
    var st = s.str;
  else if (s.type == types.NUMBER)
    var st = s.val + '';
  else
    var st = "";

  var reversed = st.split("").reverse().join("");
  var res = this.multiply(new ArthurString(reversed), true);
  this.fill(res);
  return res;
}

ArthurString.prototype.draw = function() {
  if (this.color)
    context.fillStyle = this.color.rgba();
  else
    context.fillStyle = 'black';

  context.textBaseline = "top";

  if (this.size)
    context.font = this.size.int() + "px monospace";
  else
    context.font = "12px monospace";

  //context.fillText(this.str, this.frame.x.int(), this.frame.y.int());
  drawMultiline(this.str, this.size.val, this.wrap, this.frame.x.int(), this.frame.y.int(), this.frame.w.int());
}

// help for multiline taken from
// http://stackoverflow.com/questions/5026961/html5-canvas-ctx-filltext-wont-do-line-breaks

function drawMultiline(text, size, wrap, x, y, w){
    var textvalArr = toMultiLine(text);
    var linespacing = size;

    // draw each line on canvas.
    for(var i = 0; i < textvalArr.length; i++) {
        var t = textvalArr[i];
        if (wrap) {
          var txt = canwrap(context, t, x, y, w, linespacing);
          for (var i = 0; i < txt.length; i++){
            var item = txt[i];
            context.fillText(item.text, item.x, item.y);
          }
          y += linespacing * txt.length;
        } else {
          context.fillText(t, x, y);
          y += linespacing;
        }
    }
}

function toMultiLine(text){
   var textArr = new Array();
   text = text.replace(/\n\r?/g, '<br/>');
   textArr = text.split("<br/>");
   return textArr;
}

},{"../lib/wrap":14,"./arthur-color":1,"./arthur-frame":2,"./arthur-media":4,"./arthur-number":5,"./types":11}],8:[function(require,module,exports){

var types = require('./types');
var ArthurMedia = require('./arthur-media');
var ArthurFrame = require('./arthur-frame');

module.exports = ArthurVideo;

function ArthurVideo(json) {
  var ob;
  if (typeof json == 'string')
    ob = JSON.parse(json);
  else
    ob = json;

  this.type = types.VIDEO;

  this.medfile = ob.filename;

  if (ob.frame) {
    this.frame = ob.frame;
  } else {
    var frame = {x: 0, y: 0, w: -1, h: -1};
    this.frame = new ArthurFrame(frame);
  }

  if (ob.delay) {
    this.delay = new ArthurNumber(ob.delay);
  }

  this.holder = $("<div class='arthur-video-holder'>");

  var video = $('<video class="arthur-video" id="' + this.medfile + '" preload loop>');
  var dom = video.get(0);

  var source = '<source src="' + this.medfile + '" type="video/mp4">';
  video.html(source);

  this.holder.append(video);

  this.holder.css('left', this.frame.x.val + 'px');
  this.holder.css('top', this.frame.y.val + 'px');
  video.css('width', this.frame.w.val + 'px');
  video.css('height', this.frame.h.val + 'px');

  $('body').append(this.holder);
  this.video = dom;
}

ArthurVideo.prototype.__proto__ = ArthurMedia.prototype;

ArthurVideo.prototype.setFrame = function() {
  var v = $(this.video);
  v.css('left', this.frame.x.int() + 'px');
  v.css('top', this.frame.y.int() + 'px');
  v.css('width', this.frame.w.int() + 'px');
  v.css('height', this.frame.h.int() + 'px');
}

ArthurVideo.prototype.draw = function() {
  if (this.video.paused)
    this.play();
}

ArthurVideo.prototype.play = function() {
  this.video.play();
}

ArthurVideo.prototype.pause = function() {
  this.video.pause();
}

},{"./arthur-frame":2,"./arthur-media":4,"./types":11}],9:[function(require,module,exports){

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

var types = require('./types');

var canvas = document.getElementById('canvas');
var context = canvas.getContext('2d');
module.exports.canvas = canvas;

var globals = [];
var globalMap = {};
var activeMedia = [];

// adds a media to the active Media array, where it is drawn every frame
function makeActive(med) {
  if (!med.delay) {
    med.active = true;
    activeMedia.push(med);
  } else {
    setTimeout(function() {
      med.active = true;
      activeMedia.push(med);
    }, med.delay.val * 1000);
  }
}

function checkGlobal(filename) {
  if (filename) {
    var med = globalMap[filename];
    if (med) {
      makeActive(med);
      return true;
    }
  }
  return false;
}

module.exports.literalWrapper = function(media, filename) {
  if (media instanceof Boolean) return media;

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
  var global = checkGlobal(filename);
  if (global) return;
  
  var color = new ArthurColor(json);
  makeActive(color);
}

module.exports.addArthurNumber = function(json) {

}

module.exports.addArthurString = function(json, filename) {
  var global = checkGlobal(filename);
  if (global) return;

  var str = new ArthurString(json);
  makeActive(str);
}

module.exports.addArthurImage = function(json, filename) {
  var global = checkGlobal(filename);
  if (global) return;

  var ai = new ArthurImage(json);
  makeActive(ai);
}

module.exports.addArthurSound = function(json, filename) {
  var global = checkGlobal(filename);
  if (global) return;

  var as = new ArthurSound(json);
  makeActive(as);
}

module.exports.addArthurVideo = function(json, filename) {
  var global = checkGlobal(filename);
  if (global) return;

  var av = new ArthurVideo(json);
  makeActive(av);
}

module.exports.add = function(media, frame) {
  // finish later plz vry cool
  makeActive(media);
}

},{"../lib/anim-shim":13,"./arthur-color":1,"./arthur-image":3,"./arthur-media":4,"./arthur-number":5,"./arthur-sound":6,"./arthur-string":7,"./arthur-video":8,"./builtins":9,"./types":11}],11:[function(require,module,exports){

module.exports.COLOR = 0;
module.exports.NUMBER = 1;
module.exports.STRING = 2;
module.exports.IMAGE = 3;
module.exports.SOUND = 4;
module.exports.VIDEO = 5;
module.exports.FRAME = 6;

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
var add = arthur.add;
var ms = arthur.builtins.ms;
var literalWrapper = arthur.literalWrapper;
var updateMedia = arthur.updateMedia;
function looper() { requestAnimationFrame(looper); loop(); updateMedia(); }

var lastPhrase = literalWrapper(new ArthurNumber(0.0));
var cmode = literalWrapper(new ArthurNumber(0.0));
var overlay = literalWrapper(new ArthurColor('{"r": 255.0, "g": 0.0, "b": 0.0, "a": 0.5}'), 'media/overlay__color__.json');
addArthurVideo('{"filename": "media/1__video__.mp4"}', 'media/1__video__.mp4')
addArthurSound('{"filename": "media/2__sound__.mp3"}', 'media/2__sound__.mp3')
addArthurSound('{"filename": "media/3__sound__.mp3", "delay": 1.5}', 'media/3__sound__.mp3')
addArthurSound('{"filename": "media/4__sound__.mp3", "delay": 3.0}', 'media/4__sound__.mp3')
addArthurSound('{"filename": "media/5__sound__.mp3", "delay": 7.0}', 'media/5__sound__.mp3')
addArthurSound('{"filename": "media/6__sound__.mp3", "delay": 10.0}', 'media/6__sound__.mp3')
addArthurSound('{"filename": "media/7__sound__.mp3", "delay": 15.0}', 'media/7__sound__.mp3')
addArthurSound('{"filename": "media/8__sound__.mp3", "delay": 20.0}', 'media/8__sound__.mp3')
addArthurColor('{"r": 255.0, "g": 0.0, "b": 0.0, "a": 0.5}', 'media/overlay__color__.json')



function init() {
var glass = new ArthurMedia();
glass.set(sound(new ArthurString("glass.mp3")));
var higlass = new ArthurMedia();
higlass.set( glass.add(new ArthurNumber(60.0)));
var lowglass = new ArthurMedia();
lowglass.set( glass.minus(new ArthurNumber(60.0)));
var repeatGlass = new ArthurMedia();
repeatGlass.set( glass.add( glass).add( glass));
 repeatGlass.set( repeatGlass.multiply(new ArthurNumber(6.0)));
var hello = new ArthurMedia();
hello.set(new ArthurString("Hello My Dear Friends. Welcome to my world.").castTo("Sound"));
 hello.set( hello.divide(new ArthurNumber(8.0)));
var like = new ArthurMedia();
like.set(new ArthurString("Do you like Arthur yet?").castTo("Sound"));
 like.set( like.divide(new ArthurNumber(8.0)));
var please = new ArthurMedia();
please.set(new ArthurString("Please enjoy Arthur today").castTo("Sound"));
 please.set( please.divide(new ArthurNumber(10.0)));
var ball = new ArthurMedia();
ball.set(video(new ArthurString("ball.mp4")));
 ball.set( ball.add( BLUE));
 lastPhrase.set(new ArthurNumber(0.0));
 overlay.set(new ArthurColor(255, 0, 0, 0.5));
 cmode.set(new ArthurNumber(0.0));
add( ball);
add( glass);
add( higlass, new ArthurNumber(1.5));
add( lowglass, new ArthurNumber(3.0));
add( repeatGlass, new ArthurNumber(7.0));
add( hello, new ArthurNumber(10.0));
add( like, new ArthurNumber(15.0));
add( please, new ArthurNumber(20.0));
add( overlay);

}

function loop() {
if ( cmode.arthurEquals(new ArthurNumber(0.0))) {
 overlay.set( overlay.minus(new ArthurColor(1, 0, 0, 1.0)));
 overlay.set( overlay.add(new ArthurColor(0, 1, 0, 1.0)));
if ( overlay.r.lessThanEquals(new ArthurNumber(0.0))) {
 cmode.set(new ArthurNumber(1.0));
}
}
else if ( cmode.arthurEquals(new ArthurNumber(1.0))) {
 overlay.set( overlay.add(new ArthurColor(1, 0, 0, 1.0)));
 overlay.set( overlay.minus(new ArthurColor(0, 1, 0, 1.0)));
if ( overlay.r.greaterThanEquals(new ArthurNumber(255.0))) {
 cmode.set(new ArthurNumber(0.0));
}
}
if (ms().minus( lastPhrase).lessThan(new ArthurNumber(1000.0))) {
return ;
}
 lastPhrase.set(ms());
var p = new ArthurMedia();
p.set(new ArthurString("ARTHUR LIVES"));
 p.size.set(new ArthurNumber(40.0));
add( p);

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

},{}],14:[function(require,module,exports){
// Return an array to iterate over. For my uses this is
// more efficient, because I only need to calculate the line text
// and positions once, instead of each iteration during animations.
module.exports = function wrapText(ctx, text, x, y, maxWidth, lineHeight) {
  var words = text.split('')
    , line = ''
    , lines = [];

  for(var n = 0, len = words.length; n < len; n++){
    var testLine = line + words[n]
      , metrics = ctx.measureText(testLine)
      , testWidth = metrics.width;

    if (testWidth > maxWidth) {
      lines.push({ text: line, x: x, y: y });
      line = words[n] + ' ';
      y += lineHeight;
    } else {
      line = testLine;
    }
  }

  lines.push({ text: line, x: x, y: y });
  return lines;
};

},{}]},{},[12])