
module.exports.ArthurColor = require('./arthur-color');
module.exports.ArthurNumber = require('./arthur-number');
module.exports.ArthurString = require('./arthur-string');
module.exports.ArthurImage = require('./arthur-image');
module.exports.ArthurSound = require('./arthur-sound');
module.exports.ArthurVideo = require('./arthur-video');

var canvas = document.getElementById('canvas');
var context = canvas.getContext('2d');
module.exports.canvas = canvas;

function resize() = {
  canvas.width = $(window).width();
  canvas.height = $(window).height();
}
$(window).resize(resize);
resize();

module.exports.addArthurColor = function(filename, frame) {

}

module.exports.addArthurNumber = function(filename, frame) {

}

module.exports.addArthurString = function(filename, frame) {

}

module.exports.addArthurImage = function(filename, frame) {
  return new ArthurImage(filename, frame);
}

module.exports.addArthurSound = function(filename, frame) {

}

module.exports.addArthurVideo = function(filename, frame) {

}
