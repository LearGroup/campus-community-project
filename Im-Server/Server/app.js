
/**
 * Module dependencies.
 */

var express = require('express')
  , routes = require('./routes')
  , user = require('./routes/user')
  , http = require('http')
  , socket = require('socket.io')
  , ejs = require('ejs')
  , session = require('express-session')
  , mysql =require('mysql')
  , cookieParser= require('cookie-parser')
  , _ = require('underscore')
  , bodyParser = require('body-parser')
  , redisStore = require('connect-redis')(session)
  , path = require('path');


// 创建 application/x-www-form-urlencoded 编码解析
var urlencodedParser = bodyParser.urlencoded({ extended: false })

var identityKey = 'skey';


var socketList={}

var connection = mysql.createConnection({
  host:'localhost',
  user:'root',
  password:'123456789',
  database:'currency_db'
});

var app = express();
var server =http.createServer(app)
io=socket.listen(server)
// all environments
app.engine('html',ejs.__express)
app.set('port',8081);
app.set('views', __dirname + '/views');
app.set('view engine', 'html');
app.use(cookieParser())
app.use(session({
    name: identityKey,
    secret: 'chyingp',  // 用来对session id相关的cookie进行签名
    store: new redisStore({
      host:'localhost',
      port:6379,
      db:"0"
    }),  // 本地存储session（文本文件，也可以选择其他store，比如redis的）
    saveUninitialized: true,  // 是否自动保存未初始化的会话，建议false
    resave: true,  // 是否每次都重新保存会话，建议false
    cookie: {
        maxAge: 100000 * 1000  // 有效期，单位是毫秒
    }
}));
app.use(express.favicon());
app.use(express.logger('dev'));
app.use(express.bodyParser());
app.use(express.methodOverride());
app.use(app.router);
app.use(express.static(path.join(__dirname, 'public')));


//当前在线人数
var onlineCount=0;

io.on('connection',function(socket){
  console.log('一个用户上线了')
  //监听新用户加入
  socket.on("login",function(obj){
    if(socketList[obj.userId]){

    }else{
         console.log("add user")
         socket.userName=obj.userName
         socket.userId=obj.userId
         socket.join('mainRoom')
         socketList[obj.userId]=socket.id
         socket.emit("hello",  "欢迎登陆~")
    }
      console.log(socketList)
     /* console.log(io.sockets.sockets[socketList[obj.userId]])*/
      socket.emit("hello",  "欢迎回来~")
       })

  socket.on("message",function(data){
     var targetId=data.targetId
     var time=data.time
     var content=data.content
     var myName = data.myName
     console.log(targetId+content+time)
     if(socketList[targetId]){
       var targetSocket= socketList[targetId]
        io.sockets.sockets[socketList[targetId]].emit('message',{time:time,content:content,myName:myName})
        socket.emit("messageResponse",  "success~")
     }

  })

})




// development only
if ('development' == app.get('env')) {
  app.use(express.errorHandler());
}
app.get('/', routes.index);
app.post('/getFrendList',urlencodedParser,function(req,res){
  var id=req.session.user[0].id
  connection.query('select us.username, us.header_pic, us.sex,rs.minor_user from relation_ship rs,user us where rs.main_user='+'"'+id+'"'+' and rs.level =4 and rs.minor_user=us.id',function(err,results,xfields){
    res.send(results)
  })
  
})

app.post("/checkStatus",urlencodedParser,function(req,res){
  if(req.session.user){
    res.send(req.session.user)
  }else{
    res.send("null")
  }
  
})

app.post("/login",urlencodedParser,function(req,res){
  var use=req.body.use
  var username=req.body.username
  var password=req.body.password
  var rest;

  if(use=='phone'){
    connection.query('select us.username,us.id,us.header_pic from user us where us.phone="'+username+'" and us.password="'+password+'"',function(err,results,xfields){
     if(results.length!=0){
      req.session.user=results
     }
     res.send(results)
     
    })
  }else{
	  connection.query('select us.username,us.id,us.header_pic from user us where us.email="'+username+'" and us.password="'+password+'"',function(err,results,xfields){
		 if(results.length!=0){
      req.session.user=results
     }
     res.send(results)
		})
  }
})
app.get('/users', user.list);

server.listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});

