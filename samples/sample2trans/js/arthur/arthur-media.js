
module.exports = ArthurMedia;

function ArthurMedia() {
  this.type = -1;
  this.medfile = null;
  this.glob = false;
  this.active = false;
  this.aid = -1;
}

ArthurMedia.prototype.set = function(media) {
  var g = this.glob;
  var a = this.active;
  for (var key in media) {
    this[key] = media[key];
  }
  this.glob = g;
  this.active = a;
  return this;
}
