
// get window.requestAnimationFrame looking good
require('../lib/anim-shim');

// all the nice types
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

var activeMedia = [];

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
    media.draw();
  }
}

module.exports.addArthurColor = function(filename, frame) {
  $.ajax({
    url: filename,
    success: function(cj) {
      var color = new ArthurColor(cj.r, cj.g, cj.b, cj.a, frame);
      activeMedia.push(color);
    }
  });
}

module.exports.addArthurNumber = function(filename, frame) {

}

module.exports.addArthurString = function(filename, frame) {

}

module.exports.addArthurImage = function(filename, frame) {
  var ai = new ArthurImage(filename, frame);
  activeMedia.push(ai);
}

module.exports.addArthurSound = function(filename, frame) {

}

module.exports.addArthurVideo = function(filename, frame) {

}
