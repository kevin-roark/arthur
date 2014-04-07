
var ArthurNumber = require('./arthur-number');

module.exports = ArthurFrame;

function ArthurFrame(frameob) {
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
