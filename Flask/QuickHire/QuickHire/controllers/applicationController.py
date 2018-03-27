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



class submitApplicationVideo (Resource):
    @jwt_required
    def post(self):
		data = request.get_json()

		if(data['data'] == 'done'):

			curent_user_id = User.getIdFromUsername(get_jwt_identity())
			answer = videoAnswer('binary.txt' , data['questionID'] , data['applicationID'], curent_user_id) 		
			answer.commitAnswer()

			return {'message': "Video Posted"}, 200


		else : 	
			
			binaryString = ""
			decoded_string = pybase64.urlsafe_b64decode(data['data'])
			f = '/home/liam/Developement/Flask/JrDesign/Term5_Software_Design_Project/Flask/QuickHire/QuickHire/uploads/binary.txt'
			
			with open('/home/liam/Developement/Flask/JrDesign/Term5_Software_Design_Project/Flask/QuickHire/QuickHire/uploads/binary.txt', 'ab+') as wfile:
				wfile.write(decoded_string)
			wfile.close()
		
		return
