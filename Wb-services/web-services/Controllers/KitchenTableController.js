const KitchenTable = require("../Model/KitchenTable")
const fetch = require("node-fetch")
const API_IP = "174.6.73.177:3000"
const LOCAL = "localhost:3000"

// This will return latest 3 log enteries
exports.getLatestKitchenTableEntries = async (req, res) => {
    try {
        const totalConsumption = await KitchenTable.find()
        let consumptionList = []
        let lastConsumption = 0;

        totalConsumption.forEach((item, i) => {
            
            // User's current consumption
            let consumption = item.total_energy - lastConsumption 
            
            if(item.username === req.params.username) {
                energyObj = {
                    username: item.username,
                    consumption: consumption
                }

                // Append the obj to the list
                consumptionList.push(energyObj)
            }

            // Store last user's consumption
            lastConsumption = item.total_energy
        });

        const latestEnteries = consumptionList.slice(-3)

        // console.log(energyUsed);
        res.status(200).json(latestEnteries);
    } catch (err) {
        console.error(err);
        res.status(500).send('Server Error');
    }
};

// This will return total consumption of every user
exports.getTotalEnergyAllUsers = async (req, res) => {
    try {
        const totalConsumption = await KitchenTable.find()
        let energyUsed = []
        let energyObj = {}
        let lastConsumption = 0;

        totalConsumption.forEach((item, i) => {
            
            // User's current consumption
            let consumption = item.total_energy - lastConsumption 
            
            // Find user's obj
            energyObj = energyUsed.find(energyItem => {
                return item.username === energyItem.username
            })
            
            // Declare updated obj
            let updatedObj = {}

            // Check and update the obj with consumption
            if(energyObj) {
                // Update object
                energyUsed = energyUsed.map(obj => {
                    if(energyObj.username === obj.username){
                        return {
                            ...obj,
                            consumption: obj.consumption + consumption
                        }
                    } else {
                        return obj
                    }                        
                })
            } else {

                // Return new opject
                updatedObj = {
                    username: item.username,
                    consumption: consumption
                }
                energyUsed.push(updatedObj)
            }
            
            // Store last user's consumption
            lastConsumption = item.total_energy
        });

        // console.log(energyUsed);
        res.status(200).json(energyUsed);
    } catch (err) {
        console.error(err);
        res.status(500).send('Server Error');
    }
};

// Turn on the shelly switch
exports.turnOnKitchen = async (req, res) => {
    try {
        // Run on script
        const response = await fetch(`http://${API_IP}/shelly/on/Shelly%20Smart%20Plug-1`);
        try {
            const data = await response.json()
            res.status(200).send(data)
            console.log(data);   
        } catch (error) {
            console.log("Inside" + error);
        }

        // Submit data on profile
    } catch (error) {
        console.log(error);
        res.status(500).send("Error while turning on kitche")
    }
}

// Turn off the shelly switch
exports.turnOffKitchen = async (req, res) => {
    try {

        let status;
        let config;

        // Run off script
        const response = await fetch(`http://${API_IP}/shelly/off/Shelly%20Smart%20Plug-1`);
        try {
            const data = await response.json()
            status = data;
        } catch (error) {
            console.log("Inside" + error);
        }

        // Run get config script
        const configRes = await fetch(`http://${API_IP}/shelly/status/Shelly%20Smart%20Plug-1`);
        try {
            const data = await configRes.json()
            config = data;
        } catch (error) {
            console.log("Inside" + error);
        }

        let total_energy = config.settings.total_energy

        await KitchenTable.create({
            username: req.params.username,
            status: "Off",
            total_energy: total_energy
        },
            function (err, table) {
                if (err) return res.status(500).send({ mresponsesg: false });
                res.status(200).send(status);
            }
        )
        
        // Submit data on profile
    } catch (error) {
        console.log(error);
        res.status(500).send("Error while turning off kitchen")
    }
}
