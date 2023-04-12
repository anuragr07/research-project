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

# Get the ID of the light you want to control
# light_id = 1
light_id = int(sys.argv[2])

b.set_light(light_id, 'on', True)

# Set the brightness to 50%
# b.set_light(light_id, 'bri', 25)
b.set_light(light_id, 'bri', int(sys.argv[1]))

# Set the color to red (using xy coordinates)
# b.set_light(light_id, 'xy', [0.675, 0.322])

json_object = json.dumps({ "status": "Changed"})
print(json_object)

sys.stdout.flush()