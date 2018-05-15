var express = require('express');

var routes = function(Livro){
  var livroRouter = express.Router();

  livroRouter.route('/')
    .post(function(req,res){
      var livro = new Livro(req.body);
      livro.save(livro);
      res.status(201).send(livro);
    })
    .get(function(req, res){
      var query = req.query;

      Livro.find(query, function(err, livros){
        if(err)
          res.status(500).send(err);
          else {
            res.json(livros);
          }
      });
    });
    livroRouter.use('/:livroId', function(req,res,next){
      Livro.findById(req.params.livroId, function(err, livro){
        if(err)
          res.status(500).send(err);
          else if(livro) {
            req.livro = livro;
            next();
          }
          else {
            res.status(404).send('Nenhum livro encontrado!');
          }
      });
    });
    livroRouter.route('/:livroId')
      .get(function(req, res){

        res.json(req.livro);
      })
      .put(function(req, res){
        req.livro.titulo = req.body.titulo;
        req.livro.autor = req.body.autor;
        req.livro.paginas = req.body.paginas;
        req.livro.save(function(err){
          if(err){
            res.status(500).send(err);
          } else {
            res.json(req.livro);
          }
        });

    })
    .patch(function(req, res){
        if(req.body._id)
          delete req.body._id;
        for(var p in req.body){
          req.livro[p] = req.body[p];
        }

        req.livro.save(function(err){
          if(err){
            res.status(500).send(err);
          } else {
            res.json(req.livro);
          }
        });
  })
  .delete(function(req, res){
    req.livro.remove(function(err){
      if(err){
        res.status(500).send(err);
      } else {
        res.status(500).send('Removido!');
      }
    });
  })
    return livroRouter;
};

module.exports = routes;
