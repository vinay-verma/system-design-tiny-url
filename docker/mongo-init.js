db = db.getSiblingDB('admin');
db.auth("root", "password");
db = db.getSiblingDB('tinyurldb');
db.createUser({
    user: "tinyuser",
    pwd: "password",
    roles: [
        {
            role: "dbOwner",
            db: "tinyurldb"
        }
    ]
});
db.createCollection('system_log');
db.system_log.insert({ msg: 'initialised' });
