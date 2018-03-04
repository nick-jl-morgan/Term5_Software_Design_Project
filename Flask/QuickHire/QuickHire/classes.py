from models import UserModel, ApplicationModel
import sys
class User:

	@staticmethod
	def createUser(username,password):
		#Check if user already exists
		if UserModel.find_by_username(username):
			raise ValueError('This User Already Exists')
			
		hashedPassword = UserModel.generate_hash(password)
		new_user = UserModel(
            username = username,
            password = hashedPassword
        )
		try:
			new_user.save_to_db()
		except:
			raise ValueError('DataBase Error')
		finally:
			return

	@staticmethod
	def loginUser(username, password):
		
		 current_user = UserModel.find_by_username(username)
		 
		 #Check if user exist 
		 if not UserModel.find_by_username(username):  
			 raise ValueError('User does not exist')
		 
		 if not UserModel.verify_hash( password ,current_user.password):
		 	 raise ValueError('Incorrect password')
		
		 return

class Application:
	
	@staticmethod
	def createApplication(owner_id,jobTitle,company,description,questions):
		
		Application = ApplicationModel();
		Application.id = owner_id
		Application.description = description
		Application.title = jobTitle
		Application.company_name = company
		Application.post_id = 666

		for question in questions:
			print >>sys.stderr, question
		
		return





