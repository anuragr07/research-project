# For logging errors
import logging
logging.basicConfig()

# imports
from yeelight import Bulb
from yeelight import discover_bulbs
from Yee_Attributes import YeeAttributes
import sys
import json

number_of_yee_bulbs=discover_bulbs()

first_bulb=number_of_yee_bulbs[0]
ip_address=first_bulb["ip"] 

bulb = Bulb(ip_address, auto_on=True)
attributes=YeeAttributes()

def changeBrightness(b):
    bulb.set_brightness(b)

bulb.turn_on()
changeBrightness(int(sys.argv[1]))


json_object = json.dumps({"status": "Changed"})
print(json_object)

sys.stdout.flush()
