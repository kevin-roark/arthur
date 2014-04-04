
var types = require('./types');
var ArthurMedia = require('./arthur-media');

module.exports = ArthurSound;

function ArthurSound() {
  this.type = types.SOUND;
}

ArthurSound.prototype.__proto__ = ArthurMedia.prototype;
