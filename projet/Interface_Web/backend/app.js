const express = require('express');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const jwt = require('jsonwebtoken');
const path = require('path');

const stuffRoutes = require('./routes/stuff');
const userRoutes = require('./routes/user');

const productsRoutes = require('./routes/products');

const app = express();

mongoose.connect('mongodb+srv://Setsuna:Gundam00@tartanpion.mt73z.mongodb.net/test?retryWrites=true&w=majority',
  { useNewUrlParser: true,
    useUnifiedTopology: true })
  .then(() => console.log('Connexion à MongoDB réussie !'))
  .catch(() => console.log('Connexion à MongoDB échouée !'));

app.use((req, res, next) => {
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content, Accept, Content-Type, Authorization');
  res.setHeader('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, PATCH, OPTIONS');
  next();
});

app.use(bodyParser.json());

app.use('/api/stuff', stuffRoutes);
app.use('/api/auth', userRoutes);
app.use('/images', express.static(path.join(__dirname, 'images')));

//passage à l'exo d'implémentation

app.use('/api/products', productsRoutes);

module.exports = app;
