# PythonDataScienceFullThrottle

Repository for my [O'Reilly Online Learning](https://learning.oreilly.com) live training course **Python Data Science Full Throttle: Introductory Artificial Intelligence (AI), Big Data and Cloud Case Studies**

All the Twitter examples are updated to the Twitter V2 APIs. However, Twitter in March 2023, Twitter changed the API access levels. Now, you cannot use most of the examples without having enterprise API access, which is a **minimum of US$42000 per month.** Twitter has hired a new CEO and we are hoping to see changes to the API levels in the future. 

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
 
The `pip` command is needed because a few packages are not available through the `conda` repository. If any package install fails with `conda` on Windows, use `pip` to install it. 

# Past Course Links (accessible only if you were registered for that course)
May 25, 2022:
https://learning.oreilly.com/live-events/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920289197/0636920088246/

December 8, 2022: https://learning.oreilly.com/live-events/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920289197/0636920078752/

October 31, 2022: https://learning.oreilly.com/live-events/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920289197/0636920078689/

September 27, 2022: https://learning.oreilly.com/live-events/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920289197/0636920078295/

August 11, 2022: https://learning.oreilly.com/live-events/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920289197/0636920075331/

May 23, 2022: https://learning.oreilly.com/live-events/python-data-science-full-throttle-with-paul-deitel-introductory-ai-big-data-and-cloud-case-studies/0636920289197/0636920071497/

April 26, 2022: https://learning.oreilly.com/live-events/python-data-science-full-throttle-with-paul-deitel-introductory-ai-big-data-and-cloud-case-studies/0636920289197/0636920069717/

March 22, 2022: https://learning.oreilly.com/live-events/python-data-science-full-throttle-with-paul-deitel-introductory-ai-big-data-and-cloud-case-studies/0636920289197/0636920068414/
 
February 28, 2022: https://learning.oreilly.com/live-events/python-data-science-full-throttle-with-paul-deitel-introductory-ai-big-data-and-cloud-case-studies/0636920289197/0636920066757/

January 25, 2022: https://learning.oreilly.com/live-events/python-data-science-full-throttle-with-paul-deitel-introductory-ai-big-data-and-cloud-case-studies/0636920289197/0636920064476/

December 14, 2021: https://learning.oreilly.com/live-events/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920289197/0636920062991/

November 16, 2021: https://learning.oreilly.com/live-events/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920289197/0636920062213/

October 16, 2021: https://learning.oreilly.com/live-events/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920289197/0636920060166/

September 28, 2021: https://learning.oreilly.com/live-events/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920289197/0636920059475/

July 27, 2021: https://learning.oreilly.com/attend/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920289197/0636920056748/

June 22, 2021: https://learning.oreilly.com/attend/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920289197/0636920054946/

May 25, 2021: https://learning.oreilly.com/attend/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920289197/0636920054006/

April 27, 2021: https://learning.oreilly.com/attend/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920289197/0636920052503/

February 23, 2021: https://learning.oreilly.com/live-training/courses/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920051482/

January 19, 2021: https://learning.oreilly.com/live-training/courses/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920474654/

December 8, 2020: https://learning.oreilly.com/live-training/courses/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920506140/

November 17, 2020: https://learning.oreilly.com/live-training/courses/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920464198/

October 20, 2020: https://learning.oreilly.com/live-training/courses/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920404613/

September 29, 2020: https://learning.oreilly.com/live-training/courses/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920410720/

August 18, 2020: https://learning.oreilly.com/live-training/courses/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920457855/

July 28, 2020: https://learning.oreilly.com/live-training/courses/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920444305/

June 16, 2020: https://learning.oreilly.com/live-training/courses/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920436126/

May 19, 2020: https://learning.oreilly.com/live-training/courses/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920389675/

March 17, 2020: https://learning.oreilly.com/live-training/courses/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920373117/

January 21, 2020: https://learning.oreilly.com/live-training/courses/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920358213/

December 17, 2019: https://learning.oreilly.com/live-training/courses/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920322481/

August 26, 2019: https://learning.oreilly.com/live-training/courses/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920305996/

July 30, 2019: https://learning.oreilly.com/live-training/courses/python-data-science-full-throttle-with-paul-deitel-introductory-artificial-intelligence-ai-big-data-and-cloud-case-studies/0636920289173/


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
