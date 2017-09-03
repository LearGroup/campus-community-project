 /**
  * Module dependencies.
  */

 var express = require('express'),
   routes = require('./server/routes'),
   opn = require('opn'),
   userCache = require('./server/public/js/userCache')
 webpack = require('webpack'),
   proxyMiddleware = require('http-proxy-middleware'),
   user = require('./server/routes/user'),
   http = require('http'),
   socket = require('socket.io'),
   config = require('./config'),
   ejs = require('ejs'),
   session = require('express-session'),
   mysql = require('mysql'),
   redis = require('redis')
 cookieParser = require('cookie-parser'),
   _ = require('underscore'),
   bodyParser = require('body-parser'),
   redisStore = require('connect-redis')(session),
   path = require('path');

 var redisClient = redis.createClient(6379, 'localhost', {
   auth_pass: 18247352203
 })
 redisClient.on('ready', function() {
   console.log('redis is ready');
   redisClient.del('sess:NiuDfXNHOOo7ZVtL96cgjo9_cUf-yLB8')
 })
 var uri = "localhost:8081"
 if (!process.env.NODE_ENV) {
   process.env.NODE_ENV = JSON.parse(config.dev.env.NODE_ENV)
 }
 var webpackConfig = process.env.NODE_ENV === 'testing' ?
   require('./build/webpack.prod.conf') :
   require('./build/webpack.dev.conf')


 // default port where dev server listens for incoming traffic
 var port = 8081
 // automatically open browser, if not set will be false
 var autoOpenBrowser = !!config.dev.autoOpenBrowser
 // Define HTTP proxies to your custom API backend
 // https://github.com/chimurai/http-proxy-middleware
 var proxyTable = config.dev.proxyTable




 // 创建 application/x-www-form-urlencoded 编码解析
 var urlencodedParser = bodyParser.urlencoded({
   extended: false
 })

 var identityKey = 'skey';


 var socketList = {}

 var connection = mysql.createConnection({
   host: '127.0.0.1',
   user: 'root',
   password: '123456789',
   database: 'currency_db'
 });
 var app = express()
 var server = http.createServer(app)
 io = socket.listen(server)


 app.set('port', 8081);
 app.use(cookieParser())
 app.use(session({
   name: identityKey,
   secret: 'chyingp', // 用来对session id相关的cookie进行签名
   store: new redisStore({
     host: 'localhost',
     port: 6379,
     pass: 18247352203,
     db: "0"
   }), // 本地存储session（文本文件，也可以选择其他store，比如redis的）
   cookie: {
     'maxAge': 1000 * 60 * 60 * 6 //默认session过期时间：6小时
   },
   saveUninitialized: false, // 是否自动保存未初始化的会话，建议false
   resave: false, // 是否每次都重新保存会话，建议false
 }));
 app.use(express.favicon());
 app.use(express.logger('dev'));
 app.use(express.bodyParser());
 app.use(express.methodOverride());
 app.use(app.router);
 app.use(express.static(path.join(__dirname, 'public')));


 // all environments
 var compiler = webpack(webpackConfig)

 var devMiddleware = require('webpack-dev-middleware')(compiler, {
   publicPath: webpackConfig.output.publicPath,
   quiet: true
 })

 var hotMiddleware = require('webpack-hot-middleware')(compiler, {
   log: false,
   heartbeat: 2000
 })
 // force page reload when html-webpack-plugin template changes
 compiler.plugin('compilation', function(compilation) {
   compilation.plugin('html-webpack-plugin-after-emit', function(data, cb) {
     hotMiddleware.publish({
       action: 'reload'
     })
     cb()
   })
 })

 // handle fallback for HTML5 history API
 app.use(require('connect-history-api-fallback')())

 // serve webpack bundle output
 app.use(devMiddleware)

 // enable hot-reload and state-preserving
 // compilation error display
 app.use(hotMiddleware)

 // serve pure static assets
 var staticPath = path.posix.join(config.dev.assetsPublicPath, config.dev.assetsSubDirectory)
 app.use(staticPath, express.static('./static'))

 //当前在线人数
 var onlineCount = 0;

 io.on('connection', function(socket) {
   console.log('一个用户上线了')
   //监听新用户加入
   socket.on("login", function(obj) {
     if (socketList[obj.userId]) {

     } else {
       console.log("add user")
       socket.userName = obj.userName
       socket.userId = obj.userId
       socket.join('mainRoom')
       socketList[obj.userId] = socket.id
       socket.emit("hello", "欢迎登陆~")
     }
     console.log(socketList)
     /* console.log(io.sockets.sockets[socketList[obj.userId]])*/
     socket.emit("hello", "欢迎回来~")
   })

   socket.on("message", function(data) {
     var targetId = data.targetId
     var time = data.time
     var content = data.content
     var myName = data.myName
     console.log(targetId + content + time)
     if (socketList[targetId]) {
       var targetSocket = socketList[targetId]
       io.sockets.sockets[socketList[targetId]].emit('message', {
         time: time,
         content: content,
         myName: myName
       })
       socket.emit("messageResponse", "success~")
     }

   })

 })




 // development only
 if ('development' == app.get('env')) {
   app.use(express.errorHandler());
 }

 app.all('*', function(req, res, next) {
   res.header('Access-Control-Allow-Origin', "http://localhost:8081");
   res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
   res.header("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
   res.header("X-Powered-By", ' 3.2.1');
   res.header("Content-Type", "application/json;charset=utf-8");
   res.header('Access-Control-Allow-Credentials', true)
   next();
 });

 app.post('/getFrendList', urlencodedParser, function(req, res) {
   console.log('getFrendList');
   if (req.session.user) {
     let id = req.session.user.id
     connection.query('select us.username, us.header_pic, us.sex,rs.minor_user from relation_ship rs,user us where rs.main_user=' + '"' + id + '"' + ' and rs.level =4 and rs.minor_user=us.id', function(err, results, xfields) {
       res.send(results)
     })
   }

 })


 app.post("/checkStatus", urlencodedParser, function(req, res) {
   console.log('checkStatus');
   console.log(req.session);
   if (req.session.user) {
     res.send(req.session.user)
   } else {
     res.send("null")
   }

 })

 app.post("/login", urlencodedParser, function(req, response) {
   var use = req.body.use
   var username = req.body.username
   var password = req.body.password
   var sql = null;
   console.log('login sessionID');
   console.log(req.session);
   console.log(userCache);
   console.log(userCache.userCacheModule);
   if (use == 'phone') {
     sql = 'select us.username,us.id,us.header_pic from user us where us.phone="' + username + '" and us.password="' + password + '"'
   } else {
     sql = 'select us.username,us.id,us.header_pic from user us where us.email="' + username + '" and us.password="' + password + '"'
   }
   //访问用户数据库信息
   connection.query('select us.username,us.id,us.header_pic from user us where us.phone="' + username + '" and us.password="' + password + '"', function(err, results, xfields) {
     //  req.sessionID=results[0].id
     if (err) {
       console.log(err);
     }
     if (results) {
       req.session.user = results[0]
       redisClient.get('userCache:' + results[0].id, function(err, res) {
         //若缓存不存在，创建一个缓存
         if (!res) {
           console.log('userCache not exit');
           //historyMessage:离线信息
           //activeMessage:当前活动信息
           //relationShipOne:第一等级关系：好友关系（相对于我）
           //relationShipTwo:第二等级关系：群组关系（相对于我）
           redisClient.hmset('userCache:' + results[0].id, userCache.userCacheModule,function(err,res){
                 response.send(results[0])
           })
         }
       })
     } else {
       response.send('null')
     }
   })
 })

 devMiddleware.waitUntilValid(() => {
   console.log('> Listening at ' + uri + '\n')
   // when env is testing, don't need open it
   if (autoOpenBrowser && process.env.NODE_ENV !== 'testing') {

   }
   _resolve()
 })
 server.listen(app.get('port'));

 var _resolve
 var readyPromise = new Promise(resolve => {
   _resolve = resolve
 })
 module.exports = {
   ready: readyPromise,
   close: () => {
     server.close()
   }
 }
