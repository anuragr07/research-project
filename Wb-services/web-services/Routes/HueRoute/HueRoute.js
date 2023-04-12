const express = require('express')
const router = express.Router()
const {spawn} = require('child_process');
const { response } = require('express');

const scriptPath = 'Routes/HueRoute/HueScripts/';

router.get('/props', (req, res) => {
    // TODO: return status of the light(s) in res
    const propScriptPath = scriptPath + "hueGetProperties.py";
    
    let scriptVarsList = [propScriptPath]
    
    runScript(scriptVarsList)
    .then((response) => {
        res.setHeader('Content-Type', 'application/json')
        res.send(response)
    })
})

router.get('/props/:name', (req, res) => {
    // TODO: return status of the light(s) in res
    const propScriptPath = scriptPath + "hueGetProperties.py";
    const name = req.params.name

    let scriptVarsList = [propScriptPath, name]

    runScript(scriptVarsList)
    .then((response) => {
        res.setHeader('Content-Type', 'application/json')
        res.send(response)    
    })
})

// Turn on all lights
router.get('/allOn', (req, res) => {
    // Script path
    const allOnScriptPath = scriptPath + 'hueAllOn.py';
    
    // Script vars
    // No vars needed

    // create list of vars
    let scriptVarsList = [allOnScriptPath]
    
    // Run script
    runScript(scriptVarsList)
    .then((response) => {
        // Send Response
        res.send(response)
    })
})

// Turn off all lights
router.get('/allOff', (req, res) => {
    // Script path
    const allOffScriptPath = scriptPath + 'hueAllOff.py';
    
    // Script vars
    // No vars needed

    // create list of vars
    let scriptVarsList = [allOffScriptPath]
    
    // Run script
    runScript(scriptVarsList)
    .then((response) => {
        // Send Response
        res.send(response)
    })
    
})

// Set Color Route -
// this should set the brightness and color
router.post('/bright/:name/:value', (req, res) => {
    // Script path
    const setColorScriptPath = scriptPath + 'hueColor.py';

    const briPercent = req.params.value;
    const lightName = req.params.name;
    
    // Script vars
    const briVal = Math.round(percent / 100 * 255)
    let lightId;

    switch (lightName) {
        case "Hue-Light-1":
            lightId = 2;
            
            break;
        case "Hue-Light-2":
            lightId = 3;
            
            break;
        case "Hue-Light-1":
            lightId = 1;
            
            break;

        default:
            break;
    }

    // arg 1 - Brightness value, arg 2 - light id
    // create list of vars
    let scriptVarsList = [setColorScriptPath, briVal, lightId]
    
    // Run script
    runScript(scriptVarsList)
    .then((response) => {
        // Send Response
        res.setHeader('Content-Type', 'application/json')
        res.send(response)
    })
})

// Toggle all lights
router.get('/toggle', (req, res) => {
    // Script path
    const toggleScriptPath = scriptPath + 'hueAllToggle.py'

    // Script vars
    // No vars needed

    // create list of vars
    let scriptVarsList = [toggleScriptPath]
    
    // Run script
    runScript(scriptVarsList)
    .then((response) => {
        // Send Response
        res.send(response)
    })
})

// Toggle one lights
router.get('/toggle/:id', (req, res) => {
    // Script path
    const toggleScriptPath = scriptPath + 'hueToggle.py'

    // Script vars
    const lightId = req.params.id;

    // create list of vars
    let scriptVarsList = [toggleScriptPath, lightId]
    
    // Run script
    runScript(scriptVarsList)
    .then((response) => {
        // Send Response
        res.send(response)
    })
})

// Turn on one light
router.post('on/:name', (req, res) => {
    // Script path
    const onScriptPath = scriptPath + 'hueOn.py'

    // Script vars
    let lightId;

    const lightName = req.params.name;
    
    switch (lightName) {
        case "Hue-Light-1":
            lightId = 2;
            
            break;
        case "Hue-Light-2":
            lightId = 3;
            
            break;
        case "Hue-Light-1":
            lightId = 1;
            
            break;

        default:
            break;
    }

    // create list of vars
    let scriptVarsList = [onScriptPath, lightId]
    
    // Run script
    runScript(scriptVarsList)
    .then((response) => {
        // Send Response
        res.send(response)
    })
})

router.post('off/:name', (req, res) => {
    // Script path
    const onScriptPath = scriptPath + 'hueOff.py'

    // Script vars
    let lightId;

    const lightName = req.params.name;
    
    switch (lightName) {
        case "Hue-Light-1":
            lightId = 2;
            
            break;
        case "Hue-Light-2":
            lightId = 3;
            
            break;
        case "Hue-Light-1":
            lightId = 1;
            
            break;

        default:
            break;
    }

    // create list of vars
    let scriptVarsList = [onScriptPath, lightId]
    
    // Run script
    runScript(scriptVarsList)
    .then((response) => {
        // Send Response
        res.send(response)
    })
})


// Run the python script
function runScript(scriptParamsList) {
    return new Promise((resolve, reject) => {
      const script = spawn('python', scriptParamsList);
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