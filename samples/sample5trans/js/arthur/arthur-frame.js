
var ArthurNumber = require('./arthur-number');
var types = require('./types');

module.exports = ArthurFrame;

function ArthurFrame(frameob) {
  this.type = types.FRAME;
  this.x = new ArthurNumber(frameob.x);
  this.y = new ArthurNumber(frameob.y);
  if (frameob.w < 0) {
    this.w = new ArthurNumber($(window).width());
  } else {
    this.w = new ArthurNumber(frameob.w);
  }
  if (frameob.h < 0) {
    this.h = new ArthurNumber($(window).height());
  } else {
    this.h = new ArthurNumber(frameob.h);
  }
}

ArthurFrame.prototype.rand = function() {
  var f = {
    x: Math.floor(Math.random() * $(window).width()),
    y: Math.floor(Math.random() * $(window).height()),
    w: Math.floor(Math.random() * $(window).width()),
    h: Math.floor(Math.random() * $(window).height()),
  };
  return new ArthurFrame(f);
}
