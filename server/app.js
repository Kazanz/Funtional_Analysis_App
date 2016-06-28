var bodyParser = require('body-parser')
var express = require('express');
var jsonfile = require('jsonfile')
var app = express();
 

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
})); 


app.post('/add', function (req, res) {
  var file = '/var/log/phd/' + req.body.id + ".json" 
  jsonfile.readFile(file, function(err, obj) {
    if (obj == null) {
      obj = []
    }
    obj.push(req.body)
    jsonfile.writeFile(file, obj, {spaces: 2}, function(err) {
      console.error(err)
    })
  })
  res.sendStatus(201);
});


app.listen(3000, function () {
    console.log('App listening on port 3000!');
});
