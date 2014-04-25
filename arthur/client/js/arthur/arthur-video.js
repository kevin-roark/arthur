
var types = require('./types');
var ArthurMedia = require('./arthur-media');
var ArthurFrame = require('./arthur-frame');
var ArthurNumber = require('./arthur-number');

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
    this.frame = new ArthurFrame(ob.frame);
  } else {
    var frame = {x: 0, y: 0, w: -1, h: -1};
    this.frame = new ArthurFrame(frame);
  }

  if (ob.delay) {
    this.delay = new ArthurNumber(ob.delay);
  }

  if (ob.murk) {
    this.murk = new ArthurNumber(ob.murk);
  } else {
    this.murk = new ArthurNumber(1.0);
  }

  this.holder = $("<div class='arthur-video-holder'>");

  var video = $('<video class="arthur-video" id="' + this.medfile + '" preload loop>');
  var dom = video.get(0);

  var source = '<source src="' + this.medfile + '" type="video/mp4">';
  video.html(source);

  this.holder.append(video);

  this.holder.css('left', this.frame.x.val + 'px');
  this.holder.css('top', this.frame.y.val + 'px');
  video.css('width', this.frame.w.int() + 'px');
  video.css('height', this.frame.h.int() + 'px');

  video.css('opacity', this.murk.val);

  this.video = dom;
}

ArthurVideo.prototype.__proto__ = ArthurMedia.prototype;

ArthurVideo.prototype.add = function(two) {
  return this;
}

ArthurVideo.prototype.minus = function(two) {
  return this;
}

ArthurVideo.prototype.multiply = function(two) {
  return this;
}

ArthurVideo.prototype.divide = function(two) {
  return this;
}

ArthurVideo.prototype.activate = function() {
  $('body').append(this.holder);
}

ArthurVideo.prototype.arthurEquals = function(v) {
  if (v.medfile && v.medfile == this.medfile) {
    return true;
  }
  return false;
}

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
