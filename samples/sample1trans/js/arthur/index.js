
// get window.requestAnimationFrame looking good
require('../lib/anim-shim');

// all the nice types
var ArthurMedia = module.exports.ArthurMedia = require('./arthur-media');
var ArthurColor = module.exports.ArthurColor = require('./arthur-color');
var ArthurNumber = module.exports.ArthurNumber = require('./arthur-number');
var ArthurString = module.exports.ArthurString = require('./arthur-string');
var ArthurImage = module.exports.ArthurImage = require('./arthur-image');
var ArthurSound = module.exports.ArthurSound = require('./arthur-sound');
var ArthurVideo = module.exports.ArthurVideo = require('./arthur-video');

// builtin functions like ms
var builtins = module.exports.builtins = require('./builtins');

var canvas = document.getElementById('canvas');
var context = canvas.getContext('2d');
module.exports.canvas = canvas;

var globals = [];
var globalMap = {};
var activeMedia = [];

module.exports.literalWrapper = function(media, filename) {
  media.glob = true;
  if (filename) {
    media.medfile = filename;
    globalMap[filename] = media;
  }
  globals.push(media);
  return media;
}

function resize() {
  canvas.width = $(window).width();
  canvas.height = $(window).height();
}
$(window).resize(resize);
resize();

function clearCanvas() {
  context.clearRect(0, 0, canvas.width, canvas.height);
}

module.exports.updateMedia = function() {
  clearCanvas();
  for (var i = 0; i < activeMedia.length; i++) {
    var media = activeMedia[i];
    if (media.active)
      media.draw();
  }
}

module.exports.addArthurColor = function(filename, frame) {
  var med = globalMap[filename];
  if (med) {
    med.active = true;
    activeMedia.push(med);
    return;
  }

  $.ajax({
    url: filename,
    success: function(cj) {
      var color = new ArthurColor(cj.r, cj.g, cj.b, cj.a, frame);
      color.active = true;
      activeMedia.push(color);
    }
  });
}

module.exports.addArthurNumber = function(filename, frame) {

}

module.exports.addArthurString = function(filename, frame) {

}

module.exports.addArthurImage = function(filename, frame) {
  var med = globalMap[filename];
  if (med) {
    med.active = true;
    activeMedia.push(med);
    return;
  }

  var ai = new ArthurImage(filename, frame);
  ai.active = true;
  activeMedia.push(ai);
}

module.exports.addArthurSound = function(filename, frame) {

}

module.exports.addArthurVideo = function(filename, frame) {

}

module.exports.add = function(media, frame) {
  // implement later plz vry cool
}
