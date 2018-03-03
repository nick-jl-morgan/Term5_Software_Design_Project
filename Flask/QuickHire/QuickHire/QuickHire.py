# all the imports
import os
from flask import Flask, request, session, g, redirect, url_for, abort, \
     render_template, flash, jsonify
from flask_restful import Api
from flask_sqlalchemy import SQLAlchemy
from flask_jwt_extended import JWTManager


app = Flask(__name__) 
app.config.from_object(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root:root@localhost/QuickHire'
app.config['SECRET_KEY'] = 'secret!'
app.config['JWT_SECRET_KEY'] = 'jwt-secret-string'

#Create Mobile API oject
mobileAPI = Api(app)

#Create DB ORM instance
db = SQLAlchemy(app)

#Create Java Web Token
jwt = JWTManager(app)


import resources

#LandingPage for WebApp
@app.route('/')
def landingController():
	return render_template('landingPage.html')




#Mobile API Routes
mobileAPI.add_resource(resources.UserRegistration, '/API/registration')
mobileAPI.add_resource(resources.UserLogin, '/API/login')
mobileAPI.add_resource(resources.UserLogoutAccess, '/API/logout/access')
mobileAPI.add_resource(resources.UserLogoutRefresh, '/API/logout/refresh')
mobileAPI.add_resource(resources.TokenRefresh, '/API/token/refresh')
mobileAPI.add_resource(resources.AllUsers, '/API/users')
mobileAPI.add_resource(resources.SecretResource, '/API/secret')