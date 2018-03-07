from models import *
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
		
		Posting = PostingModel();
		Posting.owner_id = owner_id
		Posting.description = description
		Posting.title = jobTitle
		Posting.company_name = company
		Posting.access_key = 6666
		Posting.save_to_db()

		appID = Posting.id
	
		for question in questions:
			try:
				 question.commitQuestion(appID)	
			except Exception , error:
				print >>sys.stderr, str(error)
		return Posting.access_key



'''
The purpose of the question interfce is that when a posting is given a list of questions, 
it can be promised that all questions will have certain functions.

An example of this is in the commitPosting method. This method is given a list of questions. It then assigns
a posting ID to each question and then commits it via the commitQuestion method. By having this method be an attribute of the
interface, the commitPosting method does not need to know what kind of question its working with as it is promised that 
all questions have this method. 
'''
class QuestionInterface(Interface):	
	def commitQuestion(self,post_id):
		 pass



'''
Text Questions are questions that are text and expect a text response. 
'''
class TextQuestion(implements(QuestionInterface)):
	 
	 __model = None

	 def __init__(self , question):
	 	 self.model = TextQuestionModel()
	 	 self.model.question = question
	 	 self.model.answer_type = 0
	 	 self.type.answer_restriction = 1000

	 def commitQuestion(self,post_id):
	 	 try:
	 	 	 self.model.posting_id = post_id
	 	 	 self.model.save_to_db
	 	 except Exception, e:
	 	 	raise ValueError('DataBase error on saving question :' + self.model.question + ' Error : ' + str(e))

'''
Multiple Choice Question class implements the Question Interface insuring that it has a commit question
method. This class is also associated with the textQuestion Model, although in the class initializer the 
appropriate model flags are set for this class. While seperating the information that MultipleChoiceQuestion 
and TextQuestion hold doesnt make sense at the database table level, it does at the class level. It is because of this
that we use different classes associated with the same models.   
'''
class multipleChoiceQuestion(implements(QuestionInterface)):
	 
	 __model = None
	 __options = []
	 
	 def __init__(self , question, optionslist):
	 	 self.__options = optionslist
	 	 self.model = TextQuestionModel()
	 	 self.model.question = question
	 	 self.model.is_multiple_choice = 1

	 def commitQuestion(self,post_id):
	 	 try:
	 	 	 self.model.posting_id = post_id
	 		 self.model.save_to_db()
	 	 	 for option in self.__options :
	 	 	 	option.commitOption(self.model.id)
	 	 except Exception, error :
	 	 	raise error
	 	 pass

'''
The multiple choice questions contain an array of multiple choice option instances. These instances can be used to 
save each option to the data base. The multiple choice option class is only accessible through its parent question class.
'''
class multipleChoiceOption():
	 __model = None

	 def __init__(self , option):
	 	 self.model = MultipleChoiceOptionModel()
	 	 self.model.option = option
	 	 

	 def commitOption(self,question_id):
	 	 try:
	 	 	 self.model.question_id = question_id
	 	 	 self.model.save_to_db()
	 	 except :
	 	 	raise ValueError('DataBase error on saving multipleChoiceOption :' + self.model.option + ' Error : ' + str(error))





'''
A common function is taking submitted JSON and converting it to python objects that can then 
be worked on.

JSON parser class contains static methods that take JSON dicts and return either an object or list of 
objects. These objects/lists then get passed to other functions via the controller.

'''

class JsonParser:
	
	@staticmethod
	def parseQuestions(questions):

		objectList = []
		for question in questions:
			if question['type']==1 :

				objectList.append(JsonParser.parseTextQuestion(question))
			
			elif question['type']==2:
				
				objectList.append(JsonParser.parseMultipleChoiceQuestion(question))

		return objectList

	@staticmethod
	def parseTextQuestion(questionJson):
		question = TextQuestion(questionJson['question'])
		return question

	@staticmethod
	def parseMultipleChoiceQuestion(multipleChoicequestionJson):
		
		optionsdict = multipleChoicequestionJson['options']
		optionslist = []
		for option in optionsdict: 
			optionslist.append(multipleChoiceOption(option))

		question = multipleChoiceQuestion(multipleChoicequestionJson['question'], optionslist)

		return question




