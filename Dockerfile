# Based on the Dockerfiles from the Jupyter Development Team which 
# are Copyright (c) Jupyter Development Team and distributed under 
# the terms of the Modified BSD License.
ARG OWNER=jupyter
ARG BASE_CONTAINER=$OWNER/pyspark-notebook
FROM $BASE_CONTAINER

LABEL maintainer="Paul Deitel <paul@deitel.com>"

# Fix: https://github.com/hadolint/hadolint/wiki/DL4006
# Fix: https://github.com/koalaman/shellcheck/wiki/SC3014
SHELL ["/bin/bash", "-o", "pipefail", "-c"]

RUN mamba install --yes \
    'dnspython' \
    'folium' \
    'geopy' \
    'imageio' \
    'nltk'  \
    'pymongo' \
    'scikit-learn' \
    'spacy' \
    'tweepy' 
     
RUN pip install --upgrade \
    'tensorflow' \
    'openai' \
    'beautifulsoup4' \
    'deepl' \
    'mastodon.py' \
    'better_profanity'  \
    'tweet-preprocessor' \
    'ibm-watson' \
    'pubnub' \
    'textblob' \
    'wordcloud' \
    'dweepy' 

# download data required by textblob and spacy
RUN python -m textblob.download_corpora && \
    python -m spacy download en_core_web_sm && \
    python -m spacy download en_core_web_md && \
    python -m spacy download en_core_web_lg 

# clean up
RUN mamba clean --all -f -y && \
    fix-permissions "${CONDA_DIR}" && \
    fix-permissions "/home/${NB_USER}"
