
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
