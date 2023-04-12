# For logging errors
import logging
logging.basicConfig()

# Imports
import sys
from yeelight import Bulb
from yeelight import discover_bulbs
from Yee_Attributes import YeeAttributes
import json

number_of_yee_bulbs=discover_bulbs()

first_bulb=number_of_yee_bulbs[0]
ip_address=first_bulb["ip"] 

# ip_address=number_of_yee_bulbs[int(sys.argv[1])]["ip"]

bulb = Bulb(ip_address, auto_on=True)
attributes=YeeAttributes()

def setColor(x):
    values = x.split(',')
    bulb.set_rgb(int(values[0]), int(values[1]), int(values[2]))

bulb.turn_on()
# setColor(attributes.rgbv)

setColor(sys.argv[1])

json_object = json.dumps({"status": "Changed"})
print(json_object)

sys.stdout.flush()
