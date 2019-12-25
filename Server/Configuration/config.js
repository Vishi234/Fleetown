const mongoose = require('mongoose');
mongoose.set('useFindAndModify', false);
const config = {
    app: {
        port: 5000
    },
    db: {
        host: 'localhost',
        port: 27017,
        name: 'fleetown'
    }
};

mongoose.set('useNewUrlParser', true);
mongoose.set('useCreateIndex', true);
const { db: { host, port, name } } = config;
const connectionString = `mongodb://${host}:${port}/${name}`;

const options = {
    useNewUrlParser: true,
    useCreateIndex: true,
    useFindAndModify: false,
    autoIndex: false, // Don't build indexes
    reconnectTries: Number.MAX_VALUE, // Never stop trying to reconnect
    reconnectInterval: 500, // Reconnect every 500ms
    poolSize: 10, // Maintain up to 10 socket connections
    bufferMaxEntries: 0,
    connectTimeoutMS: 10000, // Give up initial connection after 10 seconds
    socketTimeoutMS: 45000, // Close sockets after 45 seconds of inactivity
    family: 4 // Use IPv4, skip trying IPv6
};
mongoose.connect(connectionString, options);
module.exports = config;