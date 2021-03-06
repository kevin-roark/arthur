
var types = require('./types');
//var builtins = require('./builtins');
var ArthurNumber = require('./arthur-number');
var ArthurMedia = require('./arthur-media');
var ArthurFrame = require('./arthur-frame');

var canvas = document.getElementById('canvas');
var context = canvas.getContext('2d');

module.exports = ArthurColor;

console.log(module.exports);

ArthurColor.prototype.__proto__ = ArthurMedia.prototype;

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
  if (!other.type || other.type != types.COLOR)
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
