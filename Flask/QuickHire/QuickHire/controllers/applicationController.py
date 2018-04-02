'''
APPLICATION CONTROLLER

This controller handels all requests that have to do submitting applications


'''



import sys
from flask_restful import Resource, reqparse
from flask_jwt_extended import (create_access_token, create_refresh_token, jwt_required, jwt_refresh_token_required, get_jwt_identity, get_raw_jwt)
from classes import *
from flask import jsonify, request
from werkzeug.utils import secure_filename
from models import PostingModel
import os
import pybase64

parser = reqparse.RequestParser()


'''
Submit Application -  Given A Json Packet containing given the following formating :

{
    "postID": 126,
    "hasVideo":1,
    "answers": [
        {
            "questionID": 126,
            "answer": "I try my best to make the smells not smell. But line after line that familiar smell starts wafting through the air",
            "type": "1"
        },{
			"questionID": 127,
            "answer": "This is an answer to a question",
            "type": "1"
        }
    ]
}

This controller extracts the answer objects, converts them to python classes, creates application objects and commits them to the Database

'''
class submitApplication (Resource):

    @jwt_required
    def post(self):
		try:
			data = request.get_json()
		except :
			print >>sys.stderr, "JSON request not properly formatted"
			return {'message': "JSON request not properly formatted"}, 500 

		print >>sys.stderr , data

		curent_user_id = User.getIdFromUsername(get_jwt_identity())

		answersArray = JsonParser.parseAnswers(data['answers'])

		app = Application( curent_user_id , data['postID'] , answersArray )

		appID = app.commitApplication() 

		return {'id': appID}, 200 



'''
This controller accepts JSON packets with the following formatting
{
  "data": "----",
  "questionID" : 3,
  "applicationID":2
}

The "data" key can have two values. The first being a Base64 encoded string containing video data. In this case the data is converted to binary
and written to a file located with the questionID and applicationID. Once "data" holds a value of "Done" a video answer object is created. The constructor
handels converting the binary file to a mp4

'''
class submitApplicationVideo (Resource):
    @jwt_required
    def post(self):

		location = "/home/liam/Developement/Flask/JrDesign/Term5_Software_Design_Project/Flask/QuickHire/QuickHire/static/uploads/"

		data = request.get_json()

		file = str(data['applicationID']) +"-"+str(data['questionID']) + ".txt"

		if(data['data'] == 'done'):
			print >> sys.stderr , "Done"
			curent_user_id = User.getIdFromUsername(get_jwt_identity())

			answer = videoAnswer( file , data['questionID'] , data['applicationID'], curent_user_id) 		
			answer.commitAnswer()

			return {'message': "Video Posted"}, 200


		else : 	
			
			binaryString = ""
			decoded_string = pybase64.urlsafe_b64decode(data['data'])

			with open('/home/liam/Developement/Flask/JrDesign/Term5_Software_Design_Project/Flask/QuickHire/QuickHire/static/uploads/'+file , 'ab+') as wfile:
				wfile.write(decoded_string)
			wfile.close()
		
		return
