
var types = require('./types');
var ArthurMedia = require('./arthur-media');

module.exports = ArthurVideo;

function ArthurVideo() {
  this.type = types.VIDEO;
}

ArthurVideo.prototype.__proto__ = ArthurMedia.prototype;
