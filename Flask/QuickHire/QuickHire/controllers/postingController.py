import sys
from flask_restful import Resource, reqparse
from flask_jwt_extended import (create_access_token, create_refresh_token, jwt_required, jwt_refresh_token_required, get_jwt_identity, get_raw_jwt)
from classes import *
from flask import jsonify, request

from models import PostingModel

parser = reqparse.RequestParser()

class addPosting(Resource):
    
    @jwt_required
    def post(self):
       
        try:
            data = request.get_json()
        except :
            print >>sys.stderr, "JSON request not properly formatted"
            return {'message': "JSON request not properly formatted"}, 500    
        
        questionsJson = data['questions']
        questionsArray = JsonParser.parseQuestions(questionsJson)

        current_user_username = get_jwt_identity()
        curent_user_id = User.getIdFromUsername(current_user_username)
        

        key = Posting.commitPosting(curent_user_id, data['jobTitle'],data['company'],data['description'],questionsArray)

        message = 'Your Posting has been submitted. The access key for this posting is : ' + str(key)

        return {'message': message }

