--获取key
local key = KEYS[1]
local val = redis.call("GET", key)

if(val>0) then
    redis.call("DECR", key)
    return true
end
return false