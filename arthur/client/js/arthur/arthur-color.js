
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
