from models import *
from interface import Interface, implements
from objdict import ObjDict
import json , sys , random 



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
	__model = PostingModel();
	__questions = []
	

	def createAccessKey(self):
		done = False
		key = random.randint(100000,999999)
		while (done==False) :
			if self.__model.keyExists( key ) :
				key = random.randint(100000,999999)
			else : 
				done = True

		return key


	def __init__(self,owner_id=None,jobTitle=None,company=None,description=None,questions=[]):
		self.__questions = questions
		self.__model.owner_id = owner_id
		self.__model.description = description
		self.__model.title = jobTitle
		self.__model.company_name = company
		self.__model.access_key = self.createAccessKey()
		
		return	
	'''
	Comit Posting is used to save the given Posting object to the database.
	This then propogates saveing all of its associated classes to the database as well. Such as 
	its questions.
	'''
	def commitPosting(self):
		self.__model.save_to_db()
		appID = self.__model.id
		if self.__questions :
			for question in self.__questions:
				try:
					 question.commitQuestion(appID)	
				except Exception , error:
					print >>sys.stderr, str(error)
		return

	def getAccessKey(self):
		key = self.__model.access_key
		if key :
		 	return key
		else :
		 	return 0

	
	def getPostingFromAccessCode(self,code):
		self.__model = self.__model.getPostingFromAccessCode(code)
		self.loadQuestions()
		
		pass

	def loadQuestions(self):

		posting_id = self.__model.id
		textQuestionsModel = TextQuestionModel() 
		
		#Get all the text question models associated with the posting ID
		questions = textQuestionsModel.getTextQuestionsFromPostId(posting_id)
		
		#Convert Models into Classes for questions[] list
		for question in questions:
			if(question.is_multiple_choice == 1):
			
				multipleChoice = multipleChoiceQuestion()
				multipleChoice.getFromModel(question)
				self.__questions.append(multipleChoice) 
			
			elif(question.is_multiple_choice == 0):
				textQuestion = TextQuestion(question.question)
				self.__questions.append(textQuestion)
			else :
				pass
	def getQuestions(self):
		return self.__questions

	def getInfo(self):
		info = {}
		info['postID'] = self.__model.id
		info['ownerID'] = self.__model.owner_id
		info['description']= self.__model.description
		info['title'] = self.__model.title
		info['company'] = self.__model.company_name
		info['accessKey'] = self.__model.access_key
		info['questions']=[]
		for question in self.__questions:
			info['questions'].append(question.getInfo())		
		return info
		


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
	def getInfo(self):
		pass



'''
Text Questions are questions that are text and expect a text response. 
'''
class TextQuestion(implements(QuestionInterface)):
	 
	__model = None

	def __init__(self , question):
		self.__model = TextQuestionModel()
	 	self.__model.question = question
	 	self.__model.answer_type = 0
	 	self.__model.answer_restriction = 1000

	def commitQuestion(self,post_id):
	 	try:
	 	 	self.__model.posting_id = post_id
	 	 	self.__model.save_to_db
	 	except Exception, e:
	 	 	raise ValueError('DataBase error on saving question :' + self.model.question + ' Error : ' + str(e))
	 	pass

	def getInfo(self):
	 	info = {}
	 	info['type'] = '1'
	 	info['question'] = self.__model.question
	 	info['id']= self.__model.id
	 	return info	 	 


'''
Multiple Choice Question class implements the Question Interface insuring that it has a commit question
method. This class is also associated with the textQuestion Model, although in the class initializer the 
appropriate model flags are set for this class. While seperating the information that MultipleChoiceQuestion 
and TextQuestion hold doesnt make sense at the database table level, it does at the class level. It is because of this
that we use different classes associated with the same models.   
'''
class multipleChoiceQuestion(implements(QuestionInterface)):
	 
	__model = None

	 #List of Option Classes
	__options = []
	 
	def __init__(self , question=None, optionslist=[]):
	 	self.__options = optionslist
	 	self.__model = TextQuestionModel()
	 	self.__model.question = question
	 	self.__model.is_multiple_choice = 1

	def commitQuestion(self,post_id):
	 	try:
	 	 	self.__model.posting_id = post_id
	 		self.__model.save_to_db()
	 	 	for option in self.__options :
	 	 	 	option.commitOption(self.__model.id)
	 	except Exception, error :
	 	 	raise error
	 	pass

	def getFromModel(self, textModel):
	 	#Assign given text question Model to class object
	 	self.__model = textModel
	 	self.__options = multipleChoiceOption.getOptionsFromId(self.__model.id)
	 	pass

	def getInfo(self):
	 	info = {}
	 	info['type']=2
	 	info['id']= self.__model.id
	 	info['question'] = self.__model.question
	 	info['options'] = []
	 	for option in self.__options:
	 	 	info['options'].append(option.getOption())

	 	return info


	 	 
	 	



'''
The multiple choice questions contain an array of multiple choice option instances. These instances can be used to 
save each option to the data base. The multiple choice option class is only accessible through its parent question class.
'''
class multipleChoiceOption():
	__model = None
	 
	def __init__(self , option):
	 	self.__model = MultipleChoiceOptionModel()
	 	self.__model.option = option
	 
	 	 

	def commitOption(self,question_id):
	 	try:
	 	 	self.__model.question_id = question_id
	 	 	self.__model.save_to_db()
	 	except :
	 	 	raise ValueError('DataBase error on saving multipleChoiceOption :' + self.model.option + ' Error : ' + str(error))
	 	 	pass
	 

	def getOption(self):
	 	if(self.__model.option != None):
	 		return self.__model.option
	 	else :
	 		return None


	@staticmethod
	def getOptionsFromId(id):
	 	optionslist = []

	 	model = MultipleChoiceOptionModel()
	 	options = model.getOptionsFromId(id)
	 	
	 	for option in options:
	 	 	optionslist.append(multipleChoiceOption(option.option))
	 	return optionslist




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

	@staticmethod
	def postingToJSON(posting):
		dictinfo = {}
		jsonInfo = posting.getInfo()
		print >>sys.stderr, jsonInfo
		return jsonInfo
     	
 