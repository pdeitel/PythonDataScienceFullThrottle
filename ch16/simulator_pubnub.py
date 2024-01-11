# simulator_pubnub.py
"""A connected thermostat simulator that publishes JSON
messages using PubNub"""
import keys
from pubnub.pnconfiguration import PNConfiguration
from pubnub.pubnub import PubNub
from pubnub.exceptions import PubNubException
import random
import sys
import time

MIN_CELSIUS_TEMP = -25  
MAX_CELSIUS_TEMP = 45 
MAX_TEMP_CHANGE = 2

# get the number of messages to simulate and delay between them
NUMBER_OF_MESSAGES = int(sys.argv[1]) 
MESSAGE_DELAY = int(sys.argv[2])

# PubNub configuration
config = PNConfiguration()
config.user_id = keys.pubnub_user_id
config.publish_key = keys.pubnub_publish_key 
config.subscribe_key = keys.pubnub_subscribe_key 
pubnub = PubNub(config)

channel = 'deitel-thermostat-simulator'  # provide a unique name

thermostat = {'Location': 'Home',
              'Temperature': 20, 
              'Too_Low': False,
              'Too_High': False}

print('Temperature simulator starting')

def publish_callback(envelope, status):
    # Handle PUBLISH response and status
    if status.is_error():
        print(f'Failed to publish message: {status}')

for message in range(1, NUMBER_OF_MESSAGES + 1):
    # generate a random number in the range -MAX_TEMP_CHANGE 
    # through MAX_TEMP_CHANGE and add it to the current temperature
    thermostat['Temperature'] += random.randrange(
        -MAX_TEMP_CHANGE, MAX_TEMP_CHANGE + 1)
    
    # ensure that the temperature stays within range
    if thermostat['Temperature'] < MIN_CELSIUS_TEMP:
        thermostat['Temperature'] = MIN_CELSIUS_TEMP
    
    if thermostat['Temperature'] > MAX_CELSIUS_TEMP:
        thermostat['Temperature'] = MAX_CELSIUS_TEMP
    
    # check for low temperature warning
    if thermostat['Temperature'] < 3:
        thermostat['Too_Low'] = True
    else:
        thermostat['Too_Low'] = False

    # check for high temperature warning
    if thermostat['Temperature'] > 35:
        thermostat['Too_High'] = True
    else:
        thermostat['Too_High'] = False

    # Publish the message to PubNub
    try:
        pubnub.publish().channel(channel).message(thermostat).pn_async(publish_callback)
    except PubNubException as e:
        print(f"An error occurred: {e}")
        sys.exit(1)
    else:
        print(f'Message {message}: {thermostat}\r', end='')
        
    time.sleep(MESSAGE_DELAY)

print('\nTemperature simulator finished')
