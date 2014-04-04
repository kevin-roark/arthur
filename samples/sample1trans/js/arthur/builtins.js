
var ArthurNumber = require('./arthur-number');

var startTime = Date.now();
module.exports.start = startTime;

module.exports.ms = function () {
  return new ArthurNumber((Date.now() - startTime));
}
