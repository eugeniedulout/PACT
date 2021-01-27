const express = require('express');

const router = express.Router();
const Product = require('../models/product');

app.get('/api/products', (req, res, next) => {
  Product.find()
    .then(products => res.status(200).json({ products: Product }))
    .catch(error => res.status(400).json({ error }));
});

app.get('/api/products/:id', (req, res, next) => {
  Product.findOne({ _id: req.params.id })
    .then(product => res.status(200).json({ product: Product }))
    .catch(error => res.status(404).json({ error }));
});

app.post('/api/products', (req, res, next) => {
  //delete req.body._id;
  const product = new Product({
    ...req.body
  });
  product.save()
    .then(() => {
      console.log({ product: Product });
      res.status(201).json({ product: Product });
  })
    .catch(error => res.status(400).json({ error }));
});

app.put('/api/products/:id', (req, res, next) => {
  Thing.updateOne({ _id: req.params.id }, { ...req.body, _id: req.params.id })
    .then(() => res.status(200).json({ message: 'Modified!' }))
    .catch(error => res.status(400).json({ error }));
});

app.delete('/api/products/:id', (req, res, next) => {
  Product.deleteOne({ _id: req.params.id })
    .then(() => res.status(200).json({ message: 'Deleted!' }))
    .catch(error => res.status(400).json({ error }));
});

module.exports = router;
