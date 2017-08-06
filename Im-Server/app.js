
/**
 * Module dependencies.
 */

var express = require('express')
  , routes = require('./routes')
  , user = require('./routes/user')
  , http = require('http')
  , io = require('socket.io')(http)
  , ejs = require('ejs')
  , path = require('path');



//在线用户
var onlineUserList={};
//当前在线人数
var onlineCount=0;

io.on('connection',function(socket){
	console.log('a user connected')

	//监听新用户加入
	socket.on("login",function(obj){
		socket.name=obj.userId

		//检查在线列表，如果不在就加入
		if(!onlineUserList.hasOwnProperty(obj.userId)){
			onlineUserList[obj.userId]=obj.userName
			//在线人数+1
			onlineCount++;
		}
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
app.get('/users', user.list);

http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});
