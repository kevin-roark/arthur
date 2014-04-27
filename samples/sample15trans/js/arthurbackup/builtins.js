
var ArthurNumber = require('./arthur-number');
var ArthurColor = require('./arthur-color');
var ArthurFrame = require('./arthur-frame');

var startTime = Date.now();
module.exports.start = startTime;

module.exports.ms = function () {
  return new ArthurNumber((Date.now() - startTime));
}

module.exports.cooler = function() {
  var r = Math.floor(Math.random() * 255);
  var g = Math.floor(Math.random() * 255);
  var b = Math.floor(Math.random() * 255);
  var ob = {r: r, g: g, b: b, a: 1.0};
  return new ArthurColor(ob);
}

module.exports.rand = function() {
  return new ArthurNumber(Math.random());
}

module.exports.frame = function(x, y, w, h) {
  if (!w || !h) {
    var f = {x: x, y: y, w: -1, h: -1};
    return new ArthurFrame(f);
  } else {
    var f = {x: x, y: y, w: w, h: h};
    return new ArthurFrame(f);
  }
}

/*
module.exports.RED = new ArthurColor(255, 0, 0, 1.0);
module.exports.WHITE = new ArthurColor(255, 255, 255, 1.0);
module.exports.BLACK = new ArthurColor(0, 0, 0, 1.0);
module.exports.BLUE = new ArthurColor(0, 0, 255, 1.0);
module.exports.GREEN = new ArthurColor(0, 255, 0, 1.0);
module.exports.ORANGE = new ArthurColor(255, 128, 0, 1.0);
module.exports.YELLOW = new ArthurColor(255, 255, 0, 1.0);
module.exports.PERRYWINKLE = new ArthurColor(204, 204, 255, 1.0);
module.exports.ARTHURS_SKIN = new ArthurColor(255, 195, 34, 1.0);
module.exports.SARCOLINE = new ArthurColor(150, 223, 167, 1.0);
module.exports.COQUELICOT = new ArthurColor(236, 75, 0, 1.0);
module.exports.SMARAGDINE = new ArthurColor(8, 152, 113, 1.0);
module.exports.ALMOND = new ArthurColor(239, 222, 205, 1.0);
module.exports.ASPARAGUS = new ArthurColor(135, 169, 107, 1.0);
module.exports.BURNT_SIENNA = new ArthurColor(234, 126, 93, 1.0);
module.exports.CERULEAN = new ArthurColor(29, 172, 214, 1.0);
module.exports.DANDELION = new ArthurColor(253, 219, 109, 1.0);
module.exports.DENIM = new ArthurColor(43, 108, 196, 1.0);
module.exports.ELECTRIC_LIME = new ArthurColor(206, 255, 29, 1.0);
module.exports.FUZZY_WUZZY = new ArthurColor(204, 102, 102, 1.0);
module.exports.GOLDENROD = new ArthurColor(252, 217, 117, 1.0);
module.exports.JAZZBERRY_JAM = new ArthurColor(202, 55, 103, 1.0);
module.exports.MAC_AND_CHEESE = new ArthurColor(255, 189, 136, 1.0);
module.exports.MAHOGANY = new ArthurColor(205, 74, 76, 1.0);
module.exports.MANGO_TANGO = new ArthurColor(255, 130, 67, 1.0);
module.exports.MAUVELOUS = new ArthurColor(239, 152, 170, 1.0);
module.exports.PURPLE_PIZZAZZ = new ArthurColor(254, 78, 218, 1.0);
module.exports.RAZZMATAZZ = new ArthurColor(227, 37, 107, 1.0);
module.exports.SALMON = new ArthurColor(255, 155, 170, 1.0);
module.exports.SILVER = new ArthurColor(205, 197, 194, 1.0);
module.exports.TICKLE_ME_PINK = new ArthurColor(252, 137, 172, 1.0);
module.exports.WILD_BLUE_YONDER = new ArthurColor(162, 173, 208, 1.0);
module.exports.WISTERIA = new ArthurColor(205, 164, 222, 1.0);
module.exports.LASER_LEMON = new ArthurColor(254, 254, 34, 1.0);
module.exports.EGGPLANT = new ArthurColor(110, 81, 96, 1.0);
module.exports.CHARTREUSE = new ArthurColor(127, 255, 0, 1.0);

var colorMap = module.exports.colorMap = {
  "RED": module.exports.RED,
  "WHITE": module.exports.WHITE,
  "BLACK": module.exports.BLACK,
  "BLUE": module.exports.BLUE,
  "GREEN": module.exports.GREEN,
  "ORANGE": module.exports.ORANGE,
  "YELLOW": module.exports.YELLOW,
  "PERRYWINKLE": module.exports.PERRYWINKLE,
  "ARTHURS_SKIN": module.exports.ARTHURS_SKIN,
  "SARCOLINE": module.exports.SARCOLINE,
  "COQUELICOT": module.exports.COQUELICOT,
  "SMARAGDINE": module.exports.SMARAGDINE,
  "ALMOND": module.exports.ALMOND,
  "ASPARAGUS": module.exports.ASPARAGUS,
  "BURNT_SIENNA": module.exports.BURNT_SIENNA,
  "CERULEAN": module.exports.CERULEAN,
  "DANDELION": module.exports.DANDELION,
  "DENIM": module.exports.DENIM,
  "ELECTRIC_LIME": module.exports.ELECTRIC_LIME,
  "FUZZY_WUZZY": module.exports.FUZZY_WUZZY,
  "GOLDENROD": module.exports.GOLDENROD,
  "JAZZBERRY_JAM": module.exports.JAZZBERRY_JAM,
  "MAC_AND_CHEESE": module.exports.MAC_AND_CHEESE,
  "MAHOGANY": module.exports.MAHOGANY,
  "MANGO_TANGO": module.exports.MANGO_TANGO,
  "MAUVELOUS": module.exports.MAUVELOUS,
  "PURPLE_PIZZAZZ": module.exports.PURPLE_PIZZAZZ,
  "RAZZMATAZZ": module.exports.RAZZMATAZZ,
  "SALMON": module.exports.SALMON,
  "SILVER": module.exports.SILVER,
  "TICKLE_ME_PINK": module.exports.TICKLE_ME_PINK,
  "WILD_BLUE_YONDER": module.exports.WILD_BLUE_YONDER,
  "WISTERIA": module.exports.WISTERIA,
  "LASER_LEMON": module.exports.LASER_LEMON,
  "EGGPLANT": module.exports.EGGPLANT,
  "CHARTREUSE": module.exports.CHARTREUSE,
};

var colorList = module.exports.colorList = [
  "RED",
  "WHITE",
  "BLACK",
  "BLUE",
  "GREEN",
  "ORANGE",
  "YELLOW",
  "PERRYWINKLE",
  "ARTHURS_SKIN",
  "SARCOLINE",
  "COQUELICOT",
  "SMARAGDINE",
  "ALMOND",
  "ASPARAGUS",
  "BURNT_SIENNA",
  "CERULEAN",
  "DANDELION",
  "DENIM",
  "ELECTRIC_LIME",
  "FUZZY_WUZZY",
  "GOLDENROD",
  "JAZZBERRY_JAM",
  "MAC_AND_CHEESE",
  "MAHOGANY",
  "MANGO_TANGO",
  "MAUVELOUS",
  "PURPLE_PIZZAZZ",
  "RAZZMATAZZ",
  "SALMON",
  "SILVER",
  "TICKLE_ME_PINK",
  "WILD_BLUE_YONDER",
  "WISTERIA",
  "LASER_LEMON",
  "EGGPLANT",
  "CHARTREUSE",
];
*/
