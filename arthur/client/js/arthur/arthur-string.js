
var types = require('./types');

module.exports = ArthurString;

function ArthurString(text) {
  this.type = types.STRING;
  this.str = text;
}

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
    longer = one.str;
    shorter = two.str;
  } else {
    longer = two.str;
    shorter = one.str;
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
  for (int i = 0; i < shorter.length(); i++) {
    var avg = (longer.charCodeAt(i) + shorter.charCodeAt(i)) / 2;
    product += String.fromCharCode(avg);
  }

  return new ArthurString(product);
}

ArthurString.prototype.divide = function(s) {
  var reversed = s.str.split("").reverse().join("");
  return this.multiply(new ArthurString(reversed));
}
