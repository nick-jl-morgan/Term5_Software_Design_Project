import sys
from flask_restful import Resource, reqparse
from models import UserModel
from flask_jwt_extended import (create_access_token, create_refresh_token, jwt_required, jwt_refresh_token_required, get_jwt_identity, get_raw_jwt)
from classes import User , Posting

parser = reqparse.RequestParser()

class addPosting(Resource):
    
    def post(self):
        return {'message': 'List of users'}

    def delete(self):
        return {'message': 'Delete all users'}