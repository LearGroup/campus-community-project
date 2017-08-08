
/**
 * Module dependencies.
 */

var express = require('express')
  , routes = require('./routes')
  , user = require('./routes/user')
  , http = require('http')
  , io = require('socket.io')(http)
  , ejs = require('ejs')
  , mysql =require('mysql')
  , bodyParser = require('body-parser')
  , path = require('path');

// 创建 application/x-www-form-urlencoded 编码解析
var urlencodedParser = bodyParser.urlencoded({ extended: false })


var connection = mysql.createConnection({
  host:'localhost',
  user:'root',
  password:'123456789',
  database:'currency_db'
});



//当前在线人数
var onlineCount=0;

io.on('connection',function(socket){
	console.log('a user connected')

	//监听新用户加入
	socket.on("login",function(obj){
		socket.name=obj.userName
		socket.id=obj.userId
		onlineCount=io.sockets.sockets
    

	})
})


var app = express();

// all environments
app.engine('html',ejs.__express)
app.set('port',8081);
app.set('views', __dirname + '/views');
app.set('view engine', 'html');
app.use(express.favicon());
app.use(express.logger('dev'));
app.use(express.bodyParser());
app.use(express.methodOverride());
app.use(app.router);
app.use(express.static(path.join(__dirname, 'public')));

// development only
if ('development' == app.get('env')) {
  app.use(express.errorHandler());
}
app.get('/', routes.index);
app.post('/getFrendList',urlencodedParser,function(req,res){
  var id=req.body.id
   connection.query('select us.username, us.header_pic, us.sex,rs.minor_user from relation_ship rs,user us where rs.main_user='+'"'+id+'"'+' and rs.level =4 and rs.minor_user=us.id',function(err,results,xfields){
     res.send(results)
   })
  
})
app.get('/users', user.list);

http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});
