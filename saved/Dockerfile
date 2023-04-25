FROM jupyter/pyspark-notebook
# Install in the default python3 environment

# Install Python 3 packages
RUN conda install --quiet --yes \
	'dnspython' \
	'folium' \
	'geopy' \
	'imageio' \
	'ipython' \
	'jupyterlab' \
	'matplotlib' \
	'nltk' \
	'numpy' \
	'pymongo' \
	'scikit-learn' \
	'scipy' \
	'seaborn' \
	'spacy' \
	'statsmodels' \
	'tweepy' && \
    conda clean --all -f -y && \
	 pip install -U 'mastodon.py' && \
	 pip install -U 'textblob' && \
	 pip install 'tweet-preprocessor' && \
	 pip install --upgrade 'ibm-watson' && \
	 pip install 'pubnub' && \
	 pip install 'dweepy' && \
	 python -m textblob.download_corpora \
	 python -m spacy download en_core_web_sm \
	 python -m spacy download en_core_web_md \
	 python -m spacy download en_core_web_lg \
    npm cache clean --force && \
    rm -rf "/home/${NB_USER}/.cache/yarn" && \
    rm -rf "/home/${NB_USER}/.node-gyp" && \
    fix-permissions "${CONDA_DIR}" && \
    fix-permissions "/home/${NB_USER}"