-- init random
math.randomseed(os.time())
-- the request function that will run at each request
request = function()
    local url_path = "/api/users/login?login=" .. "user" .. math.random(100)
    return wrk.format("GET", url_path)
end