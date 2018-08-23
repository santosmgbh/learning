var express = require('express');
var mysql = require("mysql");
var bodyParser = require('body-parser');


var Livro = require('./model/livroModel');


var app = express();

app.use(function(req, res, next){
	res.locals.connection = mysql.createConnection({
		host     : '184.107.24.224',
		user     : 'ldigital',
		password : 'u!Ti{DGg5%m#',
		database : 'cambua_ld_digital'
	});
	
	res.locals.connection.connect();
	next();
});

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
