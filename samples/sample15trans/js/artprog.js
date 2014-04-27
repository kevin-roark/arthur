(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);throw new Error("Cannot find module '"+o+"'")}var f=n[o]={exports:{}};t[o][0].call(f.exports,function(e){var n=t[o][1][e];return s(n?n:e)},f,f.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){

var types = require('./types');
var ArthurNumber = require('./arthur-number');
var ArthurMedia = require('./arthur-media');
var ArthurFrame = require('./arthur-frame');
var ArthurString, builtins;

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

  if (r instanceof ArthurNumber) {
    this.r = r;
    this.g = g;
    this.b = b;
    this.a = a;
  } else if (r instanceof ArthurColor) {
    this.r = r.r;
    this.g = r.g;
    this.b = r.b;
    this.a = r.a;
  } else if (typeof r == 'object') {
    this.r = new ArthurNumber(r.r);
    this.g = new ArthurNumber(r.g);
    this.b = new ArthurNumber(r.b);
    this.a = new ArthurNumber(r.a);
    this.frame = r.frame;
    if (r.delay) {
      this.delay = new ArthurNumber(r.delay);
    }
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

  if (!this.a) {
    this.a = new ArthurNumber(1.0);
  }
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

  if (!this.frame) {
    this.frame = new ArthurFrame({x: 0, y: 0, w: canvas.width, h: canvas.height});
  }

  context.fillRect(this.frame.x.int(), this.frame.y.int(), this.frame.w.int(), this.frame.h.int());
}

ArthurColor.prototype.add = function(color) {
  if (color.type == types.COLOR) {
    var r = Math.min(this.r.val + color.r.val, 255);
    var g = Math.min(this.g.val + color.g.val, 255);
    var b = Math.min(this.b.val + color.b.val, 255);
    var a = this.a.val;

    var ob = {r: r, g: g, b: b, a: a, frame: this.frame, delay: this.delay};
    return new ArthurColor(ob);
  } else if (color.type == types.NUMBER) {
    var abs = Math.abs(color.val);
    var r = Math.max(this.r.val + abs, 255);
    var g = Math.max(this.g.val + abs, 255);
    var b = Math.max(this.b.val + abs, 255);
    var a = this.a.val;

    var ob = {r: r, g: g, b: b, a: a, frame: this.frame, delay: this.delay};
    return new ArthurColor(ob);
  } else if (color.type == types.STRING) {
    var c = color.castTo('color');
    return this.add(c);
  }

  return this;
}

ArthurColor.prototype.minus = function(color) {
  if (color.type == types.COLOR) {
    var r = Math.max(this.r.val - color.r.val, 0);
    var g = Math.max(this.g.val - color.g.val, 0);
    var b = Math.max(this.b.val - color.b.val, 0);
    var a = this.a.val;

    var ob = {r: r, g: g, b: b, a: a, frame: this.frame, delay: this.delay};
    return new ArthurColor(ob);
  } else if (color.type == types.NUMBER) {
    var abs = Math.abs(color.val);
    var r = Math.min(this.r.val - abs, 0);
    var g = Math.min(this.g.val - abs, 0);
    var b = Math.min(this.b.val - abs, 0);
    var a = this.a.val;

    var ob = {r: r, g: g, b: b, a: a, frame: this.frame, delay: this.delay};
    return new ArthurColor(ob);
  } else if (color.type == types.STRING) {
    var c = color.castTo('color');
    return this.minus(c);
  }

  return this;
}

ArthurColor.prototype.multiply = function(two) {
  if (two.type == types.COLOR) {
    var ob = {
      r: (this.r.val + two.r.val) / 2,
      g: (this.g.val+ two.g.val) / 2,
      b: (this.b.val+ two.b.val) / 2,
      a: Math.max(this.a.val, two.a.val),
      frame: this.frame,
      delay: this.delay
    };

    return new ArthurColor(ob);
  } else if (two.type == types.NUMBER) {
    var abs = Math.abs(two.val);
    var ob = {
      r: Math.max(this.r.val * abs, 255),
      g: Math.max(this.g.val * abs, 255),
      b: Math.max(this.b.val * abs, 255),
      a: Math.min(this.a.val * abs, 1.0),
      frame: this.frame,
      delay: this.delay
    };
    return new ArthurColor (ob);
  } else if (color.type == types.STRING) {
    var c = color.castTo('color');
    return this.multiply(c);
  }

  return this;
}

ArthurColor.prototype.divide = function(two) {
  if (two.type == types.COLOR) {
    var ob = {
      r: this.r.val / Math.max(two.r.val, 1),
      g: this.g.val / Math.max(two.g.val, 1),
      b: this.b.val / Math.max(two.b.val, 1),
      a: Math.max(this.a.val, two.a.val),
      frame: this.frame,
      delay: this.delay
    };
    return new ArthurColor(ob);
  } else if (two.type == types.NUMBER) {
    var abs = Math.abs(two.val);
    var ob = {
      r: this.r.val / Math.max(abs, 1),
      g: this.g.val / Math.max(abs, 1),
      b: this.b.val / Math.max(abs, 1),
      a: Math.min(this.a.val * abs, 1.0),
      frame: this.frame,
      delay: this.delay
    };
    return new ArthurColor (ob);
  } else if (color.type == types.STRING) {
    var c = color.castTo('color');
    return this.divide(c);
  }

  return this;
}

ArthurColor.prototype.valDiff = function(other) {
  if (other.type != types.COLOR)
    return new ArthurNumber(Infinity);

  var rd = Math.abs(this.r.val - other.r.val);
  var gd = Math.abs(this.g.val - other.g.val);
  var bd = Math.abs(this.b.val - other.b.val);
  var ad = Math.abs(this.a.val - other.a.val);
  return new ArthurNumber(rd + gd + bd + ad);
}

ArthurColor.prototype.castTo = function(t) {
  if (t == 'string') {
    return this.closestString();
  }

  if (t == 'num') {
    var val = 0;
    val += this.g.val;
    val += 1000 * this.b.val;
    val += 1000000 * this.r.val;
    return new ArthurNumber(val);
  }

  return this;
}

ArthurColor.prototype.closestString = function() {
  if (typeof ArthurString != 'function') {
    ArthurString = require('./arthur-string');
  }
  if (!builtins || !builtins.colorList) {
    builtins = require('./builtins');
  }

  var bestDiff = new ArthurNumber(Infinity);
  var diff;
  var currentColor, currentString;
  var bestColorString = new ArthurString("color");

  for (var i = 0; i < builtins.colorList.length; i++) {
    currentString = builtins.colorList[i];
    currentColor = builtins.colorMap[currentString];
    diff = this.valDiff(currentColor);

    if (diff.val < bestDiff.val) {
      bestDiff = diff;
      bestColorString = new ArthurString(currentString);
    }
  }

  return bestColorString;
}

ArthurColor.prototype.furthestString = function() {
  if (typeof ArthurString != 'function') {
    ArthurString = require('./arthur-string');
  }
  if (!builtins || !builtins.colorList) {
    builtins = require('./builtins');
  }

  var bestDiff = new ArthurNumber(-1 * Infinity);
  var diff;
  var currentColor, currentString;
  var bestColorString = new ArthurString("color");

  for (var i = 0; i < builtins.colorList.length; i++) {
    currentString = builtins.colorList[i];
    currentColor = builtins.colorMap[currentString];
    diff = this.valDiff(currentColor);

    if (diff.val > bestDiff.val) {
      bestDiff = diff;
      bestColorString = new ArthurString(currentString);
    }
  }

  return bestColorString;
}

},{"./arthur-frame":2,"./arthur-media":4,"./arthur-number":5,"./arthur-string":7,"./builtins":9,"./types":11}],2:[function(require,module,exports){

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

ArthurImage.prototype.add = function(two) {
  return this;
}

ArthurImage.prototype.minus = function(two) {
  return this;
}

ArthurImage.prototype.multiply = function(two) {
  return this;
}

ArthurImage.prototype.divide = function(two) {
  return this;
}

ArthurImage.prototype.dom = function() {
  var el = $('#' + this.filename);
  return el;
}

ArthurImage.prototype.arthurEquals = function(im) {
  if (im.medfile && im.medfile == this.medfile) {
    return true;
  }
  return false;
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
var ArthurColor, ArthurString;

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
  if (num.type && num.type == types.NUMBER) {
    return new ArthurNumber(this.val + num.val);
  }

  if (num.type && (num.type == types.STRING || num.type == types.COLOR)) {
    var s = num.castTo('num');
    return this.add(s);
  }

  return this;
}

ArthurNumber.prototype.minus = function(num) {
  if (num.type && num.type == types.NUMBER) {
    return new ArthurNumber(this.val - num.val);
  }

  if (num.type && (num.type == types.STRING || num.type == types.COLOR)) {
    var s = num.castTo('num');
    return this.minus(s);
  }

  return this;
}

ArthurNumber.prototype.multiply = function(num) {
  if (num.type && num.type == types.NUMBER) {
    return new ArthurNumber(this.val * num.val);
  }

  if (num.type && (num.type == types.STRING || num.type == types.COLOR)) {
    var s = num.castTo('num');
    return this.multiply(s);
  }

  return this;
}

ArthurNumber.prototype.divide = function(num) {
  if (num.type && num.type == types.NUMBER) {
    return new ArthurNumber(this.val / num.val);
  }

  if (num.type && (num.type == types.STRING || num.type == types.COLOR)) {
    var s = num.castTo('num');
    return this.divide(s);
  }

  return this;
}

ArthurNumber.prototype.castTo = function(t) {
  if (typeof ArthurColor != 'function') {
    ArthurColor = require('./arthur-color');
  }
  if (typeof ArthurString != 'function') {
    ArthurString = require('./arthur-string');
  }

  if (t == 'color') {
    var r = Math.max(this.val, 255);
    var g = Math.max(this.val, 255);
    var b = Math.max(this.val, 255);
    var a = Math.max(this.val / 255.0, 1.0);
    return new ArthurColor(r, g, b, a);
  }

  if (t == 'string') {
    return new ArthurString('' + this.val);
  }

  return this;
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

},{"./arthur-color":1,"./arthur-media":4,"./arthur-string":7,"./types":11}],6:[function(require,module,exports){

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

ArthurSound.prototype.add = function(two) {
  return this;
}

ArthurSound.prototype.minus = function(two) {
  return this;
}

ArthurSound.prototype.multiply = function(two) {
  if (two.type && two.type == types.NUMBER) {
    this.audio.playbackRate = this.audio.playbackRate * two.val;
  }

  return this;
}

ArthurSound.prototype.divide = function(two) {
  if (two.type && two.type == types.NUMBER) {
    this.audio.volume = this.audio.volume * two.val;
  }

  return this;
}

ArthurSound.prototype.arthurEquals = function(s) {
  if (s.medfile && s.medfile == this.medfile) {
    return true;
  }
  return false;
}

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
var builtins;

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
      this.tint = color;
    else
      this.tint = new ArthurColor(color.r, color.g, color.b, color.a);
  } else {
    this.tint = new ArthurColor(0, 0, 0, 1.0);
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
    this.wrap = true;
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
  s.tint = this.tint;
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
  else if (s.type == types.COLOR) {
    var closest = s.closestString();
    var res = new ArthurString(this.str + closest, true);
  }
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
  else if (s.type == types.COLOR) {
    var furthest = s.furthestString();
    var res = new ArthurString(this.str + furthest, true);
  }
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
  else if (s.type == types.COLOR)
    var st = s.castTo('string').str;
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
  else if (s.type == types.COLOR)
    var st = s.castTo('string').str;
  else
    var st = "";

  var reversed = st.split("").reverse().join("");
  var res = this.multiply(new ArthurString(reversed), true);
  this.fill(res);
  return res;
}

ArthurString.prototype.castTo = function(t) {
  if (!builtins || !builtins.colorList) {
    builtins = require('./builtins');
  }

  if (t == 'color') {
    var name = this.str;
    var upperName = this.str.toUpperCase();
    for (var i = 0; i < builtins.colorList.length; i++){
      var c = builtins.colorList[i];
      if(upperName.indexOf(c) > -1) { // contains
        return builtins.colorMap[c];
      }
    }
    return builtins.BLACK;
  }

  if (t == 'num') {
    var val = 0;
    for (var i = 0; i < this.str.length; i++) {
      val += this.str.charCodeAt(i);
    }
    return new ArthurNumber(val);
  }

  return this;
}

ArthurString.prototype.draw = function() {
  if (this.tint)
    context.fillStyle = this.tint.rgba();
  else
    context.fillStyle = 'black';

  context.textBaseline = "top";

  if (this.size)
    context.font = this.size.int() + "px monospace";
  else
    context.font = "12px monospace";

  if (!this.frame) {
    this.frame = ArthurFrame.prototype.rand();
  }

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

},{"../lib/wrap":14,"./arthur-color":1,"./arthur-frame":2,"./arthur-media":4,"./arthur-number":5,"./builtins":9,"./types":11}],8:[function(require,module,exports){

var types = require('./types');
var ArthurMedia = require('./arthur-media');
var ArthurFrame = require('./arthur-frame');
var ArthurNumber = require('./arthur-number');

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
    this.frame = new ArthurFrame(ob.frame);
  } else {
    var frame = {x: 0, y: 0, w: -1, h: -1};
    this.frame = new ArthurFrame(frame);
  }

  if (ob.delay) {
    this.delay = new ArthurNumber(ob.delay);
  }

  if (ob.murk) {
    this.murk = new ArthurNumber(ob.murk);
  } else {
    this.murk = new ArthurNumber(1.0);
  }

  this.holder = $("<div class='arthur-video-holder'>");

  var video = $('<video class="arthur-video" id="' + this.medfile + '" preload loop>');
  var dom = video.get(0);

  var source = '<source src="' + this.medfile + '" type="video/mp4">';
  video.html(source);

  this.holder.append(video);

  this.holder.css('left', this.frame.x.val + 'px');
  this.holder.css('top', this.frame.y.val + 'px');
  video.css('width', this.frame.w.int() + 'px');
  video.css('height', this.frame.h.int() + 'px');

  video.css('opacity', this.murk.val);

  this.video = dom;
}

ArthurVideo.prototype.__proto__ = ArthurMedia.prototype;

ArthurVideo.prototype.add = function(two) {
  return this;
}

ArthurVideo.prototype.minus = function(two) {
  return this;
}

ArthurVideo.prototype.multiply = function(two) {
  if (two.type && two.type == types.NUMBER) {
    this.video.playbackRate = this.video.playbackRate * two.val;
  }

  return this;
}

ArthurVideo.prototype.divide = function(two) {
  return this;
}

ArthurVideo.prototype.activate = function() {
  $('body').append(this.holder);
}

ArthurVideo.prototype.arthurEquals = function(v) {
  if (v.medfile && v.medfile == this.medfile) {
    return true;
  }
  return false;
}

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

},{"./arthur-frame":2,"./arthur-media":4,"./arthur-number":5,"./types":11}],9:[function(require,module,exports){

var ArthurNumber = require('./arthur-number');
var ArthurColor = require('./arthur-color');
var ArthurFrame = require('./arthur-frame');

var startTime = Date.now();
module.exports.start = startTime;

module.exports.ms = function () {
  return new ArthurNumber((Date.now() - startTime));
}

module.exports.cooler = function() {
  var idx = Math.floor(Math.random() * colorList.length);
  var name = colorList[idx];
  var color = colorMap[name];
  return color;
}

module.exports.rand = function() {
  return new ArthurNumber(Math.random());
}

module.exports.frame = function(x, y, w, h) {
  if (!w || !h) {
    var f = {x: x, y: y, w: -1, h: -1};
    return new ArthurFrame(f);
  } else {
    var f = {x: x, y: y, w: w, h: h};
    return new ArthurFrame(f);
  }
}

module.exports.RED = new ArthurColor(255, 0, 0, 1.0);
module.exports.WHITE = new ArthurColor(255, 255, 255, 1.0);
module.exports.BLACK = new ArthurColor(0, 0, 0, 1.0);
module.exports.BLUE = new ArthurColor(0, 0, 255, 1.0);
module.exports.GREEN = new ArthurColor(0, 255, 0, 1.0);
module.exports.ORANGE = new ArthurColor(255, 128, 0, 1.0);
module.exports.YELLOW = new ArthurColor(255, 255, 0, 1.0);
module.exports.PERRYWINKLE = new ArthurColor(204, 204, 255, 1.0);
module.exports.ARTHURS_SKIN = new ArthurColor(255, 195, 34, 1.0);
module.exports.SARCOLINE = new ArthurColor(150, 223, 167, 1.0);
module.exports.COQUELICOT = new ArthurColor(236, 75, 0, 1.0);
module.exports.SMARAGDINE = new ArthurColor(8, 152, 113, 1.0);
module.exports.ALMOND = new ArthurColor(239, 222, 205, 1.0);
module.exports.ASPARAGUS = new ArthurColor(135, 169, 107, 1.0);
module.exports.BURNT_SIENNA = new ArthurColor(234, 126, 93, 1.0);
module.exports.CERULEAN = new ArthurColor(29, 172, 214, 1.0);
module.exports.DANDELION = new ArthurColor(253, 219, 109, 1.0);
module.exports.DENIM = new ArthurColor(43, 108, 196, 1.0);
module.exports.ELECTRIC_LIME = new ArthurColor(206, 255, 29, 1.0);
module.exports.FUZZY_WUZZY = new ArthurColor(204, 102, 102, 1.0);
module.exports.GOLDENROD = new ArthurColor(252, 217, 117, 1.0);
module.exports.JAZZBERRY_JAM = new ArthurColor(202, 55, 103, 1.0);
module.exports.MAC_AND_CHEESE = new ArthurColor(255, 189, 136, 1.0);
module.exports.MAHOGANY = new ArthurColor(205, 74, 76, 1.0);
module.exports.MANGO_TANGO = new ArthurColor(255, 130, 67, 1.0);
module.exports.MAUVELOUS = new ArthurColor(239, 152, 170, 1.0);
module.exports.PURPLE_PIZZAZZ = new ArthurColor(254, 78, 218, 1.0);
module.exports.RAZZMATAZZ = new ArthurColor(227, 37, 107, 1.0);
module.exports.SALMON = new ArthurColor(255, 155, 170, 1.0);
module.exports.SILVER = new ArthurColor(205, 197, 194, 1.0);
module.exports.TICKLE_ME_PINK = new ArthurColor(252, 137, 172, 1.0);
module.exports.WILD_BLUE_YONDER = new ArthurColor(162, 173, 208, 1.0);
module.exports.WISTERIA = new ArthurColor(205, 164, 222, 1.0);
module.exports.LASER_LEMON = new ArthurColor(254, 254, 34, 1.0);
module.exports.EGGPLANT = new ArthurColor(110, 81, 96, 1.0);
module.exports.CHARTREUSE = new ArthurColor(127, 255, 0, 1.0);

var colorMap = module.exports.colorMap = {
  "RED": module.exports.RED,
  "WHITE": module.exports.WHITE,
  "BLACK": module.exports.BLACK,
  "BLUE": module.exports.BLUE,
  "GREEN": module.exports.GREEN,
  "ORANGE": module.exports.ORANGE,
  "YELLOW": module.exports.YELLOW,
  "PERRYWINKLE": module.exports.PERRYWINKLE,
  "ARTHURS_SKIN": module.exports.ARTHURS_SKIN,
  "SARCOLINE": module.exports.SARCOLINE,
  "COQUELICOT": module.exports.COQUELICOT,
  "SMARAGDINE": module.exports.SMARAGDINE,
  "ALMOND": module.exports.ALMOND,
  "ASPARAGUS": module.exports.ASPARAGUS,
  "BURNT_SIENNA": module.exports.BURNT_SIENNA,
  "CERULEAN": module.exports.CERULEAN,
  "DANDELION": module.exports.DANDELION,
  "DENIM": module.exports.DENIM,
  "ELECTRIC_LIME": module.exports.ELECTRIC_LIME,
  "FUZZY_WUZZY": module.exports.FUZZY_WUZZY,
  "GOLDENROD": module.exports.GOLDENROD,
  "JAZZBERRY_JAM": module.exports.JAZZBERRY_JAM,
  "MAC_AND_CHEESE": module.exports.MAC_AND_CHEESE,
  "MAHOGANY": module.exports.MAHOGANY,
  "MANGO_TANGO": module.exports.MANGO_TANGO,
  "MAUVELOUS": module.exports.MAUVELOUS,
  "PURPLE_PIZZAZZ": module.exports.PURPLE_PIZZAZZ,
  "RAZZMATAZZ": module.exports.RAZZMATAZZ,
  "SALMON": module.exports.SALMON,
  "SILVER": module.exports.SILVER,
  "TICKLE_ME_PINK": module.exports.TICKLE_ME_PINK,
  "WILD_BLUE_YONDER": module.exports.WILD_BLUE_YONDER,
  "WISTERIA": module.exports.WISTERIA,
  "LASER_LEMON": module.exports.LASER_LEMON,
  "EGGPLANT": module.exports.EGGPLANT,
  "CHARTREUSE": module.exports.CHARTREUSE,
};

var colorList = module.exports.colorList = [
  "RED",
  "WHITE",
  "BLACK",
  "BLUE",
  "GREEN",
  "ORANGE",
  "YELLOW",
  "PERRYWINKLE",
  "ARTHURS_SKIN",
  "SARCOLINE",
  "COQUELICOT",
  "SMARAGDINE",
  "ALMOND",
  "ASPARAGUS",
  "BURNT_SIENNA",
  "CERULEAN",
  "DANDELION",
  "DENIM",
  "ELECTRIC_LIME",
  "FUZZY_WUZZY",
  "GOLDENROD",
  "JAZZBERRY_JAM",
  "MAC_AND_CHEESE",
  "MAHOGANY",
  "MANGO_TANGO",
  "MAUVELOUS",
  "PURPLE_PIZZAZZ",
  "RAZZMATAZZ",
  "SALMON",
  "SILVER",
  "TICKLE_ME_PINK",
  "WILD_BLUE_YONDER",
  "WISTERIA",
  "LASER_LEMON",
  "EGGPLANT",
  "CHARTREUSE",
];

},{"./arthur-color":1,"./arthur-frame":2,"./arthur-number":5}],10:[function(require,module,exports){

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
var ArthurFrame = module.exports.ArthurFrame = require('./arthur-frame');

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
    if (med.activate)
      med.activate();
    activeMedia.push(med);
  } else {
    setTimeout(function() {
      med.active = true;
      if (med.activate)
        med.activate();
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

module.exports.add = function(media, frame, delay) {
  if (frame && frame.val && !delay) { // its actually delay
    media.delay = delay;
  } else {
    media.frame = frame;
  }

  if (delay) {
    media.delay = delay;
  }

  makeActive(media);
}

},{"../lib/anim-shim":13,"./arthur-color":1,"./arthur-frame":2,"./arthur-image":3,"./arthur-media":4,"./arthur-number":5,"./arthur-sound":6,"./arthur-string":7,"./arthur-video":8,"./builtins":9,"./types":11}],11:[function(require,module,exports){

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
var rand = arthur.builtins.rand;
var cooler = arthur.builtins.cooler;
var frame = arthur.builtins.frame;
var literalWrapper = arthur.literalWrapper;

var RED = arthur.builtins.RED;
var WHITE = arthur.builtins.WHITE;
var BLACK = arthur.builtins.BLACK;
var BLUE = arthur.builtins.BLUE;
var GREEN = arthur.builtins.GREEN;
var ORANGE = arthur.builtins.ORANGE;
var YELLOW = arthur.builtins.YELLOW;
var PERRYWINKLE = arthur.builtins.PERRYWINKLE;
var ARTHURS_SKIN = arthur.builtins.ARTHURS_SKIN;
var SARCOLINE = arthur.builtins.SARCOLINE;
var COQUELICOT = arthur.builtins.COQUELICOT;
var SMARAGDINE = arthur.builtins.SMARAGDINE;
var ALMOND = arthur.builtins.ALMOND;
var ASPARAGUS = arthur.builtins.ASPARAGUS;
var BURNT_SIENNA = arthur.builtins.BURNT_SIENNA;
var CERULEAN = arthur.builtins.CERULEAN;
var DANDELION = arthur.builtins.DANDELION;
var DENIM = arthur.builtins.DENIM;
var ELECTRIC_LIME = arthur.builtins.ELECTRIC_LIME;
var FUZZY_WUZZY = arthur.builtins.FUZZY_WUZZY;
var GOLDENROD = arthur.builtins.GOLDENROD;
var JAZZBERRY_JAM = arthur.builtins.JAZZBERRY_JAM;
var MAC_AND_CHEESE = arthur.builtins.MAC_AND_CHEESE;
var MAHOGANY = arthur.builtins.MAHOGANY;
var MANGO_TANGO = arthur.builtins.MANGO_TANGO;
var MAUVELOUS = arthur.builtins.MAUVELOUS;
var PURPLE_PIZZAZZ = arthur.builtins.PURPLE_PIZZAZZ;
var RAZZMATAZZ = arthur.builtins.RAZZMATAZZ;
var SALMON = arthur.builtins.SALMON;
var SILVER = arthur.builtins.SILVER;
var TICKLE_ME_PINK = arthur.builtins.TICKLE_ME_PINK;
var WILD_BLUE_YONDER = arthur.builtins.WILD_BLUE_YONDER;
var WISTERIA = arthur.builtins.WISTERIA;
var LASER_LEMON = arthur.builtins.LASER_LEMON;
var EGGPLANT = arthur.builtins.EGGPLANT;
var CHARTREUSE = arthur.builtins.CHARTREUSE;

var updateMedia = arthur.updateMedia;
var hasLoop = typeof loop != 'undefined';
function looper() { requestAnimationFrame(looper); if (hasLoop) loop(); updateMedia(); }
if (typeof key != 'undefined') $(document).keypress(function(ev) {key(new ArthurString(String.fromCharCode(ev.which))); } );
if (typeof click != 'undefined') $(document).click(function(e) { click(new ArthurNumber(e.clientX), new ArthurNumber(e.clientY)); });
if (typeof move != 'undefined') $(document).mousemove(function(e) { move(new ArthurNumber(e.clientX), new ArthurNumber(e.clientY)); });

var fut = literalWrapper(new ArthurSound({"filename": "media/fut__sound__.mp3"}), 'media/fut__sound__.mp3');
var lyric = literalWrapper(new ArthurString('{"str": "And we ain\\u0027t never going back to what we used to do I was gon\\u0027 lie to you but I had to tell the truth I\\u0027m just being honest I\\u0027M JUST BEING HONEST I\\u0027M JUST BEING HONEST", "size": "50.0", "frame": {"x": 10.0, "y": 300.0, "w": -1.0, "h": -1.0}}'), 'media/lyric__string__.json');
var ure = literalWrapper(new ArthurSound({"filename": "media/ure__sound__.mp3"}), 'media/ure__sound__.mp3');
addArthurSound('{"filename": "media/fut__sound__.mp3"}', 'media/fut__sound__.mp3')
addArthurSound('{"filename": "media/ure__sound__.mp3"}', 'media/ure__sound__.mp3')
addArthurString('{"str": "And we ain\\u0027t never going back to what we used to do I was gon\\u0027 lie to you but I had to tell the truth I\\u0027m just being honest I\\u0027M JUST BEING HONEST I\\u0027M JUST BEING HONEST", "size": "50.0", "frame": {"x": 10.0, "y": 300.0, "w": -1.0, "h": -1.0}}', 'media/lyric__string__.json')



function init() {
 fut.set(sound(new ArthurString("honest.mp3")));
 lyric.set(new ArthurString("And we ain't never going back to what we used to do I was gon' lie to you but I had to tell the truth I'm just being honest I'M JUST BEING HONEST I'M JUST BEING HONEST"));
 lyric.size.set(new ArthurNumber(50.0));
 ure.set(lyric.castTo("Sound"));
 ure.set( ure.add(new ArthurNumber(4.0)));
 ure.set( ure.divide(new ArthurNumber(10.0)));
add( fut);
add( ure);
add( lyric, frame(new ArthurNumber(10.0), new ArthurNumber(300.0)));

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