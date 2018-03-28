# all the imports
import os
from flask import Flask, request, session, g, redirect, url_for, abort, \
     render_template, flash, jsonify
from flask_restful import Api
from flask_sqlalchemy import SQLAlchemy
from flask_jwt_extended import JWTManager
import sys
from flask_jwt_extended import (create_access_token, create_refresh_token, jwt_required, jwt_refresh_token_required, get_jwt_identity, get_raw_jwt)





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




UPLOAD_FOLDER = '/home/liam/Developement/Flask/JrDesign/Term5_Software_Design_Project/Flask/QuickHire/QuickHire/uploads'
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER









import controllers.loginController as loginController, controllers.applicationController as applicationController, controllers.postingController as postingController
from classes import *
#LandingPage for WebApp
@app.route('/')
def landingController():
	return render_template('loginPage.html')

@app.route('/login',methods = ['POST'])
def login():
	result = request.form

	username = result['username']
	password = result['password']

	#Try to login the user in
	try:
		User.loginUser(username,password)
	except ValueError as error:
		return {'message from login': str(error)}, 500
	
	else:
		access_token = create_access_token(identity = username)
		refresh_token = create_refresh_token(identity = username)
		return render_template('homePage.html', accessToken=username)


@app.route('/getUserPostings',methods = ['POST'])
def getUserPostings():
	
	postings = Posting()
	name = request.form['user']

	print >> sys.stderr , name
	postingsDict = postings.getUserPostings(name)

	return jsonify(postingsDict)



@app.route('/getApplicantsFromAccessKey/<accessKey>')
def getApplicantsFromAccessKey(accessKey):

	app = Application()
	data = app.getApplicantsFromAccessKey(accessKey)

	return render_template('viewApplicants.html', data=data)





@app.route('/user/<username>')
def show_user_profile(username):
    # show the user profile for that user
    return 'User %s' % username







#Mobile API Routes
mobileAPI.add_resource(loginController.UserRegistration, '/API/registration')
mobileAPI.add_resource(loginController.UserLogin, '/API/login')
mobileAPI.add_resource(loginController.UserLogoutAccess, '/API/logout/access')
mobileAPI.add_resource(loginController.UserLogoutRefresh, '/API/logout/refresh')
mobileAPI.add_resource(loginController.TokenRefresh, '/API/token/refresh')
mobileAPI.add_resource(postingController.addPosting,'/API/AddPosting')
mobileAPI.add_resource(postingController.getPostingFromAccessCode,'/API/getPostingFromAccessCode')
mobileAPI.add_resource(applicationController.submitApplicationVideo,'/API/UploadVideo')
mobileAPI.add_resource(applicationController.submitApplication,'/API/submitApplication')


app.run(host='0.0.0.0', port=5000)