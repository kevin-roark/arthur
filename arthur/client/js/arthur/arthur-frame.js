
module.exports = ArthurFrame;

function ArthurFrame(frameob) {
  this.x = frameob.x;
  this.y = frameob.y;
  if (frameob.w < 0) {
    this.w = $(window).width();
  } else {
    this.w = frameob.w;
  }
  if (frameob.h < 0) {
    this.h = $(window).height();
  } else {
    this.h = frameob.h;
  }
}
