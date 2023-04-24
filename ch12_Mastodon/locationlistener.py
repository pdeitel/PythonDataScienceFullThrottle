# locationlistener.py
"""Receives toots and stores a list of dictionaries containing 
each toot's username/content, server domain and server location."""
import keys_mastodon
import tootutilities 
from mastodon import StreamListener
from bs4 import BeautifulSoup
import deepl
import preprocessor as p 
from textblob import TextBlob

# stream will be set to the stream handle so we can close 
# the stream after a specified number of toots are received
location_listener_stream = None

class LocationListener(StreamListener):
    """Handles incoming Toot stream to get location data."""

    def __init__(self, counts_dict, toots_list, limit=10):
        """Configure the LocationListener."""
        self.toots_list = toots_list
        self.counts_dict = counts_dict
        self.TOOT_LIMIT = limit

    def on_update(self, status):
        """Called when Mastodon pushes a new toot to you."""

        # get toot's username, text and location
        toot_data = tootutilities.get_toot_content(status)  
        self.counts_dict['total_toots'] += 1 # it's an original toot

        # ignore toots with no server location--can't plot on a map
        if not toot_data.get('location'):  
            return

        self.counts_dict['locations'] += 1 
        self.toots_list.append(toot_data) # store the toot
        
        print(f"{self.counts_dict['locations']:2}: {toot_data['username']}")

        # if TOOT_LIMIT is reached, terminate streaming
        if self.counts_dict['locations'] == self.TOOT_LIMIT:
            location_listener_stream.close()



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
