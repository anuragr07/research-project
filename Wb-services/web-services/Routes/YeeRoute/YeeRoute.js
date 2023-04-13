const express = require('express')
const router = express.Router()
const {spawn} = require('child_process')
const Profile = require('../../Model/Profile')
const scriptPath = 'Routes/YeeRoute/YeeScripts/';

// --- Code to run the scripts ---
router.get('/', (req, res) => {
    // this is to get the status of the light
    // might include some data as well in it
    
})

router.post('/toggle/:user/:name', (req, res) => {
    // Script path
    const toggleScriptPath = scriptPath + 'yeeToggle.py'

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

router.get('/props/:name', (req, res) => {
  // Script path
  const getPropsScriptPath = scriptPath + 'yeeGetProperties.py'

  // Script vars
  const lightName = req.params.name
  
  // create list of vars
  let scriptVarsList = [getPropsScriptPath, lightName]
  
  // Run script
  runScript(scriptVarsList)
  .then((response) => {
      // Send Response
      res.setHeader('Content-Type', 'application/json')
      res.send(response)
  })
})


router.post('/color/:user/:name/:r/:g/:b', async (req, res) => {
    // Script path
    const changeColorScriptPath = scriptPath + 'yeeColorChange.py'
    
    const R = parseInt(req.params.r);
    const G = parseInt(req.params.g);
    const B = parseInt(req.params.b);

    const roomName = req.params.room;
    const deviceName = req.params.name;
    const userId = req.params.user;

    
    // Script vars
    // const lightId = req.params.id;
    const rgbv = [R, G, B]
    
    // create list of vars
    let scriptVarsList = [changeColorScriptPath, rgbv]
  
    
    // Run script
    runScript(scriptVarsList)
    .then((response) => {
        // Send Response
        res.setHeader('Content-Type', 'application/json')
        res.send(response)
    })
})

router.post('/bright/:user/:name/:value', (req, res) => {
    // Script path
    const brightnessScriptPath = scriptPath + 'yeeBrightnessChange.py'
    
    // Script vars
    // const lightId = req.params.id;
    const bright = req.params.value
    
    // create list of vars
    let scriptVarsList = [brightnessScriptPath, bright]
    
    // Run script
    runScript(scriptVarsList)
    .then((response) => {
        // Send Response
        res.setHeader('Content-Type', 'application/json')
        res.send(response)
    })
})

router.post('/setScene', (req, res) => {

    // run the script to set scene of the room

})



// This code will be used to get the status of specific yee light, with id
router.get('/:id', (req, res) => {
    let id = req.params.id;

    // do stuff with the id of the yee light


})

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


// --- Code for CRUD operations of the yee light ---


module.exports = router