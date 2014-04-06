
var types = require('./types');
var ArthurMedia = require('./arthur-media');

var canvas = document.getElementById('canvas');
var context = canvas.getContext('2d');

module.exports = ArthurImage;

function ArthurImage(filename, frame) {
  this.type = types.IMAGE;
  this.medfile = filename;
  if (frame) {
    this.frame = frame;
  }

  var img = $('<img class="arthur-image" id="' + filename + '">');
  img.attr('src', filename);
  this.img = img;

  var dom = this.img.get(0);
  this.width = dom.naturalWidth;
  this.height = dom.naturalHeight;
}

ArthurImage.prototype.__proto__ = ArthurMedia.prototype;

ArthurImage.prototype.dom = function() {
  var el = $('#' + this.filename);
  return el;
}

ArthurImage.prototype.draw = function() {
  if (!this.width) {
    var dom = this.img.get(0);
    this.width = dom.naturalWidth;
    this.height = dom.naturalHeight;
  }

  if (this.frame) {
    context.drawImage(this.img.get(0), frame.x, frame.y, frame.w, frame.h);
  } else {
    context.drawImage(this.img.get(0), 0, 0, this.width, this.height);
  }
}
