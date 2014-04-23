
var ArthurNumber = require('./arthur-number');
var ArthurColor = require('./arthur-color');

var startTime = Date.now();
module.exports.start = startTime;

module.exports.ms = function () {
  return new ArthurNumber((Date.now() - startTime));
}

module.exports.cooler = function() {
  var r = Math.floor(Math.random() * 255);
  var g = Math.floor(Math.random() * 255);
  var b = Math.floor(Math.random() * 255);
  return new ArthurColor(r, g, b, 1.0);
}

module.exports.rand = function() {
  return new ArthurNumber(Math.random());
}
