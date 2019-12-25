const express= require("express");
const app=express();
var bodyParser = require("body-parser");
app.use(bodyParser.json()); 

const config=require("./Configuration/config");

app.listen(config.app.port,()=>{
    console.log(`server start on ${config.app.port}`);
});