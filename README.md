# PythonDataScienceFullThrottle

Repository for my [O'Reilly Online Learning](https://learning.oreilly.com) live training course **Python Data Science Full Throttle: Introductory Artificial Intelligence (AI), Big Data and Cloud Case Studies**

All the Twitter examples are updated to the Twitter V2 APIs. **However**, in March 2023, Twitter changed the API access levels. Now, you cannot use most of the examples without having **pro** (US$5000/month) or **enterprise** (US$42000/month) API access. **Twitter has hired a new CEO and we are hoping to see changes to the API levels in the future.**

In the meantime, for this course, I will use the Mastodon open-source federated social media network for the data mining social media examples. 


# Running the Code Examples
The easiest way to run most of the code examples is to use the Dockerfile to create a Docker container. 

From the folder containing the Dockerfile, run the command: 
> `docker build -t deitelpydsft .`
 
From the root folder of this course's examples, run the `deitelpydsft` Docker container with the following command:
> docker run --rm -p 8888:8888 -p 4040:4040 -it --user root \
>    -v .:/home/jovyan/work deitelpydsft start.sh jupyter lab

Your command-line window will show you the URL to use to access the notebooks in your web browser.

# Custom Conda Environment
Rather than using Docker, you can set up a custom Anaconda environment on your local computer. These are the commands I used. They create a custom environment named `pydsft` — you can choose any name you like:

* Create the custom environment

> conda create --name pydsft

* Activate the environment you just created

> conda activate pydsft

* The following two commands install most of what you need for the content from ch11, ch12, ch13, ch14, ch15 and a good chunk of ch16—if you’re working from our books or videos, you might need to subsequently install a few other packages. **NOTE: You might want to install one package at a time from the following command as it can take a significant amount of time to resolve all the interdependencies if you do it all at once.**
 
> conda install nodejs jupyterlab ipympl wordcloud spacy tweepy geopy folium scikit-learn tensorflow matplotlib seaborn pymongo dnspython imageio pyaudio pydub 

> pip install -U dweepy pubnub ibm-watson tweet-preprocessor textblob deep_translator deepL 
 
The `pip` command is needed because a few packages are not available through the `conda` repository. 

If any package install fails with `conda` on Windows, use `pip` to install it. 

I'll keep this repository up-to-date with any changes I make for future presentations. 

**These files are for your personal use and may not be redistributed or reposted.**

If you have any questions, open an issue in the Issues tab or email us: deitel at deitel dot com.

Copyright 2019 by Deitel & Associates, Inc. and Pearson Education, Inc. All Rights Reserved. 

# Setup for Executing the Examples
The notebooks contain links to my videos and book on O'Reilly Online Learning where I discuss setup issues. 

# Our Books on Which These Examples Are Based
The content of this course is based on our book <a href=https://amzn.to/2Kd8dQk target="_blank">Python for Programmers</a>, which is a subset of our book <a href=https://amzn.to/2KfCptN target="_blank">Intro to Python for Computer Science and Data Science: Learning to Program with AI, Big Data and the Cloud.</a>
   
![Cover image for Python for Programmers](https://deitel.com/wp-content/uploads/2020/01/python-for-programmers.jpg)

The authors and publisher of this book have used their best efforts in preparing this book. These efforts include the development, research, and testing of the theories and programs to determine their effectiveness. The authors and publisher make no warranty of any kind, expressed or implied, with regard to these programs or to the documentation contained in this book. The authors and publisher shall not be liable in any event for incidental or consequential damages in connection with, or arising out of, the furnishing, performance, or use of these programs.
