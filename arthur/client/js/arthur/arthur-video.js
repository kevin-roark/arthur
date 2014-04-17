
var types = require('./types');
var ArthurMedia = require('./arthur-media');
var ArthurFrame = require('./arthur-frame');

module.exports = ArthurVideo;

function ArthurVideo(json) {
  var ob;
  if (typeof json == 'string')
    ob = JSON.parse(json);
  else
    ob = json;

  this.type = types.VIDEO;

  this.medfile = ob.filename;

  if (ob.frame) {
    this.frame = ob.frame;
  } else {
    this.frame = new ArthurFrame(0, 0, -1, -1);
  }

  var video = $('<video class="arthur-video" id="' + this.medfile + '" preload loop>');
  var dom = video.get(0);

  var source = $('<source src="' + this.medfile + '" type="video/mp4">');
  video.append(source);

  $('body').append(video);
  this.video = dom;
}

ArthurVideo.prototype.__proto__ = ArthurMedia.prototype;

ArthurVideo.prototype.setFrame = function() {
  var v = $(this.video);
  v.css('left', this.frame.x.int() + 'px');
  v.css('top', this.frame.y.int() + 'px');
  v.css('width', this.frame.w.int() + 'px');
  v.css('height', this.frame.h.int() + 'px');
}

ArthurVideo.prototype.draw = function() {
  if (this.video.paused)
    this.play();
}

ArthurVideo.prototype.play = function() {
  this.video.play();
}

ArthurVideo.prototype.pause = function() {
  this.video.pause();
}
