-- init random
math.randomseed(os.time())
-- the request function that will run at each request
request = function()
    local array = {
        "а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "й", "к", "л", "м", "н", "о", "п", "р",
        "с", "т", "у", "ф", "х", "ц", "ч", "ш", "щ", "ъ", "ы", "ь", "э", "ю", "я"
    }
    -- define the path that will search for q=%v 9%v being a random number between 0 and 1000)
    local queryStr = array[math.random(32)] .. array[math.random(32)] .. array[math.random(32)]
    local url_path = "/api/users/search?firstName=" .. encodeURI(queryStr) .. "&lastName=" .. encodeURI(queryStr)
    -- if we want to print the path generated
--    print(url_path)
    -- Return the request object with the current URL path
    return wrk.format("GET", url_path)
end

function encodeURI(str)
    if (str) then
        str = string.gsub (str, "\n", "\r\n")
        str = string.gsub (str, "([^%w ])",
            function (c) return string.format ("%%%02X", string.byte(c)) end)
        str = string.gsub (str, " ", "+")
    end
    return str
end