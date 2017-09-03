redisClient.get('sess:j0XwDtg5nNhFIgDcjC8B7ViYTsd-wh4j', function(err, res) {
  if (err) {
    console.log(err);
  } else {
    console.log(res);
  }
})
    redisClient.rename('sess:' + req.sessionID, 'sess:test')



    if (results) {
      console.log('sess:' + results[0].id);
      //获取对应用户的缓存资源
      redisClient.get('sess:' + results[0].id, function(err, res) {
        if (err) {
        } else {
          //若不存在缓存资源则创建一份缓存资源
          if (res == null) {
            req.sessionID=results[0].id
            req.session.user = results
            console.log('login reqSessionID');
            console.log(req.sessionID);
            response.send(results)
            console.log('1');
            redisClient.hmset('res:' + results[0].id, results[0],function(err, res) {

            })
          } else {
            redisClient.del('sess:' + req.sessionID, function(err, res) {
              redisClient.get('sess:' + results[0].id, function(err, res) {
                if (res) {
                  req.session = res.session = res
                  console.log('objs2');
                  console.log(req.session);

                }
              })
            })
          }

        }
      })
    }



    redisClient.hgetall('userCache:' + results[0].id, function(err, res) {
      console.log('get res');
      console.log(res);
      console.log(err);
      req.session.userCache = res
      response.send(results[0])
    })
