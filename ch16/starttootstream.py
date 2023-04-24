# starttweetstream.py
"""Script to get tweets on topic(s) specified as script argument(s) 
   and send tweet text to a socket for processing by Spark."""
import keys_mastodon
from mastodon import Mastodon
from mastodon import StreamListener
import socket
import sys
import re

class TootListener(StreamListener):
    """Handles incoming toot stream."""

    def __init__(self, connection, limit=10):
        """Create instance variables for tracking number of tweets."""
        self.connection = connection
        self.toot_count = 0
        self.TOOT_LIMIT = limit

    def on_update(self, status):
        """Called when your listener receives a toot (status)."""
        
        # ignore if no hashtags
        if not status.tags:
            return
        
        hashtags = [] # stores the hashtags to send to spark app
        for hashtag in status.tags:
            hashtags.append(hashtag['name'].lower())

        # create a string of hashtags
        hashtags_string = ' '.join(hashtags) + '\n'
        print(f'{status.account.username}: {hashtags_string}')
        
        self.toot_count += 1  # track number of tweets processed
            
        # send hashtags to listening spark app
        try:
            # send requires bytes, so encode the string in utf-8 format
            self.connection.send(hashtags_string.encode('utf-8'))  
        except Exception as e:
            print(f'Error: {e}')

        # if TOOT_LIMIT is reached, close the stream
        if self.toot_count == self.TOOT_LIMIT:
            print('TOOT_LIMIT reached. Application terminating.')
            sys.exit(0)
            
if __name__ == '__main__':
    # create deiteltest app and save its credentials
    credentials = Mastodon.create_app(
        'DeitelSteamHashtags',
        api_base_url='https://mastodon.social',
        to_file='deitel_stream_hashtags_credentials.secret'
    )
    
    # create Mastodon client objects for making API calls then log in
    mastodon = Mastodon(client_id='deitel_stream_hashtags_credentials.secret')
    access_token = mastodon.log_in(keys_mastodon.usr, keys_mastodon.pwd, 
        to_file='deitel_stream_hashtags_credentials.secret')
    
    toot_limit = int(sys.argv[1])  # get maximum number of toots
    client_socket = socket.socket()  # create a socket 
    
    # app will use localhost (this computer) port 9876
    client_socket.bind(('localhost', 9876))  
 
    print('Waiting for connection')
    client_socket.listen()  # wait for client to connect
    
    # when connection received, get connection/client address
    connection, address = client_socket.accept()  
    print(f'Connection received from {address}')
 
    # create the StreamListener
    toot_listener = TootListener(connection, limit=toot_limit)
    
    # start the public federated stream and store the stream handle
    mastodon.stream_public(toot_listener, run_async=False)

    
##########################################################################
# (C) Copyright 2023 by Deitel & Associates, Inc. and                    #
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
