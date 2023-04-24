# tootlistener.py
"""StreamListener subclass that processes toots as they arrive."""
from mastodon import StreamListener
import tootutilities 

# Global variable toot_listener_stream will be set to the stream handle so  
# we can close the stream after a specified number of toots are received
toot_listener_stream = None 

class TootListener(StreamListener):
    """Handles incoming toot stream."""

    def __init__(self, limit=10):
        """Create instance variables for tracking number of tweets."""
        self.toot_count = 0
        self.TOOT_LIMIT = limit

    def on_update(self, status):
        """Called when your listener receives a toot (status)."""
        tootutilities.print_toot(status)
        print()
        self.toot_count += 1 # track number of toots received
            
        # if TOOT_LIMIT is reached, close the stream
        if self.toot_count == self.TOOT_LIMIT:
            toot_listener_stream.close()
    
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
