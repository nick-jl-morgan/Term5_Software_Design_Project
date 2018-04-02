# Junior Design Project - QuickHire

## Install
QuickHire Readme

QuickHire is a job application platform that allows employers to assess an applicant's interpersonal skills in addition to the traditional resume. This is done by adding one-try video responses that applicants answer when apply for a job posting. With this system employers are able to quickly assess an applicant's interpersonal skills along with their resume. Using QuickHire employers will be able to expedite their hiring process and select more desirable candidates for an interview while allowing applicants to give a personal touch to their application. 


There are two components to the quickhire application, an android application and a web portal. The android application allows employers to create job postings, applicants to find a job posting based on an access key, and applicants to apply for jobs. The web portal provides a frame work for employers to login and review the current applicants for their job postings.

To run the QuickHire Software on your local machine follow these steps.

- install android studio
- open the file location and build the application
- click run


The server for QuickHire is a Flask Mircro server written in Python. The server is hosted and active at IP 99.253.59.150





To run the server on your own local machine follow the following instructions. This requires a linux 64x bit operating system

1. Using the SQL dump in the main folder to copy the SQL database to your local machine
2. In QuickHire.py line 18 update the database access information
3. While in Term5_Software_Design_Project folder run the following command

user$~  . venv/bin/activate

This will activate the virtual envirnemnt needed to run the server

4. ApplicationController.py line 108 as well as classes.py line #491 must be updated to match the directory path to the uploads folder within your machine

5. Naviage to Term5_Software_Design_Project/Flask/QuickHire/QuickHire and enter the following commands

user$~ export FLASK_APP=QuickHire.py
user$~ flask run

The flask server should now be running on your local ip address on port 5000

6. Edit connection.java line 52 to use your local ip instead of the original server


