# For logging errors
import logging
logging.basicConfig()

# Imports
import sys
from phue import Bridge
import os
from dotenv import load_dotenv, find_dotenv
import math
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
light_id = int(sys.argv[1])

# Set the brightness to 50%
# b.set_light(light_id, 'bri', 25)
# b.set_light(light_id, 'bri', int(sys.argv[1]))

# Set the color to red (using xy coordinates)
# b.set_light(light_id, 'xy', [0.675, 0.322])
b.set_light(light_id, 'on', True)

def rgbToXyBri(r, g, b):
    r /= 255.0
    g /= 255.0
    b /= 255.0
    maxValue = max(r, g, b)
    if maxValue == 0:
        return {'x': 0, 'y': 0, 'bri': 0}
    r /= maxValue
    g /= maxValue
    b /= maxValue
    r = pow(r, 2.4) if r > 0.04045 else r / 12.92
    g = pow(g, 2.4) if g > 0.04045 else g / 12.92
    b = pow(b, 2.4) if b > 0.04045 else b / 12.92
    X = r * 0.649926 + g * 0.103455 + b * 0.197109
    Y = r * 0.234327 + g * 0.743075 + b * 0.022598
    Z = g * 0.053077 + b * 1.035763
    x = X / (X + Y + Z)
    y = Y / (X + Y + Z)
    bri = Y * 255.0

    return [x, y, round(bri)]

R = int(sys.argv[2])
G = int(sys.argv[3])
B = int(sys.argv[4])

xy = rgbToXyBri(R, G, B)

# b.set_light(light_id, 'bri', xy[2])
b.set_light(light_id, 'xy', [xy[0], xy[1]])

json_object = json.dumps({ "status": "Changed"})
print(json_object)

sys.stdout.flush()