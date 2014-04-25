
var ArthurColor = require('./arthur-color');
var ArthurNumber = require('./arthur-number');
var ArthurString = require('./arthur-string');

module.exports.castString = function(s, t) {
  if (t == 'color') {
    var name = s.str;
    var upperName = s.str.toUpperCase();
    for (var i = 0; i < builtins.colorList.length; i++){
      var c = builtins.colorList[i];
      if(upperName.indexOf(c) > -1) { // contains
        return builtins.colorMap[c];
      }
    }
    return builtins.BLACK;
  }

  if (t == 'num') {
    var val = 0;
    for (var i = 0; i < s.str.length; i++) {
      val += s.str.charCodeAt(i);
    }
    return new ArthurNumber(val);
  }

  return s;
}


module.exports.castNum = function(num, t) {
  if (t == 'color') {
    var r = Math.max(num.val, 255);
    var g = Math.max(num.val, 255);
    var b = Math.max(num.val, 255);
    var a = Math.max(num.val / 255.0, 1.0);
    return new ArthurColor(r, g, b, a);
  }

  if (t == 'string') {
    return new ArthurString('' + num.val);
  }

  return num;
}

module.exports.castColor = function(col, t) {
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
