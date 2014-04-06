
var types = require('./types');
var ArthurMedia = require('./arthur-media');
var ArthurFrame = require('./arthur-frame');

var canvas = document.getElementById('canvas');
var context = canvas.getContext('2d');

module.exports = ArthurImage;

function ArthurImage(json) {
  var ob = JSON.parse(json);

  this.type = types.IMAGE;
  this.medfile = ob.filename;
  if (ob.frame) {
    this.frame = new ArthurFrame(ob.frame);
  }

  var img = $('<img class="arthur-image" id="' + this.medfile + '">');
  img.attr('src', this.medfile);
  this.img = img;

  var dom = this.img.get(0);
  this.width = dom.naturalWidth;
  this.height = dom.naturalHeight;

  console.log(this);
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
    context.drawImage(this.img.get(0), this.frame.x, this.frame.y, this.frame.w, this.frame.h);
  } else {
    context.drawImage(this.img.get(0), 0, 0);
  }
}
