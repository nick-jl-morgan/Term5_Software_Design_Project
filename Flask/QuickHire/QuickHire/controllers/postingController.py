import sys
from flask_restful import Resource, reqparse
from flask_jwt_extended import (create_access_token, create_refresh_token, jwt_required, jwt_refresh_token_required, get_jwt_identity, get_raw_jwt)
from classes import *
from flask import jsonify, request
from werkzeug.utils import secure_filename
from models import PostingModel
import os
parser = reqparse.RequestParser()


class UploadVideo(Resource):

    @jwt_required
    def post(self):
        files = request.files
        for file in files:
            vid = request.files[file]
            filename = secure_filename(file)
            vid.save(os.path.join('/home/liam/Developement/Flask/JrDesign/Term5_Software_Design_Project/Flask/QuickHire/QuickHire/uploads/', file))
        
        return{'message': 'goteem'}








class addPosting(Resource):
    
    @jwt_required
    def post(self):
        print >>sys.stderr, request.get_data()
        print >>sys.stderr, request.headers
        try:
            data = request.get_json()
        except :
            print >>sys.stderr, "JSON request not properly formatted"
            return {'message': "JSON request not properly formatted"}, 500    
        
        questionsJson = data['questions']
        
        #Get List of question ojects
        questionsArray = JsonParser.parseQuestions(questionsJson)
        
        #Get User ID for Posting
        #current_user_username = get_jwt_identity()
        #curent_user_id = User.getIdFromUsername(current_user_username)
        curent_user_id = 3
        #Commit The posting and get the access key for it
        posting = Posting(curent_user_id, data['jobTitle'],data['company'],data['description'],questionsArray)
        posting.commitPosting()
        key = posting.getAccessKey()
        message = 'Your Posting has been submitted. The access key for this posting is : ' + str(key)
        
        print >>sys.stderr,message
        return {'message': message }

class getPostingFromAccessCode(Resource):
    
    @jwt_required
    def post(self):
        try:
            data = request.get_json()
        except :
            print >>sys.stderr, "JSON request not properly formatted"
            return {'message': "JSON request not properly formatted"}, 500   
        
        code = data['AccessCode']
        posting = Posting()
        posting.getPostingFromAccessCode(code)
        postingJSON = JsonParser.postingToJSON(posting)
        



        return jsonify(postingJSON)


        