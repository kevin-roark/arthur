#!/usr/local/bin/node

module.exports = exports = makeProject;

var ncp = require('ncp').ncp;

function makeProject() {
  console.log('initializing ur project');

  var loc = process.argv[2];

  ncp.limit = 16;
  ncp(__dirname + '/client', loc, function(err) {
    if (err) {
      return console.error(err);
    }
    console.log('made ur structure!!');
  });

}

makeProject();

