from phue import Bridge
import os
from dotenv import load_dotenv, find_dotenv
import sys
import json

# Convert CIE xy color to RGB
load_dotenv(find_dotenv())

# IP address of your Philips Hue bridge
bridge_ip = os.getenv('HUE_IP')

# API username
api_username = 'HVmL3Zatw2OdLFfUtpfp9Em33HAoganA59w0vaDj'

# Connect to the bridge with the API username
b = Bridge(bridge_ip, username=api_username)

# Get the ID of the light you want to turn on
lights=b.get_light_objects()

lightList=[]
props="";

if len(sys.argv) > 1:
    lightName = sys.argv[1]
else:
    lightName = ""

printFlag = False

# Function to convert XY values to rgb
def xyBriToRgb(x, y, bri):
    z = 1.0 - x - y
    Y = bri / 255.0 # Brightness of lamp
    X = (Y / y) * x
    Z = (Y / y) * z
    r = X * 1.612 - Y * 0.203 - Z * 0.302
    g = -X * 0.509 + Y * 1.412 + Z * 0.066
    b = X * 0.026 - Y * 0.072 + Z * 0.962
    r = 12.92 * r if r <= 0.0031308 else (1.0 + 0.055) * pow(r, 1.0 / 2.4) - 0.055
    g = 12.92 * g if g <= 0.0031308 else (1.0 + 0.055) * pow(g, 1.0 / 2.4) - 0.055
    b = 12.92 * b if b <= 0.0031308 else (1.0 + 0.055) * pow(b, 1.0 / 2.4) - 0.055
    maxValue = max(r, g, b)
    r /= maxValue
    g /= maxValue
    b /= maxValue
    r *= 255; r = 255 if r < 0 else r
    g *= 255; g = 255 if g < 0 else g
    b *= 255; b = 255 if b < 0 else b
    return [int(round(r)), int(round(g)), int(round(b))]


try:
    # Print the properties of each light
    for light in lights:   
        
        # convert xy to rgb values
        rgb = xyBriToRgb(light.xy[0], light.xy[1], light.brightness)
        
        # get device id method
        def getDeviceId(argument):
            if argument == 'Hue Light-1':
                device_id = 3
            elif argument == 'Hue Light-2':
                device_id = 4
            elif argument == 'Hue Light-3':
                device_id = 5
            else:
                device_id = 3
            return device_id

        if light.on:
            status = "On"
        if light.on != True:
            status = "Off"

        # props of hue light
        props = {
            "device_id": getDeviceId(light.name),
            "device_name": light.name,
            "status": status,
            "ip": bridge_ip,
            "settings": {
                "Brightness": light.brightness,
                "rgbv": rgb
            }
        }

        print(light.light_id)

        # props = props + "{name:" + light.name
        # props = props + ",status:" + str(light.on)
        # props = props + ",ip:" + bridge_ip
        # props = props + ",brightness:" + str(light.brightness)
        # # convert xy to rgb
        # rgb = xyBriToRgb(light.xy[0], light.xy[1], light.brightness)
        # props = props + ",rgb:" + str(rgb)
        # props = props + ",color_x:" + str(light.xy[0])
        # props = props + ",color_y:" + str(light.xy[1])
        # props = props + ",light_id: " + str(light.light_id) + "}"

        
        if light.name == lightName:
            #Convert dictionary to JSON
            json_object = json.dumps(props)
            print(json_object)
            
            printFlag = True
        else:
            lightList.append(props)
        
    if printFlag == False:
        json_object = json.dumps(lightList)
        print(json_object)

except Exception as e:
    # Handle the error here
    # print(f"Error occurred: {e}")
    print("Error: " + str(e))

# print(xy_to_rgb([0.4325, 0.1932]))


