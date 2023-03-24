import requests
import json

# set the IP address of your Shelly device
shelly_ip = "10.0.0.2"

# set the URL for the Switch.Set method
url = f"http://{shelly_ip}/rpc"

# create the JSON request body
payload = {
    "id": 1,
    "method": "Switch.Set",
    "params": {
        "id": 0,
        "on": True
    }
}
# send the HTTP PUT request to turn on the device
response = requests.put(url, json.dumps(payload))

# check the response status code
if response.status_code == 200:
    print("Device turned on")
else:
    print("Failed to turn on device")