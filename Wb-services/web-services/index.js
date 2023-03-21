const express = require('express')
const app = express()
const {spawn} = require('child_process')

const { MongoClient } = require('mongodb');

const uri = "mongodb+srv://arpiegrover:Arpie1996@picluster.u3qwjlc.mongodb.net/SmartSpace?retryWrites=true&w=majority";
const client = new MongoClient(uri, { useUnifiedTopology: true ,ssl: true});

const mongoose = require('mongoose')


const YeeRouter = require("./Routes/YeeRoute/YeeRoute")
const HueRouter = require("./Routes/HueRoute/HueRoute")

app.use('/yee', YeeRouter)
app.use('/hue', HueRouter)

app.listen(3000, function () {
    try {
        mongoose.connect(uri, {useNewUrlParser: true, useUnifiedTopology: true})
        console.log("Connected");
    } catch (error) {
        console.error(`Failed to connect to MongoDB ${error}`);
    }
})
