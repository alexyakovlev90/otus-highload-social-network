-- Tarantool configuration
-- Create one space for replication daemon purposes. This space will store the current replication state.
-- Create more spaces to store replicated data.
-- Create a user for the replication daemon and enable read/write operations on target spaces.
-- box.schema.user.create('test', { password = 'test' })
-- box.schema.user.grant('test', 'execute,received,write', 'universe')

function start()
    box.schema.space.create('user_space', { if_not_exists = true })
    box.space.user_space:create_index('primary', { type = "TREE", unique = true, parts = { 1, 'unsigned' }, if_not_exists = true })
    box.space.user_space:create_index('login_idx', { type = 'HASH', unique = true, parts = { 2, 'string' }, if_not_exists = true })
    box.space.user_space:create_index('first_name_idx', { type = 'TREE', unique = false, parts = { 3, 'string' }, if_not_exists = true })
    box.space.user_space:create_index('last_name_idx', { type = 'TREE', unique = false, parts = { 4, 'string' }, if_not_exists = true })
end

--return {
--    start = start;
--}

function search_by_first_name(prefix)
    local result = {}
    for _, tuple in box.space.user_space.index.first_name_idx:pairs({ prefix }, { iterator = 'GE' }) do
        if string.startswith(tuple[3], prefix, 1, -1) then
            table.insert(result, tuple)
        end
    end
    return result
end

function search(prefix, size)
    local count = 0
    local result = {}
    for _, tuple in box.space.user_space.index.first_name_idx:pairs({ prefix }, { iterator = 'GE' }) do
        if string.startswith(tuple[3], prefix, 1, -1) then
            table.insert(result, tuple)
            count = count + 1
            if count >= size then
                return result
            end
        else
            break
        end
    end

    for _, tuple in box.space.user_space.index.last_name_idx:pairs({ prefix }, { iterator = 'GE' }) do
        if string.startswith(tuple[4], prefix, 1, -1) then
            table.insert(result, tuple)
            count = count + 1
            if count >= size then
                return result
            end
        else
            break
        end
    end
    return result
end
