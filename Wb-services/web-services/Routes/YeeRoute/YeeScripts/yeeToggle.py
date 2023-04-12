# imports
from yeelight import discover_bulbs
from yeelight import Bulb
from Yee_Attributes import YeeAttributes
import sys
import json

# For env variable
import os
from dotenv import load_dotenv, find_dotenv

load_dotenv(find_dotenv())

# For logging errors
import logging
logging.basicConfig()


number_of_yee_bulbs=discover_bulbs()

# first_bulb=number_of_yee_bulbs[0]
# ip_address=first_bulb["ip"] 

ip_address=os.getenv('YEE_IP')

# ip_address=number_of_yee_bulbs[int(sys.argv[1])]["ip"]

bulb = Bulb(ip_address, auto_on=True)

power_state = bulb.get_properties()["power"]

    # If the bulb is on, turn it off
if power_state == "on":
        bulb.turn_off()
        power_state = "off"
    # If the bulb is off, turn it on
else:
        bulb.turn_on()
        power_state = "on"

json_object = json.dumps({"status": power_state})
print(json_object)

sys.stdout.flush()