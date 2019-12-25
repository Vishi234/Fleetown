var mongoose = require('mongoose');
var Schema = mongoose.Schema , ObjectId = Schema.ObjectId;

var registerSchema = new Schema({
    Name: {type: String},
    Mobile: {type: String, unique: true, require: true},
    vechileType: {type: String, require: true},
    userType: {type: String,  require: true},
    loginAttemp: {type: Number},
    lastLoginTime:{type: Date},
    image:{type: String}
    })

    var register = mongoose.model('driverRegister', registerSchema);

    module.exports={
        register:register
    }
