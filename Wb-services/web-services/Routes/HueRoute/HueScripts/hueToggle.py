# For logging errors
import logging
logging.basicConfig()

# Imports
import sys
from phue import Bridge
import os
from dotenv import load_dotenv, find_dotenv
import json

load_dotenv(find_dotenv())

# IP address of your Philips Hue bridge
bridge_ip = os.getenv('HUE_IP')

# API username
api_username = 'HVmL3Zatw2OdLFfUtpfp9Em33HAoganA59w0vaDj'

# Connect to the bridge with the API username
b = Bridge(bridge_ip, username=api_username)

# Get the ID of the light you want to turn on
# light_id = 1
light_id = int(sys.argv[1])

light_state = b.get_light(light_id)

try:
    # If the bulb is on, turn it off
    if light_state['state']['on']:
        b.set_light(light_id, 'on', False)
        json_object = json.dumps({ "status": "Off"})
        print(json_object)
    # If the bulb is off, turn it on
    else:
        b.set_light(light_id, 'on', True)
        json_object = json.dumps({ "status": "On"})
        print(json_object)
except Exception as e:
    # Handle the error here
    print("Error occurred:" + e)

sys.stdout.flush()