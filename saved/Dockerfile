FROM jupyter/pyspark-notebook
# Install in the default python3 environment

# Install Python 3 packages
RUN conda install --quiet --yes \
	'dnspython==1.16.0' \
	'folium==0.11.0' \
	'geopy==1.22.0' \
	'imageio==2.8.0' \
	'ipython==7.15.0' \
	'jupyterlab==2.1.4' \
	'matplotlib==3.2.1' \
	'nltk==3.4.4' \
	'numpy==1.18.5' \
	'pymongo==3.10.1' \
	'scikit-learn==0.23.1' \
	'scipy==1.4.1' \
	'seaborn==0.10.1' \
	'spacy==2.2.4' \
	'statsmodels==0.11.1' \
	'textblob==0.15.3' \
	'tweepy==3.8.0' \
	'wordcloud==1.7.0' && \
    conda clean --all -f -y && \
	 pip install 'tweet-preprocessor' && \
	 pip install --upgrade 'ibm-watson' && \
	 pip install 'pubnub>=4.5.2' && \
	 pip install 'dweepy' && \
	 python -m textblob.download_corpora \
	 python -m spacy download en \
	 python -m spacy download en_core_web_md \
    npm cache clean --force && \
    rm -rf "/home/${NB_USER}/.cache/yarn" && \
    rm -rf "/home/${NB_USER}/.node-gyp" && \
    fix-permissions "${CONDA_DIR}" && \
    fix-permissions "/home/${NB_USER}"