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

        current_user = get_jwt_identity()
        curent_user_id = User.getIdFromUsername(current_user)
        
        Posting.commitPosting(curent_user_id, data['jobTitle'],data['company'],data['description'],questionsArray)


                        
        
        # try:
        #     User.createUser(data['username'],data['password'])
        # except ValueError as error:
        #     return {'message': str(error)}, 500
        # else:
        #     access_token = create_access_token(identity = data['username'])
        #     refresh_token = create_refresh_token(identity = data['username'])
        #     return {
        #         'message': 'User {} was created'.format(data['username']),
        #         'access_token': access_token,
        #         'refresh_token': refresh_token
        #         }

        return {'message': 'List of users'}

    def delete(self):
        return {'message': 'Delete all users'}