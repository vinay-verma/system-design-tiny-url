db = db.getSiblingDB('tinyurldb');
db.createUser({
    user: "tinyuser",
    pwd: "password",
    roles: [
        {
            role: "userAdmin",
            db: "tinyurldb"
        }
    ]
});
db.createCollection('system_log');
db.system_log.insert({ msg: 'initialised' });
