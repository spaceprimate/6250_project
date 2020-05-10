# 6250_project
Final Project for CSCI6250
By Daniel Murphy
University of New Orleans - Spring 2020

# About
This project consists of 2 core components: 
* /twitter_mining
* /sentiment_analysis

### Twitter mining
In the /twitter_mining directory you'll find a java application responsible for accessing the twitter api and extracting data related to recently released movies. 

The class Filmapp.java has all the relevent methods used to access Twitter, query particular movies, and download tweet data as xml.

It is found at : twitter_mining\src\main\java\Filmapp.java

To run, you will need a twitter developer account, details for which are found in the twitter4j.properties file.

### Sentiment Analysis

In the /sentiment_analysis directory you'll find a jupyter notebook application called score_movies.ipynb

This file contains all of the code used to conduct sentiment analysis in the project

You'll also find the following

/sentiment_analysis/movie_ratings.csv
- this contains a list of movies analyzed and their Critic and Audience scores on Rotten Tomatoes

/sentiment_analysis/data
- this directory contains xml data mined from twitter for individual movies. 