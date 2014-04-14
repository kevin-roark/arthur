
var types = require('./types');
var ArthurMedia = require('./arthur-media');
var ArthurColor = require('./arthur-color');
var ArthurFrame = require('./arthur-frame');
var ArthurNumber = require('./arthur-number');

var canvas = document.getElementById('canvas');
var context = canvas.getContext('2d');

module.exports = ArthurString;

function ArthurString(json) {
  if (typeof json == 'string') {
    if (json.substring(0, 1) == '{') {
      var ob = JSON.parse(json);
    } else {
      var ob = {str: json};
    }
    return new ArthurString(ob);
  }

  this.type = types.STRING;

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
    longer = this.str;
    shorter = s.str;
  } else {
    longer = s.str;
    shorter = this.str;
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
  for (var i = 0; i < shorter.length; i++) {
    var avg = (longer.charCodeAt(i) + shorter.charCodeAt(i)) / 2;
    product += String.fromCharCode(avg);
  }

  return new ArthurString(product);
}

ArthurString.prototype.divide = function(s) {
  var reversed = s.str.split("").reverse().join("");
  return this.multiply(new ArthurString(reversed));
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
  drawMultiline(this.str, this.frame.x.int(), this.frame.y.int())
}

// help for multiline taken from
// http://stackoverflow.com/questions/5026961/html5-canvas-ctx-filltext-wont-do-line-breaks

function drawMultiline(text, x, y){
    var textvalArr = toMultiLine(text);
    var linespacing = 14;

    // draw each line on canvas.
    for(var i = 0; i < textvalArr.length; i++){
        context.fillText(textvalArr[i], x, y);
        y += linespacing;
    }
}

function toMultiLine(text){
   var textArr = new Array();
   text = text.replace(/\n\r?/g, '<br/>');
   textArr = text.split("<br/>");
   return textArr;
}
