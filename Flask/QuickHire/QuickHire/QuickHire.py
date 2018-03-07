# all the imports
import os
from flask import Flask, request, session, g, redirect, url_for, abort, \
     render_template, flash, jsonify
from flask_restful import Api
from flask_sqlalchemy import SQLAlchemy
from flask_jwt_extended import JWTManager


app = Flask(__name__) 
app.config.from_object(__name__)
app.config.update(
	SQLALCHEMY_DATABASE_URI = 'mysql+pymysql://root:root@localhost/QuickHire',
	SECRET_KEY='secret!',
	JWT_SECRET_KEY= 'jwt-secret-string',
	SQLALCHEMY_TRACK_MODIFICATIONS= 'false'
	)

mobileAPI = Api(app)

#Create DB ORM instance
db = SQLAlchemy(app)

#Create Java Web Token
jwt = JWTManager(app)


import controllers.loginController as loginController, controllers.applicationController as applicationController, controllers.postingController as postingController

#LandingPage for WebApp
@app.route('/')
def landingController():
	return render_template('landingPage.html')




#Mobile API Routes
mobileAPI.add_resource(loginController.UserRegistration, '/API/registration')
mobileAPI.add_resource(loginController.UserLogin, '/API/login')
mobileAPI.add_resource(loginController.UserLogoutAccess, '/API/logout/access')
mobileAPI.add_resource(loginController.UserLogoutRefresh, '/API/logout/refresh')
mobileAPI.add_resource(loginController.TokenRefresh, '/API/token/refresh')
mobileAPI.add_resource(postingController.addPosting,'/API/AddPosting')
app.run(host='0.0.0.0', port=5000)