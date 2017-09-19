 /**
  * Module dependencies.
  */

 var express = require('express'),
   routes = require('./server/routes'),
   opn = require('opn'),
   errorHandler = require('errorhandler'),
   multer = require('multer'),
   serveFavicon = require('serve-favicon'),
   morgan = require('morgan'),
   userCache = require('./server/public/js/userCache'),
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
   methodOverride = require('method-override'),
   _ = require('underscore'),
   bodyParser = require('body-parser'),
   redisStore = require('connect-redis')(session),
   bodyParser = require('body-parser'),
   path = require('path');
 var router = express.Router()
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
   }), // 本地存储session（文本文件，也可以选择其他store，比如redis的）,
   saveUninitialized: false, // 是否自动保存未初始化的会话，建议false
   resave: false, // 是否每次都重新保存会话，建议false
 }));
 // parse application/x-www-form-urlencoded
 app.use(bodyParser.urlencoded({
   extended: false
 }))
 // parse application/json
 app.use(bodyParser.json())
 app.use(morgan('combined'))
 app.use(methodOverride('X-HTTP-Method-Override'));
 app.use(router);
 app.use(express.static(path.join(__dirname, 'public')));
 //设置网站图标路径
 app.use(serveFavicon(path.join(__dirname, 'static', 'favicon.ico')))

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
     console.log('socket login' + obj.userName);
     console.log("add user")
     socket.join('mainRoom')
     socketList[obj.userId] = socket.id
     socket.emit("loginResponse", "欢迎登陆~")
     console.log(socketList)
     /* console.log(io.sockets.sockets[socketList[obj.userId]])*/
     socket.emit("hello", "欢迎回来~")
   })



   socket.on("message", function(data) {
     console.log('socketList');
     console.log(socketList);
     var targetId = data.targetId
     var time = data.time
     var content = data.content
     var myName = data.myName
     var ownId = data.ownId
     console.log(targetId + content + time)
     console.log(socketList[targetId]);
     if (io.sockets.connected[socketList[targetId]]) {
       console.log('向' + socketList[targetId] + '发送信息');
       io.sockets.connected[socketList[targetId]].emit('message', {
         time: time,
         content: content,
         myName: myName,
         ownId: ownId
       });
     } else {
       console.log('用户不在线');
       redisClient.select('0', function(err) {
         if (!err) {
           console.log('userCache:' + data.targetId);
           redisClient.rpush('historyMessage:' + data.targetId, JSON.stringify(data), function(err, res) {
             console.log(err);
             console.log(res);
           })
         }
       })
     }

   })

 })




 // development only
 if ('development' == app.get('env')) {
   app.use(errorHandler);
 }

 router.all('*', function(req, res, next) {
   res.header('Access-Control-Allow-Origin', "http://localhost:8081");
   res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
   res.header("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
   res.header("X-Powered-By", ' 3.2.1');
   res.header("Content-Type", "application/json;charset=utf-8");
   res.header('Access-Control-Allow-Credentials', true)
   next();
 });

 router.post('/getFrendList', urlencodedParser, function(req, res, next) {
   console.log('getFrendList');
   if (req.session.user) {
     let id = req.session.user.id
     connection.query('select us.username, us.header_pic, us.sex,rs.minor_user from relation_ship rs,user us where rs.main_user=' + '"' + id + '"' + ' and rs.level =4 and rs.minor_user=us.id', function(err, results, xfields) {
       res.send(results)
     })
   }

 })


 router.post("/checkStatus", urlencodedParser, function(req, res) {
   console.log('checkStatus');
   console.log(req.session);
   if (req.session.user) {
     res.send(req.session.user)
   } else {
     res.send("null")
   }

 })


 router.post('/pullCurrentMessageList', urlencodedParser, function(req, response) {
   console.log('pullCurrentMessageList');
   console.log(req.session);
   if (req.session.user) {
     redisClient.lrange('historyMessage:' + req.session.user.id, 0, -1, function(err, res) {

       if (res) {
         response.send(res)
       } else {
         response.send(null)
       }
     })
   }

 })

 router.post("/login", urlencodedParser, function(req, response) {
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
   connection.query(sql, function(err, results, xfields) {
     //  req.sessionID=results[0].id
     if (err) {
       console.log(err);
     }
     console.log('results');
     console.log(results);
     if (results[0]) {
       req.session.user = results[0]
       response.send(results[0])
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
