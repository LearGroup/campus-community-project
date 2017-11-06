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
  uuid=require('node-uuid'),
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
   password: '18247352203',
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
     pass: '18247352203',
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


   socket.on('synchronization',function(data){
    console.log("synchronization ID",socket.userId)
    //历史私人聊天信息
    redisClient.lrange('historyMessage:' + socket.userId, 0, -1, function(err, res) {
     if (res.length!=0) {
      console.log("有历史信息",res)
      socket.emit('message',JSON.parse('{"status":"1","results":['+res+']}'))
      redisClient.del('historyMessage:' + socket.userId)
    } else {
      socket.emit('message',JSON.parse('{"status":"null"}'))
    }
  })

    //历史好友请求消息
    redisClient.lrange('requestBuildRelationShip:' + socket.userId, 0, -1, function(err, res) {
     if (res.length!=0) {
       socket.emit('requestBuildRelationShip',JSON.parse('{"status":"1","results":['+res+']}'))
     } 
   })

    //历史好友请求接受信息 historyRelation

    redisClient.lrange('historyRelation:' + socket.userId, 0, -1, function(err, res) {
     if (res.length!=0) {
       socket.emit('registerPersonRelationShip',JSON.parse('{"status":"1","results":['+res+']}'))
     } 
   })



  })


   socket.on("ofline",function(obj){
    console.log("ofline")
    console.log(obj.sessionId)
    redisClient.del(obj.sessionId,function(err,results){
      if(err){
        console.log(err)
      }
      console.log(results)
      socket.emit("checkStatus",JSON.parse('{"status":"2"}'))
      socket.disconnect(true)
    })

  })


   console.log('一个用户上线了')
   //监听新用户加入
   socket.on("login", function(obj) {
     console.log('socket login' + obj.userName);
     console.log("add user")
     socket.join('mainRoom')
     socket.userId=obj.userId
     socketList[obj.userId] = socket.id
     socket.emit("loginResponse", "欢迎登陆~")
     console.log(socketList)
     /* console.log(io.sockets.sockets[socketList[obj.userId]])*/
     socket.emit("checkStatus",JSON.parse('{"status":"1"}'))
     console.log("loginreconnect",socket.id)
     /*redisClient.lrange('historyMessage:' + socket.id, 0, -1, function(err, res) {
     if (res) {
        console.log("有历史信息",res)
         socket.emit('message',JSON.parse('{"status":"1","results":['+JSON.stringify(res)+']}'))
       } 
     })*/
   })



   socket.on('registerPersonRelationShipResponse',function(result){
    console.log('registerPersonRelationShipResponse')
     console.log(result)
     console.log(result.status)
  //  let data=JSON.parse(result)
    if(result.status=="1"){

     redisClient.del('historyRelation:' + socket.userId)
    }

  })

   socket.on('requestBuildRelationShipResponse',function(result){
   console.log('requestBuildRelationShipResponse')
   console.log(result)
   console.log(result.status)
   // let data=JSON.parse(result)
    if(result.status=="1"){

     redisClient.del('requestBuildRelationShip:' + socket.userId)
    }
   })



   socket.on('requestBuildRelationShip',function(result){
    console.log('requestBuildRelationShip')
    let data=JSON.parse(result)
    data["sex"]=data["sex"]+""
    data["view_type"]=data["view_type"]+""

    console.log(result)
    let targetId = data.target_id//发送目标id
    console.log(data.target_id)
    if(io.sockets.connected[socketList[targetId]]){
      console.log("send")
      delete data['target_id']
      console.log(data.target_id)

      io.sockets.connected[socketList[targetId]].emit('requestBuildRelationShip',
        JSON.parse('{"status":"1","results":'+'['+JSON.stringify(data)+']}'))
    }else {
      //首先验证该请求信息是否已存在
      redisClient.lrange('requestBuildRelationShip:' + targetId, 0, -1, function(err, res) {
       if (res.length!=0) {
        let rs=JSON.parse('['+res+']')
        for(let i=0 ;i<rs.length;i++){
          if(rs[i].user_id==data.user_id){
            console.log("信息已存在")
            return ;   
          }
          
        }
      } else {
        console.log('用户不在线');
        redisClient.select('0', function(err) {
         if (!err) {
           console.log('userCache:' + targetId);
           redisClient.rpush('requestBuildRelationShip:' +  targetId, JSON.stringify(data), function(err, res) {
             console.log(err);
             console.log(res);
           })
         }
       })
      }
    })

    }

    
  })


   socket.on("message", function(data) {
     console.log('socketList');
     console.log(socketList);
     var targetId = data.targetId//发送目标id
     var time = data.time
     var content = data.content
     var myName = data.myName
     var ownId = data.ownId//发送者id
     console.log(targetId + content + time+ownId)
     console.log(socketList[targetId]);
     if (io.sockets.connected[socketList[targetId]]) {
       console.log('向' + socketList[targetId] + '发送信息');
       console.log(time)
       io.sockets.connected[socketList[targetId]].emit('message',JSON.parse('{"status":"1","results":'+'['+JSON.stringify({
         "time": time,
         "content": content,
         "myName": myName,
         "ownId": ownId
       })+ ']}'));
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

   socket.on('reconnect',function(data){
     console.log("reconnect")
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
   console.log(req.sessionID);
   if (req.session.user) {
    console.log("userId",req.session.user.id)
    let id = req.session.user.id
    connection.query('select us.header_pic,us.username,us.sex,us.age,us.email,us.self_abstract,us.sprovince,us.sarea,us.town,us.phone,rs.minor_user ,rs.level ,rs.id   from relation_ship rs,user us where rs.main_user=' + '"' + id + '"' + ' and rs.level =4 and rs.minor_user=us.id', function(err, results, xfields) {
     console.log(err)
     console.log(results);
     res.send(JSON.parse(JSON.stringify('{"status":"1","results":'+JSON.stringify(results)+'}')))
   })
  }else{
   res.send(JSON.parse(JSON.stringify('{"status":"0"}')));
 }
})



 //注册新关系
 router.post("/registerPersonRelationShip",urlencodedParser,function(req,response){
   console.log('registerPersonRelationShip')
   let main_id=req.body.min_id;//接受请求者
   let req_id=req.body.req_id
   let parid1=uuid.v1();
   let parid2=uuid.v1();
   console.log(main_id)

   console.log( req_id)
   let querySql ='select rs.id from relation_ship rs where (rs.main_user='+"'"+main_id+"'"+' and rs.minor_user='+"'"+req_id+"'"+')  and(rs.level=4)'

   let sql='insert into relation_ship(id,main_user,minor_user,level) values(?,?,?,?)'
   let params =[parid1,req.body.min_id,req.body.req_id,'4']
   let params2=[parid2,req.body.req_id,req.body.min_id,'4']

   //首先查询该关系是否存在
   connection.query(querySql, function(err, results, xfields){
    if(err){
      console.log(err)
    }
    if(results){

      //若不存在 则插入新关系
      if(results.length==0){
        connection.query(sql,params,function(err,res){  
          if(err){
            console.log(err)
          }
          if(res){
            connection.query(sql,params2,function(err,res){
              if(err){
                console.log(err)
              }
              if(res){
                //插入成功 向终端发送好友增加信息

                connection.query('select us.header_pic,us.username,us.sex,us.age,us.email,us.self_abstract,us.sprovince,us.sarea,us.stown,us.phone from user us where id='+"'"+req_id+"'",function(err,result){
                  if(err) console.log(err)
                    if(result){
                      let parm=result
                      parm[0].level='4';
                      parm[0]['id']=parid1;
                      parm[0]['minor_user']=req_id
                      parm[0]['self_abstract']= parm[0]['self_abstract']+""
                      parm[0]['age']= parm[0]['age']+""
                      parm[0]['town']= parm[0]['town']+""
                      parm=JSON.stringify(result)
                      response.send(JSON.parse(JSON.stringify('{"status":"1","results":'+parm+'}')));
                      console.log(result)

                      connection.query('select us.header_pic,us.username,us.sex,us.age,us.email,us.self_abstract,us.sprovince,us.sarea,us.stown,us.phone from user us where id='+"'"+main_id+"'",function(err,result){

                        if(result){
                         let parm=result
                         parm[0].level='4';
                         parm[0]['id']=parid2;
                         parm[0]['minor_user']=main_id
                         parm[0]['self_abstract']= parm[0]['self_abstract']+""
                         parm[0]['age']= parm[0]['age']+""
                         parm[0]['stown']= parm[0]['stown']+""


                         if (io.sockets.connected[socketList[req_id]]) {
                           console.log('向' + socketList[req_id] + '发送好友注册信息');
                           console.log(parm[0])
                           io.sockets.connected[socketList[req_id]].emit('registerPersonRelationShip',     JSON.parse('{"status":"1","results":'+JSON.stringify(result)+'}'));
                         } else { 
                      //若用户不在线 将信息缓存到redis
                      console.log('用户不在线 redis');
                      redisClient.select('0', function(err) {
                       if (!err) {
                         console.log('userCache:' + req_id);
                         redisClient.rpush('historyRelation:' +req_id , parm, function(err, res) {
                           console.log(err);
                           console.log(res);
                         })
                       }
                     })
                    }                  



                  }
                })



                    }
                  })


              }
            })
          }
        })
      }else{
        console.log('关系已存在')
        response.send(JSON.parse(JSON.stringify('{"status":"0"}')));
      }
    }
  })

    /*    connection.query(sql,params,function(err,res){
          if(err){
            console.log(err)
          }
          if(res){
            connection.query(sql,params2,function(err,res){
              if(err){
                console.log(err)
              }
              if(res){
                response.send(JSON.parse(JSON.stringify('{"status":"1"}')));
              }
            })
          }
        })*/
      })

 router.post('/searchUser',urlencodedParser,function(req,res){
  console.log('searchUser')
  let use=req.body.use
  let result=req.body.result
  console.log(use+":"+result)
  console.log(req.session.user)
  let sql
  if(req.session.user){
    if(use=='phone'){
      sql='select us.id user_id ,us.sprovince,us.sex,us.town, us.phone results,us.header_pic header_pic, us.username username  from user us where us.phone='+'"'+result+'"'
    }else if(use=='email'){

      sql='select us.id user_id ,us.sprovince,us.sex,us.town,  us.email results,us.header_pic header_pic, us.username username  from user us where us.email='+'"'+result+'"'

    }else if(use=='username'){

      sql='select us.id user_id ,us.sprovince,us.sex,us.town,  us.username results,us.header_pic header_pic,us.username username from user us where us.username='+'"'+result+'"'

    }

    connection.query(sql,function(err, results, xfields) {
      if(err){
        console.log(err)
      }
      console.log(results);
      if(results.length>0){
       res.send(JSON.parse(JSON.stringify('{"status":"1","results":'+JSON.stringify(results)+'}')))
     }else{
       res.send(JSON.parse(JSON.stringify('{"status":"0"}')));
     }
   })
  }else{
    res.send(JSON.parse(JSON.stringify('{"status":"-1"}')));
  }


})

 router.post("/checkStatus", urlencodedParser, function(req, res) {
   console.log('checkStatus');
   if (req.session.user) {
     res.send(JSON.stringify('{"status":"1","results":'+JSON.stringify(req.session.user)+'}'))
   } else {
     res.send(JSON.parse(JSON.stringify('{"status":null}')))
   }

 })


 router.post('/pullCurrentMessageList', urlencodedParser, function(req, response) {
   console.log('pullCurrentMessageList');
   console.log(req.session);
   if (req.session.user) {
     redisClient.lrange('historyMessage:' + req.session.user.id, 0, -1, function(err, res) {

       if (res) {
         response.send(JSON.parse('{"status":"1","results":'+JSON.stringify(res)+'}'))
       } else {
         response.send(JSON.parse(JSON.stringify('{"status":null}')))
       }
     })
   }

 })

 router.post("/login", urlencodedParser, function(req, response) {
   var use = req.body.use
   var username = req.body.username
   var password = req.body.password
   var sql = null;
   console.log(use+username,password);
   console.log('login sessionID');
   console.log(req.sessionID);
   if (use == 'phone') {
     sql = 'select * from user us where us.phone="' + username + '" and us.password="' + password + '"'
   } else {
     sql = 'select * from user us where <us class="emai"></us>l="' + username + '" and us.password="' + password + '"'
   }
   //访问用户数据库信息
   connection.query(sql, function(err, results, xfields) {
     //  req.sessionID=results[0].id
     if (err) {
       console.log(err);
     }
     console.log('results');
     console.log(results[0]);
     if (results[0]) {
       req.session.user = results[0]
       let temp=JSON.parse(JSON.stringify('{"status":"1","results":'+JSON.stringify(results[0])+'}'))
       response.send(temp)
     } else {
       response.send(JSON.parse(JSON.stringify('{"status":null}')))
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

