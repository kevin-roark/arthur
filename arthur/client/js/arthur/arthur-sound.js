
var types = require('./types');
var ArthurMedia = require('./arthur-media');
var ArthurNumber = require('./arthur-number');

module.exports = ArthurSound;

function ArthurSound(json) {
  var ob;
  if (typeof json == 'string')
    ob = JSON.parse(json);
  else
    ob = json;

  this.type = types.SOUND;
  this.medfile = ob.filename;

  if (ob.delay) {
    this.delay = new ArthurNumber(ob.delay);
  }

  var audio = $('<audio class="arthur-audio" id="' + this.medfile + '" preload loop>');
  var dom = audio.get(0);

  var source = $('<source src="' + this.medfile + '" type="audio/mpeg">');
  audio.append(source);

  $('body').append(audio);
  this.audio = dom;
}

ArthurSound.prototype.__proto__ = ArthurMedia.prototype;

ArthurSound.prototype.draw = function() {
  if (this.audio.paused)
    this.play();
}

ArthurSound.prototype.play = function() {
  this.audio.play();
}

ArthurSound.prototype.pause = function() {
  this.audio.pause();
}
