from models import UserModel, PostingModel, textQuestionModel
import sys
from interface import Interface, implements
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

	@staticmethod
	def getIdFromUsername(username):
		return UserModel.getID(username)







class Posting:

	@classmethod
	def commitPosting(cls,owner_id,jobTitle,company,description,questions):
		print >>sys.stderr,'One'
		

		Posting = PostingModel();
		Posting.owner_id = owner_id
		Posting.description = description
		Posting.title = jobTitle
		Posting.company_name = company
		Posting.access_key = 6666
		Posting.save_to_db()

		appID = Posting.id
		print >>sys.stderr,'Two'

		for question in questions:
			try:
				question.commitQuestion(appID)
			except:
				print >>sys.stderr,'commitQuestion Failure'
		return

class QuestionInterface(Interface):
	
	def commitQuestion(id):
		 pass

class TextQuestion(implements(QuestionInterface)):
	 
	 __model = None

	 def __init__(self , question):
	 	 self.model = textQuestionModel()
	 	 self.model.question = question

	 def commitQuestion(id):
	 	 try:
	 	 	 self.model.posting_id = id
	 	 	 self.model.save_to_db;
	 	 except :
	 	 	raise ValueError('DataBase error on saving question with ID:' + id)


class JsonParser:
	
	@staticmethod
	def parseQuestions(questions):

		objectList = []

		for question in questions:
			if question['type']==2 :

				objectList.append(JsonParser.parseTextQuestion(question))
			
			elif question['type']==1:
				pass
				#objectList.append(JsonParser.parseMultipleChoiceQuestion(question))

		return objectList

	@staticmethod
	def parseTextQuestion(questionJson):
		q = TextQuestion(questionJson['question'])
		return q

	@staticmethod
	def parseMultipleChoiceQuestion(question):
		pass





