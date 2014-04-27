
var ArthurNumber = require('./arthur-number');
var ArthurColor = require('./arthur-color');
var ArthurFrame = require('./arthur-frame');

var startTime = Date.now();
module.exports.start = startTime;

module.exports.ms = function () {
  return new ArthurNumber((Date.now() - startTime));
}

module.exports.cooler = function() {
  var idx = Math.floor(Math.random() * colorList.length);
  var name = colorList[idx];
  var color = colorMap[name];
  return color;
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
module.exports.INDIAN_RED = new ArthurColor(176, 23, 31, 1.0);
module.exports.CRIMSON = new ArthurColor(220, 20, 60, 1.0);
module.exports.PINK = new ArthurColor(255, 192, 203, 1.0);
module.exports.PALEVIOLETRED = new ArthurColor(219, 112, 147, 1.0);
module.exports.LAVENDERBLUSH = new ArthurColor(255, 240, 245, 1.0);
module.exports.HOTPINK = new ArthurColor(255, 105, 180, 1.0);
module.exports.RASPBERRY = new ArthurColor(135, 38, 87, 1.0);
module.exports.DEEPPINK = new ArthurColor(255, 20, 147, 1.0);
module.exports.MAROON = new ArthurColor(255, 52, 179, 1.0);
module.exports.ORCHID = new ArthurColor(218, 112, 214, 1.0);
module.exports.THISTLE = new ArthurColor(216, 191, 216, 1.0);
module.exports.PLUM = new ArthurColor(255, 187, 255, 1.0);
module.exports.VIOLET = new ArthurColor(238, 130, 238, 1.0);
module.exports.MAGENTA = new ArthurColor(255, 0, 255, 1.0);
module.exports.PURPLE = new ArthurColor(128, 0, 128, 1.0);
module.exports.DARKVIOLET = new ArthurColor(148, 0, 211, 1.0);
module.exports.HENRY = new ArthurColor(147, 112, 219, 1.0);
module.exports.SLATEBLUE = new ArthurColor(72, 61, 139, 1.0);
module.exports.GHOSTWHITE = new ArthurColor(248, 248, 255, 1.0);
module.exports.LAVENDER = new ArthurColor(230, 230, 250, 1.0);
module.exports.NAVY = new ArthurColor(0, 0, 128, 1.0);
module.exports.MIDNIGHTBLUE = new ArthurColor(25, 25, 112, 1.0);
module.exports.COBALT = new ArthurColor(61, 89, 171, 1.0);
module.exports.ROYALBLUE = new ArthurColor(64, 105, 225, 1.0);
module.exports.CORNFLOWERBLUE = new ArthurColor(100, 149, 237, 1.0);
module.exports.LIGHTSTEELBLUE = new ArthurColor(176, 196, 222, 1.0);
module.exports.SLATEGRAY = new ArthurColor(112, 128, 144, 1.0);
module.exports.SKYBLUE = new ArthurColor(135, 206, 235, 1.0);
module.exports.PEACOCK = new ArthurColor(51, 161, 201, 1.0);
module.exports.TURQUIOSE = new ArthurColor(0, 245, 255, 1.0);
module.exports.AZURE = new ArthurColor(240, 255, 255, 1.0);
module.exports.AQUAMARINE = new ArthurColor(69, 139, 116, 1.0);
module.exports.MINT = new ArthurColor(189, 252, 201, 1.0);
module.exports.HONEYDEW = new ArthurColor(240, 255, 240, 1.0);
module.exports.LIMEGREEN = new ArthurColor(50, 205, 50, 1.0);
module.exports.DARKGREEN = new ArthurColor(0, 100, 0, 1.0);
module.exports.GREENYELLOW = new ArthurColor(173, 255, 47, 1.0);
module.exports.IVORY = new ArthurColor(255, 255, 240, 1.0);
module.exports.BEIGE = new ArthurColor(245, 245, 220, 1.0);
module.exports.OLIVE = new ArthurColor(128, 128, 0, 1.0);
module.exports.KHAKI = new ArthurColor(255, 246, 143, 1.0);
module.exports.LEMONCHIFFON = new ArthurColor(255, 250, 205, 1.0);
module.exports.BANANA = new ArthurColor(227, 207, 87, 1.0);
module.exports.GOLD = new ArthurColor(255, 215, 0, 1.0);
module.exports.DARKGOLDENROD = new ArthurColor(184, 134, 11, 1.0);
module.exports.WHEAT = new ArthurColor(245, 222, 179, 1.0);
module.exports.BRICK = new ArthurColor(156, 102, 31, 1.0);
module.exports.CARROT = new ArthurColor(237, 145, 33, 1.0);
module.exports.CORAL = new ArthurColor(255, 127, 80, 1.0);
module.exports.BROWN = new ArthurColor(165, 42, 42, 1.0);
module.exports.BEET = new ArthurColor(142, 56, 142, 1.0);
module.exports.TOMATO = new ArthurColor(255, 99, 71, 1.0);
module.exports.INDIGO = new ArthurColor(75, 0, 130, 1.0);

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
  "INDIAN_RED": module.exports.INDIAN_RED,
  "CRIMSON": module.exports.CRIMSON,
  "PINK": module.exports.PINK,
  "PALEVIOLETRED": module.exports.PALEVIOLETRED,
  "LAVENDERBLUSH": module.exports.LAVENDERBLUSH,
  "HOTPINK": module.exports.HOTPINK,
  "RASPBERRY": module.exports.RASPBERRY,
  "DEEPPINK": module.exports.DEEPPINK,
  "MAROON": module.exports.MAROON,
  "ORCHID": module.exports.ORCHID,
  "THISTLE": module.exports.THISTLE,
  "PLUM": module.exports.PLUM,
  "VIOLET": module.exports.VIOLET,
  "MAGENTA": module.exports.MAGENTA,
  "PURPLE": module.exports.PURPLE,
  "DARKVIOLET": module.exports.DARKVIOLET,
  "HENRY": module.exports.HENRY,
  "SLATEBLUE": module.exports.SLATEBLUE,
  "GHOSTWHITE": module.exports.GHOSTWHITE,
  "LAVENDER": module.exports.LAVENDER,
  "NAVY": module.exports.NAVY,
  "MIDNIGHTBLUE": module.exports.MIDNIGHTBLUE,
  "COBALT": module.exports.COBALT,
  "ROYALBLUE": module.exports.ROYALBLUE,
  "CORNFLOWERBLUE": module.exports.CORNFLOWERBLUE,
  "LIGHTSTEELBLUE": module.exports.LIGHTSTEELBLUE,
  "SLATEGRAY": module.exports.SLATEGRAY,
  "SKYBLUE": module.exports.SKYBLUE,
  "PEACOCK": module.exports.PEACOCK,
  "TURQUIOSE": module.exports.TURQUIOSE,
  "AZURE": module.exports.AZURE,
  "AQUAMARINE": module.exports.AQUAMARINE,
  "MINT": module.exports.MINT,
  "HONEYDEW": module.exports.HONEYDEW,
  "LIMEGREEN": module.exports.LIMEGREEN,
  "DARKGREEN": module.exports.DARKGREEN,
  "GREENYELLOW": module.exports.GREENYELLOW,
  "IVORY": module.exports.IVORY,
  "BEIGE": module.exports.BEIGE,
  "OLIVE": module.exports.OLIVE,
  "KHAKI": module.exports.KHAKI,
  "LEMONCHIFFON": module.exports.LEMONCHIFFON,
  "BANANA": module.exports.BANANA,
  "GOLD": module.exports.GOLD,
  "DARKGOLDENROD": module.exports.DARKGOLDENROD,
  "WHEAT": module.exports.WHEAT,
  "CARROT": module.exports.CARROT,
  "BRICK": module.exports.BRICK,
  "BROWN": module.exports.BROWN,
  "BEET": module.exports.BEET,
  "TOMATO": module.exports.TOMATO,
  "INDIGO": module.exports.INDIGO
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
  "INDIAN_RED",
  "CRIMSON",
  "PINK",
  "PALEVIOLETRED",
  "LAVENDERBLUSH",
  "HOTPINK",
  "RASPBERRY",
  "DEEPPINK",
  "MAROON",
  "ORCHID",
  "THISTLE",
  "PLUM",
  "VIOLET",
  "MAGENTA",
  "PURPLE",
  "DARKVIOLET",
  "HENRY",
  "SLATEBLUE",
  "GHOSTWHITE",
  "LAVENDER",
  "NAVY",
  "MIDNIGHTBLUE",
  "COBALT",
  "ROYALBLUE",
  "CORNFLOWERBLUE",
  "LIGHTSTEELBLUE",
  "SLATEGRAY",
  "SKYBLUE",
  "PEACOCK",
  "TURQUIOSE",
  "AZURE",
  "AQUAMARINE",
  "MINT",
  "HONEYDEW",
  "LIMEGREEN",
  "DARKGREEN",
  "GREENYELLOW",
  "IVORY",
  "BEIGE",
  "OLIVE",
  "KHAKI",
  "LEMONCHIFFON",
  "BANANA",
  "GOLD",
  "DARKGOLDENROD",
  "WHEAT",
  "BRICK",
  "CARROT",
  "BROWN",
  "BEET",
  "TOMATO",
  "INDIGO"
];
