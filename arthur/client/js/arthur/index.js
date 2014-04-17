
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

var types = require('./types');

var canvas = document.getElementById('canvas');
var context = canvas.getContext('2d');
module.exports.canvas = canvas;

var globals = [];
var globalMap = {};
var activeMedia = [];

function checkGlobal(filename) {
  if (filename) {
    var med = globalMap[filename];
    if (med) {
      med.active = true;
      activeMedia.push(med);
      return true;
    }
  }
  return false;
}

module.exports.literalWrapper = function(media, filename) {
  if (media instanceof Boolean) return media;

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

module.exports.addArthurColor = function(json, filename) {
  var global = checkGlobal(filename);
  if (global) return;

  var ob = JSON.parse(json);

  var color = new ArthurColor(ob.r, ob.g, ob.b, ob.a, ob.frame);
  color.active = true;
  activeMedia.push(color);
}

module.exports.addArthurNumber = function(json) {

}

module.exports.addArthurString = function(json, filename) {
  var global = checkGlobal(filename);
  if (global) return;

  var str = new ArthurString(json);
  str.active = true;
  activeMedia.push(str);
}

module.exports.addArthurImage = function(json, filename) {
  var global = checkGlobal(filename);
  if (global) return;

  var ai = new ArthurImage(json);
  ai.active = true;
  activeMedia.push(ai);
}

module.exports.addArthurSound = function(json, filename) {
  var global = checkGlobal(filename);
  if (global) return;

  var as = new ArthurSound(json);
  as.active = true;
  activeMedia.push(as);
}

module.exports.addArthurVideo = function(json, filename) {

}

module.exports.add = function(media, frame) {
  // finish later plz vry cool
  media.active = true;
  activeMedia.push(media);
}
