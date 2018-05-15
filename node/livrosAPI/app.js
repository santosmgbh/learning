var express = require('express');
var mongoose = require('mongoose');
var bodyParser = require('body-parser');

var db = mongoose.connect('mongodb://localhost/livroAPI');

var Livro = require('./model/livroModel');


var app = express();

var port = process.env.PORT || 3000;

app.use(bodyParser.urlencoded({extended:true}));
app.use(bodyParser.json());

livroRouter = require('./routes/livroRoutes')(Livro);

app.use('/api/livros', livroRouter);

app.get('/', function(req, res){
  res.send('Bem vindos a minha API');
});

app.listen(port, function(){
  console.log('Runing on PORT: ' + port);
});
