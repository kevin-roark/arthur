
var ArthurNumber = require('./arthur-number');
var ArthurColor = require('./arthur-color');
var ArthurFrame = require('./arthur-frame');

var startTime = Date.now();
module.exports.start = startTime;

module.exports.ms = function () {
  return new ArthurNumber((Date.now() - startTime));
}

module.exports.cooler = function() {
  var r = Math.floor(Math.random() * 255);
  var g = Math.floor(Math.random() * 255);
  var b = Math.floor(Math.random() * 255);
  var ob = {r: r, g: g, b: b, a: 1.0};
  return new ArthurColor(ob);
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
