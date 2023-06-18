# tweetutilities.py
"""Utility functions for interacting with Tweepy objects."""
from better_profanity import profanity
from bs4 import BeautifulSoup
import deepl
from geopy import ArcGIS
import keys_mastodon
import preprocessor as p
import requests
import socket
import time 
from urllib.parse import urlparse
from IPython.core.display import display, HTML

# load censored words list
profanity.load_censor_words()

# translator to autodetect source language and return English
translator = deepl.Translator(keys_mastodon.deepL_key)

# tweet-preprocessor remove @ mentions, emojis, hashtags, URLs
p.set_options(p.OPT.MENTION, p.OPT.EMOJI, p.OPT.HASHTAG, p.OPT.URL)

def print_toot(toot):
    """Prints one toot's username and content (translated, if not English)."""
    
    # display the username of the sender 
    print(f'{toot.account.username}:')

    # display toot text
    if toot.language and toot.language.startswith('en'):
        # render HTML version of toot in an HTML div indented 0.5 inches
        text = profanity.censor(toot.content)
        display(HTML(f'<div style="padding-left: 0.5in;">{text}</div>'))

    # if the language is not English, display original & translated text 
    else:            
        # render HTML version of toot in an HTML div indented 0.5 inches
        print(f'ORIGINAL: ')
        display(HTML(f'<div style="padding-left: 0.5in;">{toot.content}</div>'))

        # remove HTML
        soup = BeautifulSoup(toot.content, 'html.parser') 
        content = soup.get_text() # remove all HTML/CSS tags and commands
        
        # translate content
        try:
            result = translator.translate_text(content, target_lang='en-us')
            text = profanity.censor(result.text)
        except:
            text = 'toot was empty'   

        # render HTML version of toot in an HTML div indented 0.5 inches
        print(f'TRANSLATED: ')
        display(HTML(f'<div style="padding-left: 0.5in;">{text}</div>'))
        
def print_toots(toots):
    """For each toot in toots, call print_toot to display 
       the username of the sender and toot text."""
    for toot in toots:
        print_toot(toot)

def get_domain_location_from_url(url):
    """Parse the domain from url, then look up the IP address 
       and get its city.
       
       Get a key at for ipgeolocation.io web service at: 
       https://ipgeolocation.io/signup.html
    """
    
    # get domain from url argument using Python 
    # urllib.parse module's urlparse function
    domain = urlparse(url).netloc

    # get IP address for hostname using Python 
    # socket module's gethostbyname function
    ip_address = socket.gethostbyname(domain)
    
    # URL to access ipgeolocation.io web services
    ipgeolocation_url = ('https://api.ipgeolocation.io/ipgeo?apiKey=' + 
       keys_mastodon.ipgeolocation_key + f'&ip={ip_address}')

    # use Python requests module to invoke web service
    response = requests.get(ipgeolocation_url)

    # if successful get the location
    if response.status_code == 200:
        data = response.json() # convert JSON to dictionary
        location = (f"{data['city']}, {data['state_prov']}, " +
            f"{data['country_code2']}")
        return location
    else:
        print('Error getting location\n',
              f'{response.status_code}: {response.text}')
        return None
        
def get_toot_content(toot):
    """Return dictionary with username of toot sender, toot content
       translated to English (if necessary) and the sender account's 
       Mastodon server domain."""

    fields = {}
    fields['username'] = toot.account.username

    # possibly translate plain_text
    if toot.language and not toot.language.startswith('en'):
        try:
            result = translator.translate_text(
                toot.content, target_lang='en-us')
            
            # save translated text
            fields['text'] = profanity.censor(result.text)
        except:
            fields['text'] = None
    else:
        fields['text'] = profanity.censor(toot.content)
        
    # look up server location
    fields['location'] = get_domain_location_from_url(toot.url)

    return fields

def get_geocodes(toot_list):
    """Get the latitude and longitude for each tweet's location.
    Returns the number of tweets with invalid location data."""
    
    print('Getting coordinates for mastodon server locations...')
    geo = ArcGIS()  # geocoder
    bad_locations = 0  

    for toot in toot_list:
        processed = False
        delay = .1  # used if OpenMapQuest times out to delay next call
        while not processed:
            try:  # get coordinates for tweet['location']
                geo_location = geo.geocode(toot['location'])
                processed = True
            except:  # timed out, so wait before trying again
                print('Service timed out. Waiting.')
                time.sleep(delay)
                delay += .1

        if geo_location:  
            toot['latitude'] = geo_location.latitude
            toot['longitude'] = geo_location.longitude
        else:  
            bad_locations += 1  # toot['location'] was invalid
    
    print('Done geocoding')
    return bad_locations


##########################################################################
# (C) Copyright 2022 by Deitel & Associates, Inc. and                    #
# Pearson Education, Inc. All Rights Reserved.                           #
#                                                                        #
# DISCLAIMER: The authors and publisher of this book have used their     #
# best efforts in preparing the book. These efforts include the          #
# development, research, and testing of the theories and programs        #
# to determine their effectiveness. The authors and publisher make       #
# no warranty of any kind, expressed or implied, with regard to these    #
# programs or to the documentation contained in these books. The authors #
# and publisher shall not be liable in any event for incidental or       #
# consequential damages in connection with, or arising out of, the       #
# furnishing, performance, or use of these programs.                     #
##########################################################################
