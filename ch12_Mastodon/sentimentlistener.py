# sentimentlisener.py
"""Tallies the number of positive, neutral and negative toots."""
import keys_mastodon
from better_profanity import profanity 
from mastodon import StreamListener
from bs4 import BeautifulSoup
import deepl
import preprocessor as p 
from textblob import TextBlob

# load censored words list
profanity.load_censor_words()

# stream will be set to the stream handle so we can close 
# the stream after a specified number of toots are received
sentiment_listener_stream = None

# translator to autodetect source language and return English
translator = deepl.Translator(keys_mastodon.deepL_key)

class SentimentListener(StreamListener):
    """Handles incoming Tweet stream."""

    def __init__(self, sentiment_dict, limit=10):
        """Configure the SentimentListener."""
        self.sentiment_dict = sentiment_dict
        self.toot_count = 0
        self.TOOT_LIMIT = limit
        
        # tweet-preprocessor remove @ mentions, emojis, hashtags, URLs
        p.set_options(p.OPT.MENTION, p.OPT.EMOJI, p.OPT.HASHTAG, p.OPT.URL)

    def on_update(self, status):
        """Called when Mastodon pushes a new toot to you."""

        # if the toot is not a retoot
        if status.reblogs_count == 0:                 
            # remove all HTML/CSS tags and commands
            plain_text = BeautifulSoup(
                status.content, 'html.parser').get_text()

            # clean the toot
            text = p.clean(plain_text) 

            # possibly translate plain_text
            if status.language and not status.language.startswith('en'):
                try:
                    result = translator.translate_text(
                        plain_text, target_lang='en-us')
                    text = profanity.censor(result.text) # save translated text
                except:
                    text = 'toot was empty'   

            # update self.sentiment_dict with the polarity
            blob = TextBlob(text)
            if blob.sentiment.polarity > 0.1:
                sentiment = '+'
                self.sentiment_dict['positive'] += 1 
            elif blob.sentiment.polarity < -0.1:
                sentiment = '-'
                self.sentiment_dict['negative'] += 1 
            else:
                sentiment = ' '
                self.sentiment_dict['neutral'] += 1 

            # display the toot
            print(f'{sentiment} {status.account.username}: {text}\n')

            self.toot_count += 1 # track number of toots processed

        # if TOOT_LIMIT reached, terminate streaming 
        if self.toot_count == self.TOOT_LIMIT:
            sentiment_listener_stream.close()


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
