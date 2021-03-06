
var types = require('./types');
//var builtins = require('./builtins');
var ArthurMedia = require('./arthur-media');
var ArthurColor = require('./arthur-color');
var ArthurFrame = require('./arthur-frame');
var ArthurNumber = require('./arthur-number');

var canwrap = require('../lib/wrap');

var canvas = document.getElementById('canvas');
var context = canvas.getContext('2d');

module.exports = ArthurString;

console.log(module.exports);

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
