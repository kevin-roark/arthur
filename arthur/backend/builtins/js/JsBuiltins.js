
var startTime = Date.now();
module.exports.start = startTime;

module.exports.ms = function () {
  return (Date.now() - startTime);
}
