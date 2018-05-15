var mongoose = require('mongoose');
  Schema = mongoose.Schema;

var livroModel = new Schema({
  titulo:{type:String},
  autor:{type:String},
  paginas:{type:Number}
});

module.exports = mongoose.model('Livro', livroModel);
