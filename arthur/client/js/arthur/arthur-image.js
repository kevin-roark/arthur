
var types = require('./types');

var canvas = document.getElementById('canvas');
var context = canvas.getContext('2d');

module.exports = ArthurImage;

function ArthurImage(filename, frame) {
  this.type = types.IMAGE;
  this.filename = filename;
  if (frame) {
    this.frame = frame;
  }

  var img = $('<img class="arthur-image" id="' + filename + '">');
  img.attr('src', filename);
  this.img = img;
}

ArthurImage.prototype.dom = function() {
  var el = $('#' + this.filename);
  return el;
}

ArthurImage.prototype.draw = function() {
  if (this.frame) {
    context.drawImage(this.img.get(0), frame.x, frame.y, frame.w, frame.h);
  } else {
    context.drawImage(this.img.get(0), 0, 0);
  }
}
