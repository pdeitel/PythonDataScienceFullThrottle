# tweetlistener.py
"""TweetListener downloads tweets and stores them in MongoDB."""
import json
import tweepy
from IPython.display import clear_output

class TweetListener(tweepy.StreamingClient):
    """Handles incoming Tweet stream."""

    def __init__(self, bearer_token, database, limit=10000):
        """Create instance variables for tracking number of tweets."""
        self.db = database
        self.tweet_count = 0
        self.TWEET_LIMIT = limit  # 10,000 by default
        super().__init__(bearer_token, wait_on_rate_limit=True)  

    def on_connect(self):
        """Called when your connection attempt is successful, enabling 
        you to perform appropriate application tasks at that point."""
        print('Successfully connected to Twitter\n')

    def on_data(self, data):
        """Called when Twitter pushes a new tweet to you."""
        self.tweet_count += 1  # track number of tweets processed
        json_data = json.loads(data)  # convert string to JSON
        self.db.tweets.insert_one(json_data)  # store in tweets collection
        clear_output()  # ADDED: show one tweet at a time in Jupyter Notebook

        try:
            print(f'    Screen name: {json_data["includes"]["users"][0]["username"]}') 
            print(f'     Created at: {json_data["data"]["created_at"][:10]}')         
            print(f'Tweets received: {self.tweet_count}')    
        except:
            print("Twitter response missing a requested field")

        # if TWEET_LIMIT is reached, return False to terminate streaming
        if self.tweet_count == self.TWEET_LIMIT:
            self.disconnect()
    
    def on_exception(self, status):
        print(f'Error: {status}')
        return True

##########################################################################
# (C) Copyright 2019 by Deitel & Associates, Inc. and                    #
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
