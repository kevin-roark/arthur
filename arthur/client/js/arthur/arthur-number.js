
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
  return casting.castNum(this, t);
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
