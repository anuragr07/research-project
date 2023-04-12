const express = require('express')
const router = express.Router()
const {spawn} = require('child_process');
const { error } = require('console');

const scriptPath = 'Routes/ShellyRoute/ShellyScripts/';

// ------ Code to run the shelly scripts -------
router.get('/', (req, res) => {
    // this is to get the status of the light
    // might include some data as well in it
    
})

// TODO: Use these funcions along with the id of the device from db
// ---------------- TURN ON/OFF ---------------------
router.get('/toggle', (req, res) => {
    // Script path
    const toggleScriptPath = scriptPath + 'ShellyToggle.py'

    // Script vars
    // const lightId = req.params.id;
    
    // create list of vars
    let scriptVarsList = [toggleScriptPath]
    
    // Run script
    runScript(scriptVarsList)
    .then((response) => {
        // Send Response
        res.send(response)
    })
})

router.get('/on/:plug', (req, res) => {
    // Script path
    const turnOnScriptPath = scriptPath + 'ShellyOn.py'

    // Script vars - plug name
    const plugName = req.params.plug
    
    // create list of vars
    let scriptVarsList = [turnOnScriptPath, plugName]
    
    // Run script
    runScript(scriptVarsList)
    .then((response) => {
        // Send Response
        res.send(response)
    })    
    .catch((error) => {
        res.send(error)
    })
})

router.get('/off/:plug', (req, res) => {
    // Script path
    const turnOffScriptPath = scriptPath + 'ShellyOff.py'

    // Script vars - plug name
    const plugName = req.params.plug
    
    // create list of vars
    let scriptVarsList = [turnOffScriptPath, plugName]
    
    // Run script
    runScript(scriptVarsList)
    .then((response) => {
        // Send Response
        res.send(response)
    })    
    .catch((error) => {
        res.send(error)
    })
})

router.get('/printLines', (req, res) => {
    // Script path
    const turnOffScriptPath = scriptPath + 'ShellyEnergy.py'
    

    // Script vars
    // const lightId = req.params.id;
    
    // create list of vars
    let scriptVarsList = [turnOffScriptPath]
    
    // Run script
    runScript(scriptVarsList)
    .then((response) => {
        // Send Response
        res.send(response)
    })
    .catch((error) => {
        res.status(401).send(error)
    })
})

// Change this according to the id passed
router.get('/status/:name', (req, res) => {
    // Script path
    const configScriptPath = scriptPath + 'ShellyGetConfig.py'

    const plugName= req.params.name;

    // Script vars
    let scriptVarsList = [configScriptPath, plugName]

    // Run script
    runScript(scriptVarsList)
    .then((response) => {
        // Send response
        res.setHeader('Content-Type', 'application/json')
        res.send(response)
    })
})
// ------------------- TURN ON/OFF ENDS ---------------


// Run the python script
function runScript(scriptParamsList) {
    return new Promise((resolve, reject) => {
      const script = spawn('python3', scriptParamsList);
      let responseFromScript = '';
  
      script.stdout.on('data', (data) => {
        responseFromScript += data.toString();
      });
  
      script.stderr.on('data', (data) => {
        console.error(`stderr: ${data}`);
        reject(data);
      });
  
      script.on('close', (code) => {
        console.log(`child process exited with code ${code}`);
        resolve(responseFromScript);
      });
    });
}


module.exports = router