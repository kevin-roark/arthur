
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
