
var types = require('./types');
var ArthurMedia = require('./arthur-media');
var ArthurFrame = require('./arthur-frame');
var ArthurNumber = require('./arthur-number');

var canvas = document.getElementById('canvas');
var context = canvas.getContext('2d');

module.exports = ArthurImage;

function ArthurImage(json) {
  var ob;
  if (typeof json == 'string')
    ob = JSON.parse(json);
  else
    ob = json;

  this.type = types.IMAGE;
  this.medfile = ob.filename;
  if (ob.frame) {
    this.frame = new ArthurFrame(ob.frame);
  }
  if (ob.murk) {
    this.murk = new ArthurNumber(ob.murk);
  }
  if (ob.delay) {
    this.delay = new ArthurNumber(ob.delay);
  }

  var img = $('<img class="arthur-image" id="' + this.medfile + '">');
  img.attr('src', this.medfile);
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

  if (this.murk) {
    context.globalAlpha = this.murk.val;
  }

  if (this.frame) {
    context.drawImage(this.img.get(0), this.frame.x.int(), this.frame.y.int(), this.frame.w.int(), this.frame.h.int());
  } else {
    context.drawImage(this.img.get(0), 0, 0);
  }
  context.globalAlpha = 1.0;
}
