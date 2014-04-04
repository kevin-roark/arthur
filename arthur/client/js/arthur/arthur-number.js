
var types = require('./types');

module.exports = ArthurNumber;

function ArthurNumber(num) {
  this.type = types.NUMBER;
  this.val = num;
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
