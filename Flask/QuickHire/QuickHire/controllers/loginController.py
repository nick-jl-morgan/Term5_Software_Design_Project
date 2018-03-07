
import sys
from flask_restful import Resource, reqparse
from models import UserModel
from flask_jwt_extended import (create_access_token, create_refresh_token, jwt_required, jwt_refresh_token_required, get_jwt_identity, get_raw_jwt)
from classes import User
from flask import jsonify, request

parser = reqparse.RequestParser()
parser.add_argument('username', help = 'This field cannot be blank', required = True)
parser.add_argument('password', help = 'This field cannot be blank', required = True)

class UserRegistration(Resource):
    def post(self):
        print >>sys.stderr, request.get_data()
        print >>sys.stderr, request.headers
        try:
            data = request.get_json(force = True)
        except :
            print >>sys.stderr, "JSON request not properly formatted"
            return {'message': "JSON request not properly formatted"}, 500
            

        try:
            User.createUser(data['username'],data['password'])
        except ValueError as error:
            print >>sys.stderr, str(error)
            return {'message': str(error)}, 500
        else:
            access_token = create_access_token(identity = data['username'])
            refresh_token = create_refresh_token(identity = data['username'])
            return {
                'message': 'User {} was created'.format(data['username']),
                'access_token': access_token,
                'refresh_token': refresh_token
                }


class UserLogin(Resource):
    def post(self):
        data = parser.parse_args()
        try:
            User.loginUser(data['username'],data['password'])
        except ValueError as error:
            return {'message': str(error)}, 500
        else:
            access_token = create_access_token(identity = data['username'])
            refresh_token = create_refresh_token(identity = data['username'])
            return{
                'message': 'Logged in as {}'.format(data['username']),
                'access_token': access_token,
                'refresh_token': refresh_token
            }
      
class UserLogoutAccess(Resource):
    def post(self):
        return {'message': 'User logout'}
      
      
class UserLogoutRefresh(Resource):
    def post(self):
        return {'message': 'User logout'}



class TokenRefresh(Resource):
    @jwt_refresh_token_required
    def post(self):
        current_user = get_jwt_identity()
        access_token = create_access_token(identity = current_user)
        return {'access_token': access_token}