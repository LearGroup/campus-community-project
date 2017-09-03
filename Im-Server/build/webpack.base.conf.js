var path = require('path')
var utils = require('./utils')
var config = require('../config')
var webpack = require('webpack')
var vueLoaderConfig = require('./vue-loader.conf')

function resolve (dir) {
  return path.join(__dirname, '..', dir)
}

module.exports = {
  plugins: [
    new webpack.optimize.CommonsChunkPlugin('common.js'),
    new webpack.ProvidePlugin({
         jQuery: "jquery",
         $: "jquery",
         "window.jQuery": "jquery"
       })
     ],
     entry: {
       app: './src/main.js'
     },
     output: {
       path: config.build.assetsRoot,
       filename: '[name].js',
       publicPath: process.env.NODE_ENV === 'production'
         ? config.build.assetsPublicPath
         : config.dev.assetsPublicPath
     },
     resolve: {
       extensions: ['.js', '.vue', '.json'],
       alias: {
         'vue$': 'vue/dist/vue.esm.js',
         '@': resolve('src'),
         'jquery': 'jquery',
         'bootstrap':resolve('src/assets/bootstrap')
       }
     },
     module: {
       rules: [
         {
           test: /\.vue$/,
           loader: 'vue-loader',
           options: vueLoaderConfig
         },
         {
           test: /\.js$/,
           loader: 'babel-loader',
           include: [resolve('src'), resolve('test'),resolve('src/assets/bootstrap/js')]
         },
         {
           test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
           loader: 'url-loader',
           options: {
             limit: 10000,
             name: utils.assetsPath('img/[name].[hash:7].[ext]')
           }
         },
         {
           test: /\.(mp4|webm|ogg|mp3|wav|flac|aac)(\?.*)?$/,
           loader: 'url-loader',
           options: {
             limit: 10000,
             name: utils.assetsPath('media/[name].[hash:7].[ext]')
           }
         },
         {
           test: /\.(woff2?|eot|ttf|otf)(\?.*)?$/,
           loader: 'url-loader',
           options: {
             limit: 10000,
             name: utils.assetsPath('fonts/[name].[hash:7].[ext]')
           }
         },
         {
           test: /\\.css$/,
           loader: "style-loader!css-loader",
         },
         {
           test: /\\.(eot|woff|woff2|ttf)([\\?]?.*)$/,
           loader: "file"
         }
       ]
     }
   }
