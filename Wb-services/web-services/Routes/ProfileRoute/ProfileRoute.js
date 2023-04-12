const express = require('express')
const router = express.Router()
const ProfileController = require("../../Controllers/ProfileController")

router.get("/:id", ProfileController.getProfileByUserId)
router.get("/:id/:roomname", ProfileController.getRoomByUserId )
router.get("/:id/:roomname/:device", ProfileController.getDeviceByRoom )
router.post("/:id/:roomname/:device/:status", ProfileController.updateDeviceStatus)

module.exports = router